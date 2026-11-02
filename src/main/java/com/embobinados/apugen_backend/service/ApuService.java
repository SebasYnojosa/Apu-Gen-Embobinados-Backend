package com.embobinados.apugen_backend.service;

import com.embobinados.apugen_backend.dto.ApuResponseDTO;
import com.embobinados.apugen_backend.entity.*;
import com.embobinados.apugen_backend.entity.Process;
import com.embobinados.apugen_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApuService {
    private final ProcessRepository processRepository;
    private final MaterialRepository materialRepository;
    private final EquipmentRepository equipmentRepository;
    private final LaborRepository laborRepository;
    private final ProcessMaterialRepository processMaterialRepository;
    private final ProcessEquipmentRepository processEquipmentRepository;
    private final ProcessLaborRepository processLaborRepository;

    @Transactional
    public Process calcAndSaveApu(
            Process process,
            List<ProcessMaterial> materials,
            List<ProcessEquipment> equipments,
            List<ProcessLabor> labors
    ) {
        // CALCULO DE MATERIALES
        BigDecimal totalMateriales = BigDecimal.ZERO;
        for (ProcessMaterial pm : materials) {
            // Ir al catalogo, extraer datos y hacer el snapshot
            Material catMat = materialRepository.findById(pm.getMaterial().getId())
                    .orElseThrow(() -> new RuntimeException("Material no encontrado"));

            pm.setDescriptionSnapshot(catMat.getDescription());
            pm.setUnitSnapshot(catMat.getUnit());
            pm.setCostApplied(catMat.getBaseCost());

            // monto = costo * cantidad
            BigDecimal amount = pm.getCostApplied().multiply(pm.getQuantity()).setScale(2, RoundingMode.HALF_UP);
            pm.setAmount(amount);
            pm.setProcess(process);

            totalMateriales = totalMateriales.add(amount);
        }

        //costo_unitario_materiales = total_materiales * cantidad_item
        BigDecimal materialUnitCost = totalMateriales.multiply(process.getItemQuantity()).setScale(2, RoundingMode.HALF_UP);
        process.setMaterialUnitCost(materialUnitCost);

        // CALCULO DE EQUIPOS
        BigDecimal totalEquipos = BigDecimal.ZERO;
        for (ProcessEquipment pe : equipments) {
            // Ir al inventario, extraer datos y hacer el snapshot
            Equipment catEq = equipmentRepository.findById(pe.getEquipment().getId())
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

            pe.setDescriptionSnapshot(catEq.getDescription());
            pe.setValueApplied(catEq.getBaseValue());
            pe.setDepreciationFactorApplied(catEq.getDepreciationFactor()); // Se mantiene con 5 decimales

            // costo_uso_diario = valor * factor_depreciacion
            BigDecimal dailyUseCost = pe.getValueApplied().multiply(pe.getDepreciationFactorApplied().setScale(2, RoundingMode.HALF_UP));
            pe.setDailyUseCost(dailyUseCost);
            pe.setProcess(process);

            totalEquipos = totalEquipos.add(dailyUseCost);
        }

        // costo_unitario_equipos = total_equipos / rendimiento
        BigDecimal equipmentUnitCost = totalEquipos.divide(process.getPerformance(), 2, RoundingMode.HALF_UP);
        process.setEquipmentUnitCost(equipmentUnitCost);

        // CALCULO DE MANO DE OBRA
        BigDecimal totalEnMano = BigDecimal.ZERO;
        for (ProcessLabor pl : labors) {
            Labor catLab = laborRepository.findById(pl.getLabor().getId())
                    .orElseThrow(() -> new RuntimeException("Mano de Obra no encontrada"));

            pl.setDescriptionSnapshot(catLab.getDescription());
            pl.setDailyWageApplied(catLab.getBaseDailyWage());

            // costo_diario = salario * dotacion
            BigDecimal dailyCost = pl.getDailyWageApplied().multiply(pl.getDotation()).setScale(2, RoundingMode.HALF_UP);
            pl.setDailyCost(dailyCost);
            pl.setProcess(process);

            totalEnMano = totalEnMano.add(dailyCost);
        }

        // costo_por_leyes_sociales = total_en_mano * socialLawsPercentage
        BigDecimal costoLeyesSociales = totalEnMano.multiply(process.getSocialLawsPercentage());
        // costo_diario_personal = total_en_mano + costo_por_leyes_sociales
        BigDecimal costoDiarioPersonal = totalEnMano.add(costoLeyesSociales);

        // costo_unitario_mano_obra = costo_diario_personal / rendimiento
        BigDecimal laborUnitCost = costoDiarioPersonal.divide(process.getPerformance(), 2, RoundingMode.HALF_UP);
        process.setLaborUnitCost(laborUnitCost );

        // TOTALES GENERALES DEL PROCESO
        // costo_unitario_directo = sumatoria de las 3 listas
        BigDecimal directUnitCost = materialUnitCost.add(equipmentUnitCost).add(laborUnitCost);
        process.setDirectUnitCost(directUnitCost);

        // admin_cost_value = directUnitCost * adminPercentage
        BigDecimal adminCostValue = directUnitCost.multiply(process.getAdminPercentage()).setScale(2, RoundingMode.HALF_UP);
        process.setAdminCostValue(adminCostValue);

        // totalUnitCost = directUnitCost + adminCostValue
        BigDecimal totalUnitCost = directUnitCost.add(adminCostValue);
        process.setTotalUnitCost(totalUnitCost);

        // utilityCostValue = totalUnitCost * utilityContingencyPercentage
        BigDecimal utilityCostValue = totalUnitCost.multiply(process.getUtilityContingencyPercentage()).setScale(2, RoundingMode.HALF_UP);
        process.setUtilityCostValue(utilityCostValue);

        // calculatedUnitPrice = totalUnitCost + utilityCostValue
        BigDecimal calculatedUnitPrice = totalUnitCost.add(utilityCostValue);
        process.setCalculatedUnitPrice(calculatedUnitPrice);

        // precio_unitario_propuesto (entero)
        process.setProposedUnitPrice(calculatedUnitPrice.setScale(0, RoundingMode.HALF_UP).intValue());

        // GUARDAR EN LA BASE DE DATOS
        // Guardamos la cabecera primero para que las listas tengan el ID referencial
        Process savedProcess = processRepository.save(process);

        processMaterialRepository.saveAll(materials);
        processEquipmentRepository.saveAll(equipments);
        processLaborRepository.saveAll(labors);

        return savedProcess;
    }

    @Transactional
    public void deleteApu(String serviceRequest) {
        // 1. Verificar que exista
        if (!processRepository.existsById(serviceRequest)) {
            throw new RuntimeException("No se puede eliminar: No se encontró ningún APU con la solicitud " + serviceRequest);
        }

        // 2. Borrar los detalles (Snapshots) primero para liberar las llaves foráneas
        processMaterialRepository.deleteAll(processMaterialRepository.findByProcessServiceRequest(serviceRequest));
        processEquipmentRepository.deleteAll(processEquipmentRepository.findByProcessServiceRequest(serviceRequest));
        processLaborRepository.deleteAll(processLaborRepository.findByProcessServiceRequest(serviceRequest));

        // 3. Borrar la cabecera del proceso
        processRepository.deleteById(serviceRequest);
    }

    public ApuResponseDTO getApuByServiceRequest(String serviceRequest) {
        // 1. Buscar la cabecera principal
        Process process = processRepository.findById(serviceRequest)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún APU con la solicitud: " + serviceRequest));

        // 2. Buscar las 3 listas usando los métodos que acabamos de crear
        List<ProcessMaterial> materials = processMaterialRepository.findByProcessServiceRequest(serviceRequest);
        List<ProcessEquipment> equipments = processEquipmentRepository.findByProcessServiceRequest(serviceRequest);
        List<ProcessLabor> labors = processLaborRepository.findByProcessServiceRequest(serviceRequest);

        // 3. Empaquetar la informacion en el DTO y devolverlo
        return new ApuResponseDTO(process, materials, equipments, labors);
    }

    public List<Process> getAllApus() {
        // findAll() busca todas las cabeceras de la tabla 'processes'
        return processRepository.findAll();
    }

    public List<Process> searchApus(Long companyId, String companyName, String processType, String power, String serial, String brand, boolean exactMatch) {

        // Limpiamos strings vacíos para evitar falsos positivos en el IS NULL
        companyName = (companyName != null && !companyName.trim().isEmpty()) ? companyName : null;
        processType = (processType != null && !processType.trim().isEmpty()) ? processType : null;
        power = (power != null && !power.trim().isEmpty()) ? power : null;
        serial = (serial != null && !serial.trim().isEmpty()) ? serial : null;
        brand = (brand != null && !brand.trim().isEmpty()) ? brand : null;

        // Si no consigue nada, Spring Data JPA devuelve [] automáticamente.
        return processRepository.searchApus(companyId, companyName, processType, power, serial, brand, exactMatch);
    }
}

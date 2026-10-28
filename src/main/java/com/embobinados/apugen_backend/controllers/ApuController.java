package com.embobinados.apugen_backend.controllers;

import com.embobinados.apugen_backend.dto.ApuRequestDTO;
import com.embobinados.apugen_backend.entity.*;
import com.embobinados.apugen_backend.entity.Process;
import com.embobinados.apugen_backend.service.ApuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apus")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite que el frontend en React (puerto 5173) se comunique sin bloqueos CORS
public class ApuController {

    private final ApuService apuService;

    @PostMapping("/generar")
    public ResponseEntity<Process> generarApu(@RequestBody ApuRequestDTO dto) {

        // 1. Mapear la cabecera (Process)
        Process process = new Process();
        process.setServiceRequest(dto.getServiceRequest());
        process.setProcessType(dto.getProcessType());
        process.setPower(dto.getPower());
        process.setSerial(dto.getSerial());
        process.setBrand(dto.getBrand());
        process.setItemQuantity(dto.getItemQuantity());
        process.setPerformance(dto.getPerformance());
        process.setSocialLawsPercentage(dto.getSocialLawsPercentage());
        process.setAdminPercentage(dto.getAdminPercentage());
        process.setUtilityContingencyPercentage(dto.getUtilityContingencyPercentage());

        // Truco JPA: Para guardar relaciones, solo necesitamos instanciar la entidad con su ID
        Company company = new Company();
        company.setId(dto.getCompanyId());
        process.setCompany(company);

        // 2. Mapear la lista de Materiales
        List<ProcessMaterial> materials = dto.getMaterials().stream().map(mDto -> {
            ProcessMaterial pm = new ProcessMaterial();
            Material material = new Material();
            material.setId(mDto.getMaterialId());
            pm.setMaterial(material);
            pm.setQuantity(mDto.getQuantity());
            return pm;
        }).collect(Collectors.toList());

        // 3. Mapear la lista de Equipos
        List<ProcessEquipment> equipments = dto.getEquipments().stream().map(eDto -> {
            ProcessEquipment pe = new ProcessEquipment();
            Equipment equipment = new Equipment();
            equipment.setId(eDto.getEquipmentId());
            pe.setEquipment(equipment);
            pe.setQuantity(eDto.getQuantity());
            return pe;
        }).collect(Collectors.toList());

        // 4. Mapear la lista de Mano de Obra
        List<ProcessLabor> labors = dto.getLabors().stream().map(lDto -> {
            ProcessLabor pl = new ProcessLabor();
            Labor labor = new Labor();
            labor.setId(lDto.getLaborId());
            pl.setLabor(labor);
            pl.setDotation(lDto.getDotation());
            return pl;
        }).collect(Collectors.toList());

        // 5. Ejecutar el cálculo y guardar
        Process savedProcess = apuService.calcAndSaveApu(process, materials, equipments, labors);

        // Devolvemos el APU calculado y guardado con un código HTTP 200 (OK)
        return ResponseEntity.ok(savedProcess);
    }
}
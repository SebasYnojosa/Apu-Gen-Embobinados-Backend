package com.embobinados.apugen_backend.controllers;

import com.embobinados.apugen_backend.entity.Company;
import com.embobinados.apugen_backend.entity.Equipment;
import com.embobinados.apugen_backend.entity.Labor;
import com.embobinados.apugen_backend.entity.Material;
import com.embobinados.apugen_backend.repository.CompanyRepository;
import com.embobinados.apugen_backend.repository.EquipmentRepository;
import com.embobinados.apugen_backend.repository.LaborRepository;
import com.embobinados.apugen_backend.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Previene bloqueos CORS desde React
public class CatalogController {

    // Inyección de dependencias de los repositorios maestros
    private final CompanyRepository companyRepository;
    private final MaterialRepository materialRepository;
    private final EquipmentRepository equipmentRepository;
    private final LaborRepository laborRepository;

    @GetMapping("/empresas")
    public ResponseEntity<List<Company>> getCompanies() {
        // findAll() ejecuta un "SELECT * FROM companies" automáticamente
        return ResponseEntity.ok(companyRepository.findAll());
    }

    // ==========================================
    // CRUD PARA MATERIALES
    // ==========================================

    @GetMapping("/materiales")
    public ResponseEntity<List<Material>> getMaterials() {
        return ResponseEntity.ok(materialRepository.findAll());
    }

    @GetMapping("/materiales/{id}")
    public ResponseEntity<Material> getMaterial(@PathVariable Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el material con ID: " + id));

        return ResponseEntity.ok(material);
    }

    @PostMapping("/materiales")
    public ResponseEntity<Material> crearMaterial(@RequestBody Material material) {
        // Validación crucial por usar IDs manuales
        if (material.getId() == null) {
            throw new RuntimeException("El ID del material es obligatorio.");
        }
        if (materialRepository.existsById(material.getId())) {
            throw new RuntimeException("Ya existe un material registrado con el ID: " + material.getId());
        }
        return ResponseEntity.ok(materialRepository.save(material));
    }

    @PutMapping("/materiales/{id}")
    public ResponseEntity<Material> actualizarMaterial(@PathVariable Long id, @RequestBody Material detalles) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el material con ID: " + id));

        material.setDescription(detalles.getDescription());
        material.setUnit(detalles.getUnit());
        material.setBaseCost(detalles.getBaseCost());

        return ResponseEntity.ok(materialRepository.save(material));
    }

    @DeleteMapping("/materiales/{id}")
    public ResponseEntity<Void> borrarMaterial(@PathVariable Long id) {
        if (!materialRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el material con ID: " + id);
        }
        try {
            materialRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("No se puede borrar el material porque está siendo usado en un APU.");
        }
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // CRUD PARA EQUIPOS
    // ==========================================

    @GetMapping("/equipos")
    public ResponseEntity<List<Equipment>> getEquipments() {
        return ResponseEntity.ok(equipmentRepository.findAll());
    }

    @GetMapping("/equipos/{id}")
    public ResponseEntity<Equipment> getEquipo(@PathVariable Long id) {
        Equipment equipo = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el equipo con ID: " + id));

        return ResponseEntity.ok(equipo);
    }

    @PostMapping("/equipos")
    public ResponseEntity<Equipment> crearEquipo(@RequestBody Equipment equipo) {
        // Validación crucial por usar IDs manuales
        if (equipo.getId() == null) {
            throw new RuntimeException("El ID del equipo es obligatorio.");
        }
        if (equipmentRepository.existsById(equipo.getId())) {
            throw new RuntimeException("Ya existe un equipo registrado con el ID: " + equipo.getId());
        }
        return ResponseEntity.ok(equipmentRepository.save(equipo));
    }

    @PutMapping("/equipos/{id}")
    public ResponseEntity<Equipment> actualizarEquipo(@PathVariable Long id, @RequestBody Equipment detalles) {
        Equipment equipo = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el equipo con ID: " + id));

        equipo.setDescription(detalles.getDescription());
        equipo.setBaseValue(detalles.getBaseValue());
        equipo.setDepreciationFactor(detalles.getDepreciationFactor());

        return ResponseEntity.ok(equipmentRepository.save(equipo));
    }

    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<Void> borrarEquipo(@PathVariable Long id) {
        if (!equipmentRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el equipo con ID: " + id);
        }
        try {
            equipmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("No se puede borrar el equipo porque está siendo usado en un APU.");
        }
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // CRUD PARA MANO DE OBRA (CARGOS)
    // ==========================================

    @GetMapping("/cargos")
    public ResponseEntity<List<Labor>> getLabors() {
        return ResponseEntity.ok(laborRepository.findAll());
    }

    @GetMapping("/cargos/{id}")
    public ResponseEntity<Labor> getCargo(@PathVariable Long id) {
        Labor cargo = laborRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el cargo con ID: " + id));

        return ResponseEntity.ok(cargo);
    }

    @PostMapping("/cargos")
    public ResponseEntity<Labor> crearCargo(@RequestBody Labor cargo) {
        // Validación crucial por usar IDs manuales
        if (cargo.getId() == null) {
            throw new RuntimeException("El ID del cargo es obligatorio.");
        }
        if (laborRepository.existsById(cargo.getId())) {
            throw new RuntimeException("Ya existe un cargo registrado con el ID: " + cargo.getId());
        }
        return ResponseEntity.ok(laborRepository.save(cargo));
    }

    @PutMapping("/cargos/{id}")
    public ResponseEntity<Labor> actualizarCargo(@PathVariable Long id, @RequestBody Labor detalles) {
        Labor cargo = laborRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el equipo con ID: " + id));

        cargo.setDescription(detalles.getDescription());
        cargo.setBaseDailyWage(detalles.getBaseDailyWage());

        return ResponseEntity.ok(laborRepository.save(cargo));
    }

    @DeleteMapping("/cargos/{id}")
    public ResponseEntity<Void> borrarCargo(@PathVariable Long id) {
        if (!laborRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el cargo con ID: " + id);
        }
        try {
            laborRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("No se puede borrar el cargo porque está siendo usado en un APU.");
        }
        return ResponseEntity.noContent().build();
    }
}
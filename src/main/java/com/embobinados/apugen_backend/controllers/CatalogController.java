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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/materiales")
    public ResponseEntity<List<Material>> getMaterials() {
        return ResponseEntity.ok(materialRepository.findAll());
    }

    @GetMapping("/equipos")
    public ResponseEntity<List<Equipment>> getEquipments() {
        return ResponseEntity.ok(equipmentRepository.findAll());
    }

    @GetMapping("/cargos")
    public ResponseEntity<List<Labor>> getLabors() {
        return ResponseEntity.ok(laborRepository.findAll());
    }
}
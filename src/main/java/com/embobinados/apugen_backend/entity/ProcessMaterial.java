package com.embobinados.apugen_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "process_materials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "process_service_request", nullable = false)
    private Process process;

    // Referencia al catálogo maestro
    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    // Fotografía (Snapshot) de los datos al momento de crear el APU
    @Column(nullable = false, length = 255)
    private String descriptionSnapshot;

    @Column(length = 20)
    private String unitSnapshot;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal costApplied; // El costo que se usó (puede ser el base o uno modificado por el cliente)

    // Variables del proceso
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal amount; // quantity * costApplied
}
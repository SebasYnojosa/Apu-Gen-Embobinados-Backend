package com.embobinados.apugen_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "process_equipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "process_service_request", nullable = false)
    private Process process;

    // Referencia al catálogo maestro
    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    // Fotografía (Snapshot)
    @Column(nullable = false, length = 255)
    private String descriptionSnapshot;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal valueApplied;

    @Column(name = "depreciation_factor_applied", precision = 12, scale = 5, nullable = false)
    private BigDecimal depreciationFactorApplied;

    // Variables del proceso
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "daily_use_cost", precision = 12, scale = 2, nullable = false)
    private BigDecimal dailyUseCost; // valueApplied * depreciationFactorApplied
}
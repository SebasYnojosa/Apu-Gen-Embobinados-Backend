package com.embobinados.apugen_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "process_labors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessLabor {
    @Id
    private Long id; // ID Manual

    @ManyToOne
    @JoinColumn(name = "process_service_request", nullable = false)
    private Process process;

    // Referencia al catálogo maestro
    @ManyToOne
    @JoinColumn(name = "labor_id", nullable = false)
    private Labor labor;

    // Fotografía (Snapshot)
    @Column(nullable = false, length = 255)
    private String descriptionSnapshot;

    @Column(name = "daily_wage_applied", precision = 12, scale = 2, nullable = false)
    private BigDecimal dailyWageApplied;

    // Variables del proceso
    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal dotation; // Porcentaje (Ej: 1.00 para 100%)

    @Column(name = "daily_cost", precision = 12, scale = 2, nullable = false)
    private BigDecimal dailyCost; // dailyWageApplied * dotation
}
package com.embobinados.apugen_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "equipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    @Id
    private Long id; // ID Manual

    @Column(nullable = false, length = 255)
    private String description;

    @Column(name = "base_value", precision = 12, scale = 2, nullable = false)
    private BigDecimal baseValue;

    // Precisión de 5 decimales solicitada
    @Column(name = "depreciation_factor", precision = 12, scale = 5, nullable = false)
    private BigDecimal depreciationFactor;
}
package com.embobinados.apugen_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "materials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    @Id
    private Long id; // ID Manual

    @Column(nullable = false, length = 255)
    private String description;

    @Column(length = 20)
    private String unit;

    @Column(name = "base_cost", precision = 12, scale = 2, nullable = false)
    private BigDecimal baseCost;
}
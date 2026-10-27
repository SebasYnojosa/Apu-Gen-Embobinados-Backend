package com.embobinados.apugen_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "labors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Labor {
    @Id
    private Long id; // ID Manual

    @Column(nullable = false, length = 255)
    private String description; // Ej: "Técnico Embobinador Principal"

    @Column(name = "base_daily_wage", precision = 12, scale = 2, nullable = false)
    private BigDecimal baseDailyWage;
}
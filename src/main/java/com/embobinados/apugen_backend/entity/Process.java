package com.embobinados.apugen_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "processes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Process {
    @Id
    @Column(name = "service_request", length = 50)
    private String serviceRequest; // ID Manual (Solicitud de Servicio)

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "process_type", nullable = false, length = 100)
    private String processType;

    @Column(length = 50)
    private String power;

    @Column(length = 100)
    private String serial;

    @Column(length = 100)
    private String brand;

    // --- VARIABLES DEL APU (Atributos del Proceso) ---
    @Column(name = "item_quantity", precision = 12, scale = 2)
    private BigDecimal itemQuantity; // Cantidad de veces que se realizará el item

    @Column(precision = 12, scale = 2)
    private BigDecimal performance; // Rendimiento del item

    @Column(name = "social_laws_percentage", precision = 5, scale = 2)
    private BigDecimal socialLawsPercentage; // Porcentaje Leyes Sociales (Ej: 0.75 para 75%)

    @Column(name = "admin_percentage", precision = 5, scale = 2)
    private BigDecimal adminPercentage; // Porcentaje Administración

    @Column(name = "utility_contingency_percentage", precision = 5, scale = 2)
    private BigDecimal utilityContingencyPercentage; // Porcentaje Utilidad e Imprevistos

    // --- TOTALES CALCULADOS (Se guardan para congelar el presupuesto) ---
    @Column(name = "material_unit_cost", precision = 12, scale = 2)
    private BigDecimal materialUnitCost; // Costo Unitario de Materiales

    @Column(name = "equipment_unit_cost", precision = 12, scale = 2)
    private BigDecimal equipmentUnitCost; // Costo Unitario de Equipos

    @Column(name = "labor_unit_cost", precision = 12, scale = 2)
    private BigDecimal laborUnitCost; // Costo Unitario de Mano de Obra

    @Column(name = "direct_unit_cost", precision = 14, scale = 2)
    private BigDecimal directUnitCost; // Sumatoria de los 3 anteriores

    @Column(name = "admin_cost_value", precision = 14, scale = 2)
    private BigDecimal adminCostValue; // directUnitCost * adminPercentage

    @Column(name = "total_unit_cost", precision = 14, scale = 2)
    private BigDecimal totalUnitCost; // directUnitCost + adminCostValue

    @Column(name = "utility_cost_value", precision = 14, scale = 2)
    private BigDecimal utilityCostValue; // totalUnitCost * utilityContingencyPercentage

    @Column(name = "calculated_unit_price", precision = 14, scale = 2)
    private BigDecimal calculatedUnitPrice; // totalUnitCost + utilityCostValue

    // El precio propuesto debe ser entero según la regla
    @Column(name = "proposed_unit_price")
    private Integer proposedUnitPrice;
}
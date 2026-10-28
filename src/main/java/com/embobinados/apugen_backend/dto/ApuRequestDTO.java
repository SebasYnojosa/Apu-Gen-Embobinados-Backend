package com.embobinados.apugen_backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ApuRequestDTO {
    // Cabecera
    private String serviceRequest;
    private Long companyId;
    private String processType;
    private String power;
    private String serial;
    private String brand;

    // Variables del proceso
    private BigDecimal itemQuantity;
    private BigDecimal performance;
    private BigDecimal socialLawsPercentage;
    private BigDecimal adminPercentage;
    private BigDecimal utilityContingencyPercentage;

    // Listas de detalles
    private List<MaterialDTO> materials;
    private List<EquipmentDTO> equipments;
    private List<LaborDTO> labors;

    // Clases anidadas para estructurar las listas del JSON
    @Data
    public static class MaterialDTO {
        private Long materialId;
        private BigDecimal quantity;
    }

    @Data
    public static class EquipmentDTO {
        private Long equipmentId;
        private BigDecimal quantity;
    }

    @Data
    public static class LaborDTO {
        private Long laborId;
        private BigDecimal dotation;
    }
}

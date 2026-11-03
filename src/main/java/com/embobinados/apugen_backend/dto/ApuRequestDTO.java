package com.embobinados.apugen_backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ApuRequestDTO {
    // Cabecera
    private String serviceRequest;
    private String companyId;
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
        private String materialId;
        private BigDecimal quantity;
    }

    @Data
    public static class EquipmentDTO {
        private String equipmentId;
        private BigDecimal quantity;
    }

    @Data
    public static class LaborDTO {
        private String laborId;
        private BigDecimal dotation;
    }
}

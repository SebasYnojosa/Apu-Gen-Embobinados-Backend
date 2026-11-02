package com.embobinados.apugen_backend.dto;

import com.embobinados.apugen_backend.entity.Process;
import com.embobinados.apugen_backend.entity.ProcessEquipment;
import com.embobinados.apugen_backend.entity.ProcessLabor;
import com.embobinados.apugen_backend.entity.ProcessMaterial;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApuResponseDTO {
    private Process process;
    private List<ProcessMaterial> materials;
    private List<ProcessEquipment> equipments;
    private List<ProcessLabor> labors;
}
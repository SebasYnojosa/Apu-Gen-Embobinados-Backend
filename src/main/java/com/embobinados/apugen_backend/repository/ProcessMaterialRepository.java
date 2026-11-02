package com.embobinados.apugen_backend.repository;
import com.embobinados.apugen_backend.entity.ProcessMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProcessMaterialRepository extends JpaRepository<ProcessMaterial, Long> {
    List<ProcessMaterial> findByProcessServiceRequest(String serviceRequest);
}
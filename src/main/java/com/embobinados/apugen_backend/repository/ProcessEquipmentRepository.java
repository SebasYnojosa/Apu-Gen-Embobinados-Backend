package com.embobinados.apugen_backend.repository;
import com.embobinados.apugen_backend.entity.ProcessEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProcessEquipmentRepository extends JpaRepository<ProcessEquipment, String> {
    List<ProcessEquipment> findByProcessServiceRequest(String serviceRequest);
}
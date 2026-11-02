package com.embobinados.apugen_backend.repository;
import com.embobinados.apugen_backend.entity.ProcessLabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProcessLaborRepository extends JpaRepository<ProcessLabor, Long> {
    List<ProcessLabor> findByProcessServiceRequest(String serviceRequest);
}
package com.embobinados.apugen_backend.repository;
import com.embobinados.apugen_backend.entity.ProcessEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessEquipmentRepository extends JpaRepository<ProcessEquipment, Long> {}
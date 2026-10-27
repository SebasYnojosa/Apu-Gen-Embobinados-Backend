package com.embobinados.apugen_backend.repository;
import com.embobinados.apugen_backend.entity.ProcessMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessMaterialRepository extends JpaRepository<ProcessMaterial, Long> {}
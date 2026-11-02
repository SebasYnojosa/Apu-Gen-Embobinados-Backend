package com.embobinados.apugen_backend.repository;

import com.embobinados.apugen_backend.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, String> {

    @Query("""
        SELECT p FROM Process p 
        WHERE (:companyId IS NULL OR p.company.id = :companyId) 
          AND (:companyName IS NULL OR (
                (:exactMatch = true AND normalizar(p.company.name) = normalizar(cast(:companyName as String))) OR 
                (:exactMatch = false AND normalizar(p.company.name) LIKE concat('%', normalizar(cast(:companyName as String)), '%'))
              ))
          AND (:processType IS NULL OR (
                (:exactMatch = true AND normalizar(p.processType) = normalizar(cast(:processType as String))) OR 
                (:exactMatch = false AND normalizar(p.processType) LIKE concat('%', normalizar(cast(:processType as String)), '%'))
              ))
          AND (:power IS NULL OR (
                (:exactMatch = true AND normalizar(p.power) = normalizar(cast(:power as String))) OR 
                (:exactMatch = false AND normalizar(p.power) LIKE concat('%', normalizar(cast(:power as String)), '%'))
              ))
          AND (:serial IS NULL OR (
                (:exactMatch = true AND normalizar(p.serial) = normalizar(cast(:serial as String))) OR 
                (:exactMatch = false AND normalizar(p.serial) LIKE concat('%', normalizar(cast(:serial as String)), '%'))
              ))
          AND (:brand IS NULL OR (
                (:exactMatch = true AND normalizar(p.brand) = normalizar(cast(:brand as String))) OR 
                (:exactMatch = false AND normalizar(p.brand) LIKE concat('%', normalizar(cast(:brand as String)), '%'))
              ))
    """)
    List<Process> searchApus(
            @Param("companyId") Long companyId,
            @Param("companyName") String companyName,
            @Param("processType") String processType,
            @Param("power") String power,
            @Param("serial") String serial,
            @Param("brand") String brand,
            @Param("exactMatch") boolean exactMatch
    );
}
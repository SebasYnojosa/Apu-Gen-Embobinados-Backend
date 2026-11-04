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
    WHERE (:companyId IS NULL OR normalizar(cast(p.company.id as String)) LIKE normalizar(cast(:companyId as String)))
      AND (:companyName IS NULL OR normalizar(p.company.name) LIKE normalizar(cast(:companyName as String)))
      AND (:mainProcess IS NULL OR normalizar(p.mainProcess) LIKE normalizar(cast(:mainProcess as String)))
      AND (:description IS NULL OR normalizar(p.description) LIKE normalizar(cast(:description as String)))
      AND (:power IS NULL OR normalizar(p.power) LIKE normalizar(cast(:power as String)))
      AND (:serial IS NULL OR normalizar(p.serial) LIKE normalizar(cast(:serial as String)))
      AND (:brand IS NULL OR normalizar(p.brand) LIKE normalizar(cast(:brand as String)))
      AND (:location IS NULL OR normalizar(p.location) LIKE normalizar(cast(:location as String)))
""")
    List<Process> searchApus(
            @Param("companyId") String companyId,
            @Param("companyName") String companyName,
            @Param("mainProcess") String mainProcess,
            @Param("description") String description,
            @Param("power") String power,
            @Param("serial") String serial,
            @Param("brand") String brand,
            @Param("location") String location
    );
}
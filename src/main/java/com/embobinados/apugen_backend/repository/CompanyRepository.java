package com.embobinados.apugen_backend.repository;

import com.embobinados.apugen_backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query("SELECT c FROM Company WHERE normalizar(c.name) LIKE normalizar(cast(:nombre as String))")
    List<Company> buscarPorNombre(@Param("nombre") String nombre);
}
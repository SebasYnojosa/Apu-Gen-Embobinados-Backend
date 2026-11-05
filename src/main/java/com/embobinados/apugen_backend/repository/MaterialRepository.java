package com.embobinados.apugen_backend.repository;
import com.embobinados.apugen_backend.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {
    @Query("SELECT m FROM Material m WHERE normalizar(m.description) LIKE normalizar(cast(:descripcion as String))")
    List<Material> buscarPorDescripcion(@Param("descripcion") String descripcion);
}
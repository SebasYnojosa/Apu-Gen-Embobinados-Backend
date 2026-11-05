package com.embobinados.apugen_backend.repository;
import com.embobinados.apugen_backend.entity.Labor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LaborRepository extends JpaRepository<Labor, String> {
    @Query("SELECT l FROM Labor l WHERE normalizar(l.description) LIKE normalizar(cast(:descripcion as String))")
    List<Labor> buscarPorDescripcion(@Param("descripcion") String descripcion);
}
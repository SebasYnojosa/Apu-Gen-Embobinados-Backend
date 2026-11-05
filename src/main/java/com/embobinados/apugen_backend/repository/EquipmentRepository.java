package com.embobinados.apugen_backend.repository;
import com.embobinados.apugen_backend.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    @Query("SELECT e FROM Equipment e WHERE normalizar(e.description) LIKE normalizar(cast(:descripcion as String))")
    List<Equipment> buscarPorDescripcion(@Param("descripcion") String descripcion);
}
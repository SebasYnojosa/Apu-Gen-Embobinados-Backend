package com.embobinados.apugen_backend.repository;
import com.embobinados.apugen_backend.entity.Labor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaborRepository extends JpaRepository<Labor, Long> {}
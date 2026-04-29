package com.example.gendoc.repository;

import com.example.gendoc.entity.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
    List<Seance> findByClasseId(Long classeId);
}

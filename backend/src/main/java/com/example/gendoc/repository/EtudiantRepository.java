package com.example.gendoc.repository;

import com.example.gendoc.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByUtilisateurId(Long utilisateurId);
}

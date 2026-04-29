package com.example.gendoc.repository;

import com.example.gendoc.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByEtudiantIdAndSemestre(Long etudiantId, String semestre);
}

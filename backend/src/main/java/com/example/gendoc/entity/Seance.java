package com.example.gendoc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seances")
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String jour; // LUNDI, MARDI, etc.

    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;

    @Column(name = "heure_fin", nullable = false)
    private LocalTime heureFin;

    @ManyToOne
    @JoinColumn(name = "matiere_id", nullable = false)
    private Matiere matiere;

    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "professeur_id", nullable = false)
    private Utilisateur professeur;

    @Column(nullable = false)
    private String salle;
}

package com.example.gendoc.config;

import com.example.gendoc.entity.*;
import com.example.gendoc.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final FiliereRepository filiereRepository;
    private final ClasseRepository classeRepository;
    private final MatiereRepository matiereRepository;
    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialisation des Rôles
        Role roleAdmin = createRoleIfNotFound("ROLE_ADMIN");
        Role roleProf = createRoleIfNotFound("ROLE_PROF");
        Role roleEtudiant = createRoleIfNotFound("ROLE_ETUDIANT");

        // Création de l'Administrateur spécifié par l'utilisateur
        String adminEmail = "mahmoudag2003@gmail.com";
        if (utilisateurRepository.findByEmail(adminEmail).isEmpty()) {
            Utilisateur admin = Utilisateur.builder()
                    .nom("Admin")
                    .prenom("Mahmoud")
                    .email(adminEmail)
                    .motDePasse(passwordEncoder.encode("admin123"))
                    .role(roleAdmin)
                    .build();
            utilisateurRepository.save(admin);
            System.out.println("✅ Administrateur créé : " + adminEmail + " / MDP : admin123");
        }

        // Création des jeux d'essai uniquement si la base est vide (basé sur Filiere)
        if (filiereRepository.count() == 0) {
            // Filières
            Filiere info = filiereRepository.save(new Filiere(null, "Informatique", "Développement logiciel et réseaux"));
            Filiere eco = filiereRepository.save(new Filiere(null, "Économie", "Gestion et finance"));

            // Classes
            Classe l1Info = classeRepository.save(new Classe(null, "Licence 1 Info", "L1", info));
            Classe l2Info = classeRepository.save(new Classe(null, "Licence 2 Info", "L2", info));
            Classe m1Eco = classeRepository.save(new Classe(null, "Master 1 Eco", "M1", eco));

            // Matières
            Matiere math = matiereRepository.save(new Matiere(null, "Mathématiques Algorithmiques", 5));
            Matiere java = matiereRepository.save(new Matiere(null, "Programmation Orientée Objet (Java)", 6));
            Matiere macro = matiereRepository.save(new Matiere(null, "Macroéconomie", 4));

            // Utilisateur Professeur
            Utilisateur prof1 = Utilisateur.builder()
                    .nom("Dupont")
                    .prenom("Jean")
                    .email("jean.dupont@gen.doc")
                    .motDePasse(passwordEncoder.encode("prof123"))
                    .role(roleProf)
                    .build();
            utilisateurRepository.save(prof1);

            // Utilisateur Étudiant
            Utilisateur uEtu1 = Utilisateur.builder()
                    .nom("Martin")
                    .prenom("Sophie")
                    .email("sophie.martin@gen.doc")
                    .motDePasse(passwordEncoder.encode("etudiant123"))
                    .role(roleEtudiant)
                    .build();
            utilisateurRepository.save(uEtu1);

            // Étudiant
            Etudiant etudiant1 = new Etudiant(null, "ETU-2024-001", LocalDate.of(2003, 5, 15), null, uEtu1, l1Info);
            etudiantRepository.save(etudiant1);
            
            System.out.println("✅ Données d'essai ajoutées avec succès.");
        }
    }

    private Role createRoleIfNotFound(String roleName) {
        return roleRepository.findByNomRole(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setNomRole(roleName);
            return roleRepository.save(role);
        });
    }
}

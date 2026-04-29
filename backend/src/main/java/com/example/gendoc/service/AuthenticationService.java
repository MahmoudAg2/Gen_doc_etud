package com.example.gendoc.service;

import com.example.gendoc.dto.AuthenticationRequest;
import com.example.gendoc.dto.AuthenticationResponse;
import com.example.gendoc.dto.RegisterRequest;
import com.example.gendoc.entity.Role;
import com.example.gendoc.entity.Utilisateur;
import com.example.gendoc.repository.RoleRepository;
import com.example.gendoc.repository.UtilisateurRepository;
import com.example.gendoc.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UtilisateurRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = roleRepository.findByNomRole(request.getRoleNom())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setNomRole(request.getRoleNom());
                    return roleRepository.save(newRole);
                });

        var user = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .role(role)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().getNomRole())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().getNomRole())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .build();
    }
}

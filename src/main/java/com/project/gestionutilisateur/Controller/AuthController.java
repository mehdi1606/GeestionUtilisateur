
package com.project.gestionutilisateur.Controller;

import com.project.gestionutilisateur.Dto.AuthRequest;
import com.project.gestionutilisateur.Dto.AuthResponse;
import com.project.gestionutilisateur.Dto.CreateUserRequest;
import com.project.gestionutilisateur.Entity.Personne;
import com.project.gestionutilisateur.Entity.Role;
import com.project.gestionutilisateur.Entity.User;
import com.project.gestionutilisateur.Repository.RoleRepository;
import com.project.gestionutilisateur.Repository.UserRepository;
import com.project.gestionutilisateur.Service.AuthService;
import com.project.gestionutilisateur.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }
    @PostMapping("/create-first-admin")
    public ResponseEntity<?> createFirstAdmin(@RequestBody CreateUserRequest request) {
        try {
            // Vérifier si un admin existe déjà
            boolean adminExists = userRepository.existsByLogin("admin") ||
                    roleRepository.findByLibelle("ROLE_ADMIN")
                            .map(role -> !role.getUsers().isEmpty())
                            .orElse(false);

            if (adminExists) {
                return ResponseEntity.badRequest().body(Map.of("error", "Un admin existe déjà"));
            }

            // Créer le rôle ADMIN s'il n'existe pas
            Role adminRole = roleRepository.findByLibelle("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

            // Créer la personne
            Personne personne = new Personne(
                    request.getNom(), request.getPrenom(),
                    request.getTelephone(), request.getEmail()
            );

            // Créer l'admin
            User admin = new User(
                    request.getLogin(),
                    passwordEncoder.encode(request.getPassword()),
                    personne,
                    Set.of(adminRole)
            );

            User savedAdmin = userRepository.save(admin);

            return ResponseEntity.ok(Map.of(
                    "message", "Premier admin créé avec succès",
                    "admin_id", savedAdmin.getId(),
                    "login", savedAdmin.getLogin()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody CreateUserRequest request) {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam String token) {
        try {
            String newToken = authService.refreshToken(token);
            return ResponseEntity.ok(Map.of("token", newToken));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String token) {
        try {
            authService.logout(token);
            return ResponseEntity.ok(Map.of("message", "Déconnexion réussie"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok(Map.of("valid", isValid));
    }
}

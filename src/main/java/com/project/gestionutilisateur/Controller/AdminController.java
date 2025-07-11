package com.project.gestionutilisateur.Controller;

import com.project.gestionutilisateur.Service.PersonneService;
import com.project.gestionutilisateur.Service.RoleService;
import com.project.gestionutilisateur.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PersonneService personneService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalUsers", userService.getAllUsers().size());
        stats.put("totalPersonnes", personneService.getAllPersonnes().size());
        stats.put("totalRoles", roleService.getAllRoles().size());

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/users/recent")
    public ResponseEntity<Map<String, Object>> getRecentUsers() {
        // Dans une implémentation réelle, on filtrerait par date récente
        Map<String, Object> response = new HashMap<>();
        response.put("recentUsers", userService.getAllUsers());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/{userId}/toggle-status")
    public ResponseEntity<String> toggleUserStatus(@PathVariable Long userId) {
        // Implémentation pour activer/désactiver un utilisateur
        // Pour l'instant, on retourne juste un message
        return ResponseEntity.ok("Statut utilisateur modifié");
    }
}

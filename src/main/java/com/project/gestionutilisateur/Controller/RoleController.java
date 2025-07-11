package com.project.gestionutilisateur.Controller;

import com.project.gestionutilisateur.Dto.RoleDto;
import com.project.gestionutilisateur.Service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        RoleDto role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

    @GetMapping("/by-libelle/{libelle}")
    public ResponseEntity<RoleDto> getRoleByLibelle(@PathVariable String libelle) {
        RoleDto role = roleService.getRoleByLibelle(libelle);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id,
                                              @Valid @RequestBody RoleDto roleDto) {
        RoleDto updatedRole = roleService.updateRole(id, roleDto);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{libelle}")
    public ResponseEntity<Boolean> checkRoleExists(@PathVariable String libelle) {
        boolean exists = roleService.existsByLibelle(libelle);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/by-libelles")
    public ResponseEntity<List<RoleDto>> getRolesByLibelles(@RequestBody List<String> libelles) {
        List<RoleDto> roles = roleService.getRolesByLibelles(libelles);
        return ResponseEntity.ok(roles);
    }
}

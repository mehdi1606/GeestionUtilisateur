package com.project.gestionutilisateur.Controller;


import com.project.gestionutilisateur.Dto.PersonneDto;
import com.project.gestionutilisateur.Service.PersonneService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnes")
@CrossOrigin(origins = "*")
public class PersonneController {

    @Autowired
    private PersonneService personneService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PersonneDto>> getAllPersonnes() {
        List<PersonneDto> personnes = personneService.getAllPersonnes();
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PersonneDto> getPersonneById(@PathVariable Long id) {
        PersonneDto personne = personneService.getPersonneById(id);
        return ResponseEntity.ok(personne);
    }

    @GetMapping("/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")
    public ResponseEntity<PersonneDto> getPersonneByEmail(@PathVariable String email) {
        PersonneDto personne = personneService.getPersonneByEmail(email);
        return ResponseEntity.ok(personne);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<PersonneDto>> searchPersonnes(
            @RequestParam String nom,
            @RequestParam String prenom) {
        List<PersonneDto> personnes = personneService.findByNomAndPrenom(nom, prenom);
        return ResponseEntity.ok(personnes);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PersonneDto> createPersonne(@Valid @RequestBody PersonneDto personneDto) {
        PersonneDto createdPersonne = personneService.createPersonne(personneDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonne);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PersonneDto> updatePersonne(@PathVariable Long id,
                                                      @Valid @RequestBody PersonneDto personneDto) {
        PersonneDto updatedPersonne = personneService.updatePersonne(id, personneDto);
        return ResponseEntity.ok(updatedPersonne);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = personneService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}

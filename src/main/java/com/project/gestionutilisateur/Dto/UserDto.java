package com.project.gestionutilisateur.Dto;

import java.time.LocalDateTime;
import java.util.Set;

public class UserDto {
    private Long id;
    private String login;
    private LocalDateTime derniereConnexion;
    private PersonneDto personne;
    private Set<RoleDto> roles;

    // Constructeurs
    public UserDto() {}

    public UserDto(Long id, String login, LocalDateTime derniereConnexion, PersonneDto personne, Set<RoleDto> roles) {
        this.id = id;
        this.login = login;
        this.derniereConnexion = derniereConnexion;
        this.personne = personne;
        this.roles = roles;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public LocalDateTime getDerniereConnexion() { return derniereConnexion; }
    public void setDerniereConnexion(LocalDateTime derniereConnexion) { this.derniereConnexion = derniereConnexion; }

    public PersonneDto getPersonne() { return personne; }
    public void setPersonne(PersonneDto personne) { this.personne = personne; }

    public Set<RoleDto> getRoles() { return roles; }
    public void setRoles(Set<RoleDto> roles) { this.roles = roles; }
}

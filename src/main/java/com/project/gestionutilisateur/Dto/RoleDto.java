package com.project.gestionutilisateur.Dto;


public class RoleDto {
    private Long id;
    private String libelle;

    // Constructeurs
    public RoleDto() {}

    public RoleDto(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}

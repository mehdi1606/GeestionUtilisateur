package com.project.gestionutilisateur.Dto;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    @NotBlank(message = "Le login est obligatoire")
    private String login;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    // Constructeurs
    public AuthRequest() {}

    public AuthRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Getters et Setters
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

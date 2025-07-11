package com.project.gestionutilisateur.Exception;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String login) {
        super("Utilisateur non trouvé avec le login : " + login);
    }

    public UserNotFoundException(Long id) {
        super("Utilisateur non trouvé avec l'ID : " + id);
    }
}

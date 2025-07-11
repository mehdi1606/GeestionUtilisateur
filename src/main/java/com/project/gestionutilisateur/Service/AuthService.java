package com.project.gestionutilisateur.Service;


import com.project.gestionutilisateur.Dto.AuthRequest;
import com.project.gestionutilisateur.Dto.AuthResponse;
import com.project.gestionutilisateur.Dto.CreateUserRequest;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
    AuthResponse register(CreateUserRequest request);
    boolean validateToken(String token);
    String refreshToken(String token);
    void logout(String token);
}

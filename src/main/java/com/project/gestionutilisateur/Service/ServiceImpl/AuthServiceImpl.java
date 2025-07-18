package com.project.gestionutilisateur.Service.ServiceImpl;

import com.project.gestionutilisateur.Dto.AuthRequest;
import com.project.gestionutilisateur.Dto.AuthResponse;
import com.project.gestionutilisateur.Dto.CreateUserRequest;
import com.project.gestionutilisateur.Dto.UserDto;
import com.project.gestionutilisateur.Service.AuthService;
import com.project.gestionutilisateur.Service.UserService;
import com.project.gestionutilisateur.Util.DtoMapper;
import com.project.gestionutilisateur.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        try {
            // Authentifier l'utilisateur
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getLogin(),
                            authRequest.getPassword()
                    )
            );

            // Générer le token JWT
            String token = jwtTokenProvider.generateToken(authentication);

            // Récupérer les informations de l'utilisateur
            UserDto userDto = userService.getUserByLogin(authRequest.getLogin());

            // Mettre à jour la dernière connexion
            userService.updateLastConnection(authRequest.getLogin());

            return new AuthResponse(token, userDto);

        } catch (BadCredentialsException e) {
            throw new com.project.gestionutilisateur.Exception.AuthenticationException("Login ou mot de passe incorrect");
        } catch (Exception e) {
            throw new com.project.gestionutilisateur.Exception.AuthenticationException("Erreur lors de l'authentification: " + e.getMessage());
        }
    }

    @Override
    public AuthResponse register(CreateUserRequest request) {
        // Créer l'utilisateur
        UserDto userDto = userService.createUser(request);

        // Authentifier automatiquement après création
        AuthRequest authRequest = new AuthRequest(request.getLogin(), request.getPassword());
        return login(authRequest);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String refreshToken(String token) {
        if (jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsernameFromToken(token);
            // Pour générer un nouveau token, nous devons créer une nouvelle authentification
            // Ici, nous retournons simplement un nouveau token pour le même utilisateur
            UserDto user = userService.getUserByLogin(username);
            // Créer une authentification simple pour générer le token
            Authentication auth = new UsernamePasswordAuthenticationToken(username, null);
            return jwtTokenProvider.generateToken(auth);
        }
        throw new RuntimeException("Token invalide pour le rafraîchissement");
    }

    @Override
    public void logout(String token) {
        // Dans une implémentation complète, on pourrait ajouter le token à une blacklist
        // Pour l'instant, on se contente de valider que le token est valide
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Token invalide");
        }
        // Le logout côté client se fait en supprimant le token du stockage local
    }
}

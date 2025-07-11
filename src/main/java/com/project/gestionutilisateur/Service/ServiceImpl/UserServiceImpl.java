package com.project.gestionutilisateur.Service.ServiceImpl;

import com.project.gestionutilisateur.Dto.CreateUserRequest;
import com.project.gestionutilisateur.Dto.UserDto;
import com.project.gestionutilisateur.Entity.Personne;
import com.project.gestionutilisateur.Entity.Role;
import com.project.gestionutilisateur.Entity.User;
import com.project.gestionutilisateur.Exception.ResourceNotFoundException;
import com.project.gestionutilisateur.Repository.RoleRepository;
import com.project.gestionutilisateur.Repository.UserRepository;
import com.project.gestionutilisateur.Service.UserService;
import com.project.gestionutilisateur.Util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public UserDto createUser(CreateUserRequest request) {
        // Vérifier si le login existe déjà
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new IllegalArgumentException("Login déjà utilisé : " + request.getLogin());
        }

        // Créer la personne
        Personne personne = new Personne(
                request.getNom(),
                request.getPrenom(),
                request.getTelephone(),
                request.getEmail()
        );

        // Récupérer le rôle USER par défaut
        Role roleUser = roleRepository.findByLibelle("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Rôle USER non trouvé"));

        // Créer l'utilisateur
        User user = new User(
                request.getLogin(),
                passwordEncoder.encode(request.getPassword()),
                personne,
                Set.of(roleUser)
        );

        // Sauvegarder
        User savedUser = userRepository.save(user);

        return dtoMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findByIdWithPersonne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));

        return dtoMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByLogin(String login) {
        User user = userRepository.findByLoginWithRoles(login)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec le login : " + login));

        return dtoMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAllWithRolesAndPersonne();
        return users.stream()
                .map(dtoMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, CreateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));

        // Vérifier si le nouveau login n'existe pas déjà (sauf pour cet utilisateur)
        if (!existingUser.getLogin().equals(request.getLogin()) &&
                userRepository.existsByLogin(request.getLogin())) {
            throw new IllegalArgumentException("Login déjà utilisé : " + request.getLogin());
        }

        // Mettre à jour les informations
        existingUser.setLogin(request.getLogin());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Mettre à jour la personne
        Personne personne = existingUser.getPersonne();
        personne.setNom(request.getNom());
        personne.setPrenom(request.getPrenom());
        personne.setEmail(request.getEmail());
        personne.setTelephone(request.getTelephone());

        User updatedUser = userRepository.save(existingUser);

        return dtoMapper.toUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public void updateLastConnection(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé : " + login));

        user.setDerniereConnexion(LocalDateTime.now());
        userRepository.save(user);
    }
}

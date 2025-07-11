package com.project.gestionutilisateur.Service;





import com.project.gestionutilisateur.Dto.CreateUserRequest;
import com.project.gestionutilisateur.Dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(CreateUserRequest request);
    UserDto getUserById(Long id);
    UserDto getUserByLogin(String login);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, CreateUserRequest request);
    void deleteUser(Long id);
    boolean existsByLogin(String login);
    void updateLastConnection(String login);
}

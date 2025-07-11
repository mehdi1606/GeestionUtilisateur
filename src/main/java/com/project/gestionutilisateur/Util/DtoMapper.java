package com.project.gestionutilisateur.Util;



import com.project.gestionutilisateur.Dto.PersonneDto;
import com.project.gestionutilisateur.Dto.RoleDto;
import com.project.gestionutilisateur.Dto.UserDto;
import com.project.gestionutilisateur.Entity.Personne;
import com.project.gestionutilisateur.Entity.Role;
import com.project.gestionutilisateur.Entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    // ===== USER MAPPING =====
    public UserDto toUserDto(User user) {
        if (user == null) return null;

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setDerniereConnexion(user.getDerniereConnexion());

        // Mapper la personne
        if (user.getPersonne() != null) {
            dto.setPersonne(toPersonneDto(user.getPersonne()));
        }

        // Mapper les rôles
        if (user.getRoles() != null) {
            Set<RoleDto> roleDtos = user.getRoles().stream()
                    .map(this::toRoleDto)
                    .collect(Collectors.toSet());
            dto.setRoles(roleDtos);
        }

        return dto;
    }

    public User toUserEntity(UserDto dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setLogin(dto.getLogin());
        user.setDerniereConnexion(dto.getDerniereConnexion());

        // Mapper la personne
        if (dto.getPersonne() != null) {
            user.setPersonne(toPersonneEntity(dto.getPersonne()));
        }

        // Mapper les rôles
        if (dto.getRoles() != null) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(this::toRoleEntity)
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return user;
    }

    // ===== PERSONNE MAPPING =====
    public PersonneDto toPersonneDto(Personne personne) {
        if (personne == null) return null;

        PersonneDto dto = new PersonneDto();
        dto.setId(personne.getId());
        dto.setNom(personne.getNom());
        dto.setPrenom(personne.getPrenom());
        dto.setTelephone(personne.getTelephone());
        dto.setEmail(personne.getEmail());

        return dto;
    }

    public Personne toPersonneEntity(PersonneDto dto) {
        if (dto == null) return null;

        Personne personne = new Personne();
        personne.setId(dto.getId());
        personne.setNom(dto.getNom());
        personne.setPrenom(dto.getPrenom());
        personne.setTelephone(dto.getTelephone());
        personne.setEmail(dto.getEmail());

        return personne;
    }

    // ===== ROLE MAPPING =====
    public RoleDto toRoleDto(Role role) {
        if (role == null) return null;

        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setLibelle(role.getLibelle());

        return dto;
    }

    public Role toRoleEntity(RoleDto dto) {
        if (dto == null) return null;

        Role role = new Role();
        role.setId(dto.getId());
        role.setLibelle(dto.getLibelle());

        return role;
    }
}

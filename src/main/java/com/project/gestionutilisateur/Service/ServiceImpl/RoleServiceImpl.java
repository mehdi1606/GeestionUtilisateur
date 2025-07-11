package com.project.gestionutilisateur.Service.ServiceImpl;

import com.project.gestionutilisateur.Dto.RoleDto;
import com.project.gestionutilisateur.Entity.Role;
import com.project.gestionutilisateur.Exception.ResourceNotFoundException;
import com.project.gestionutilisateur.Repository.RoleRepository;
import com.project.gestionutilisateur.Service.RoleService;
import com.project.gestionutilisateur.Util.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        if (roleRepository.existsByLibelle(roleDto.getLibelle())) {
            throw new IllegalArgumentException("Rôle déjà existant : " + roleDto.getLibelle());
        }

        Role role = dtoMapper.toRoleEntity(roleDto);
        Role savedRole = roleRepository.save(role);

        return dtoMapper.toRoleDto(savedRole);
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rôle non trouvé avec l'ID : " + id));

        return dtoMapper.toRoleDto(role);
    }

    @Override
    public RoleDto getRoleByLibelle(String libelle) {
        Role role = roleRepository.findByLibelle(libelle)
                .orElseThrow(() -> new ResourceNotFoundException("Rôle non trouvé : " + libelle));

        return dtoMapper.toRoleDto(role);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(dtoMapper::toRoleDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rôle non trouvé avec l'ID : " + id));

        // Vérifier si le nouveau libellé n'existe pas déjà (sauf pour ce rôle)
        if (!existingRole.getLibelle().equals(roleDto.getLibelle()) &&
                roleRepository.existsByLibelle(roleDto.getLibelle())) {
            throw new IllegalArgumentException("Rôle déjà existant : " + roleDto.getLibelle());
        }

        existingRole.setLibelle(roleDto.getLibelle());
        Role updatedRole = roleRepository.save(existingRole);

        return dtoMapper.toRoleDto(updatedRole);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rôle non trouvé avec l'ID : " + id));

        // Vérifier si le rôle n'est pas utilisé par des utilisateurs
        if (!role.getUsers().isEmpty()) {
            throw new IllegalStateException("Impossible de supprimer le rôle : il est utilisé par des utilisateurs");
        }

        roleRepository.deleteById(id);
    }

    @Override
    public boolean existsByLibelle(String libelle) {
        return roleRepository.existsByLibelle(libelle);
    }

    @Override
    public List<RoleDto> getRolesByLibelles(List<String> libelles) {
        List<Role> roles = roleRepository.findByLibelleIn(libelles);
        return roles.stream()
                .map(dtoMapper::toRoleDto)
                .collect(Collectors.toList());
    }
}

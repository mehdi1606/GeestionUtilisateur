package com.project.gestionutilisateur.Service;


import com.project.gestionutilisateur.Dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);
    RoleDto getRoleById(Long id);
    RoleDto getRoleByLibelle(String libelle);
    List<RoleDto> getAllRoles();
    RoleDto updateRole(Long id, RoleDto roleDto);
    void deleteRole(Long id);
    boolean existsByLibelle(String libelle);
    List<RoleDto> getRolesByLibelles(List<String> libelles);
}

package com.project.gestionutilisateur.Service;


import com.project.gestionutilisateur.Dto.PersonneDto;

import java.util.List;

public interface PersonneService {
    PersonneDto createPersonne(PersonneDto personneDto);
    PersonneDto getPersonneById(Long id);
    PersonneDto getPersonneByEmail(String email);
    List<PersonneDto> getAllPersonnes();
    PersonneDto updatePersonne(Long id, PersonneDto personneDto);
    void deletePersonne(Long id);
    boolean existsByEmail(String email);
    List<PersonneDto> findByNomAndPrenom(String nom, String prenom);
}

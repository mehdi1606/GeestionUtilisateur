package com.project.gestionutilisateur.Service.ServiceImpl;

import com.project.gestionutilisateur.Dto.PersonneDto;
import com.project.gestionutilisateur.Entity.Personne;
import com.project.gestionutilisateur.Exception.ResourceNotFoundException;
import com.project.gestionutilisateur.Repository.PersonneRepository;
import com.project.gestionutilisateur.Service.PersonneService;
import com.project.gestionutilisateur.Util.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonneServiceImpl implements PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public PersonneDto createPersonne(PersonneDto personneDto) {
        if (personneRepository.existsByEmail(personneDto.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé : " + personneDto.getEmail());
        }

        Personne personne = dtoMapper.toPersonneEntity(personneDto);
        Personne savedPersonne = personneRepository.save(personne);

        return dtoMapper.toPersonneDto(savedPersonne);
    }

    @Override
    public PersonneDto getPersonneById(Long id) {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personne non trouvée avec l'ID : " + id));

        return dtoMapper.toPersonneDto(personne);
    }

    @Override
    public PersonneDto getPersonneByEmail(String email) {
        Personne personne = personneRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Personne non trouvée avec l'email : " + email));

        return dtoMapper.toPersonneDto(personne);
    }

    @Override
    public List<PersonneDto> getAllPersonnes() {
        List<Personne> personnes = personneRepository.findAll();
        return personnes.stream()
                .map(dtoMapper::toPersonneDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonneDto updatePersonne(Long id, PersonneDto personneDto) {
        Personne existingPersonne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personne non trouvée avec l'ID : " + id));

        // Vérifier si le nouvel email n'existe pas déjà (sauf pour cette personne)
        if (!existingPersonne.getEmail().equals(personneDto.getEmail()) &&
                personneRepository.existsByEmail(personneDto.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé : " + personneDto.getEmail());
        }

        existingPersonne.setNom(personneDto.getNom());
        existingPersonne.setPrenom(personneDto.getPrenom());
        existingPersonne.setEmail(personneDto.getEmail());
        existingPersonne.setTelephone(personneDto.getTelephone());

        Personne updatedPersonne = personneRepository.save(existingPersonne);

        return dtoMapper.toPersonneDto(updatedPersonne);
    }

    @Override
    public void deletePersonne(Long id) {
        if (!personneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Personne non trouvée avec l'ID : " + id);
        }
        personneRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return personneRepository.existsByEmail(email);
    }

    @Override
    public List<PersonneDto> findByNomAndPrenom(String nom, String prenom) {
        List<Personne> personnes = personneRepository.findByNomAndPrenom(nom, prenom);
        return personnes.stream()
                .map(dtoMapper::toPersonneDto)
                .collect(Collectors.toList());
    }
}


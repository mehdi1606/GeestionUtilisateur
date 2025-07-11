package com.project.gestionutilisateur.Repository;


import com.project.gestionutilisateur.Entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

    Optional<Personne> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT p FROM Personne p WHERE p.nom = :nom AND p.prenom = :prenom")
    java.util.List<Personne> findByNomAndPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

    @Query("SELECT p FROM Personne p JOIN FETCH p.users WHERE p.id = :id")
    Optional<Personne> findByIdWithUsers(@Param("id") Long id);
}

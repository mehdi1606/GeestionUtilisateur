package com.project.gestionutilisateur.Repository;

import java.util.Optional;


import com.project.gestionutilisateur.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByLibelle(String libelle);

    boolean existsByLibelle(String libelle);

    @Query("SELECT r FROM Role r JOIN FETCH r.users WHERE r.id = :id")
    Optional<Role> findByIdWithUsers(@Param("id") Long id);

    @Query("SELECT r FROM Role r WHERE r.libelle IN :libelles")
    java.util.List<Role> findByLibelleIn(@Param("libelles") java.util.List<String> libelles);
}

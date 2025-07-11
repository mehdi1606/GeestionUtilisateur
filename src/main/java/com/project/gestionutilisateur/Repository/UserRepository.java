package com.project.gestionutilisateur.Repository;

import com.project.gestionutilisateur.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.login = :login")
    Optional<User> findByLoginWithRoles(@Param("login") String login);

    @Query("SELECT u FROM User u JOIN FETCH u.personne WHERE u.id = :id")
    Optional<User> findByIdWithPersonne(@Param("id") Long id);

    @Query("SELECT u FROM User u JOIN FETCH u.roles JOIN FETCH u.personne")
    java.util.List<User> findAllWithRolesAndPersonne();
}

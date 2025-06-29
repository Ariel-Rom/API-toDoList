package com.Ariel_Rom.toDoList.repositories;

import com.Ariel_Rom.toDoList.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * Repositorio JPA para manejar operaciones sobre la entidad UserEntity.
 * Permite CRUD y búsqueda personalizada por username.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /*
     * Busca un usuario por su nombre de usuario (username).
     *
     * @param username nombre de usuario a buscar
     * @return Optional con el usuario si existe, o vacío si no
     */
    Optional<UserEntity> findByUsername(String username);

}

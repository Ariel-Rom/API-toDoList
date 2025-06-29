package com.Ariel_Rom.toDoList.repositories;

import com.Ariel_Rom.toDoList.models.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * Repositorio JPA para manejar las operaciones CRUD sobre la entidad TaskEntity.
 * Extiende JpaRepository para obtener métodos listos como save, findAll, delete, etc.
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    /*
     * Busca una tarea por su ID, devolviendo un Optional para manejar si no existe.
     *
     * @param id ID de la tarea a buscar
     * @return Optional con la tarea si se encontró, o vacío si no
     */
    Optional<TaskEntity> findTaskById(Long id);

}

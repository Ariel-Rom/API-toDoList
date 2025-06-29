package com.Ariel_Rom.toDoList.controllers;

import com.Ariel_Rom.toDoList.dtos.PaginatedResponseDTO;
import com.Ariel_Rom.toDoList.dtos.TaskDTO;
import com.Ariel_Rom.toDoList.models.TaskEntity;
import com.Ariel_Rom.toDoList.services.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Controlador REST para gestionar las operaciones CRUD
 * relacionadas con las tareas (Task).
 *
 * Base path: /app/v1/task
 */
@RestController
@RequestMapping("/app/v1/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /*
     * Crea una nueva tarea.
     * Recibe un objeto TaskEntity validado en el cuerpo de la solicitud,
     * delega la creación en TaskService y devuelve la tarea creada como TaskDTO.
     */
    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskEntity newTask){
        return ResponseEntity.ok(taskService.createTask(newTask));
    }

    /*
     * Obtiene una lista paginada de tareas.
     * Parámetros opcionales:
     * - page: número de página (default 1)
     * - limit: cantidad de tareas por página (default 10)
     * Devuelve un PaginatedResponseDTO con la lista de TaskDTO y metadatos.
     */
    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<TaskDTO>> getPaginatedTasks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit){
        return ResponseEntity.ok(taskService.getPaginatedTasks(page, limit));
    }

    /*
     * Obtiene una tarea específica por su ID.
     * Devuelve un TaskDTO si la tarea existe, o un error si no.
     */
    @GetMapping("{id}")
    public ResponseEntity<TaskDTO> findTask(@PathVariable Long id){
        return ResponseEntity.ok(taskService.findTaskByID(id));
    }

    /*
     * Actualiza una tarea existente por ID.
     * Recibe un TaskEntity validado con los nuevos datos,
     * actualiza la tarea correspondiente y devuelve el TaskDTO actualizado.
     */
    @PutMapping("{id}")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody @Valid TaskEntity newTask, @PathVariable Long id){
        return ResponseEntity.ok(taskService.updateTask(newTask, id));
    }

    /*
     * Elimina una tarea por ID.
     * No devuelve contenido en el body, solo un código HTTP 204 NO_CONTENT.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

}

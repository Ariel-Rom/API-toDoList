package com.Ariel_Rom.toDoList.services;

import com.Ariel_Rom.toDoList.dtos.PaginatedResponseDTO;
import com.Ariel_Rom.toDoList.dtos.TaskDTO;
import com.Ariel_Rom.toDoList.mappers.TaskMapper;
import com.Ariel_Rom.toDoList.models.TaskEntity;
import com.Ariel_Rom.toDoList.repositories.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Indica que esta clase es un servicio de Spring
@RequiredArgsConstructor // Genera constructor con todos los atributos final (inyección de dependencias)
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    // Crea una nueva tarea y la devuelve como DTO
    public TaskDTO createTask(TaskEntity newTask) {
        TaskEntity taskEntity = taskRepository.save(newTask); // Guarda la tarea en la DB
        return taskMapper.toDTO(taskEntity); // Convierte la entidad a DTO
    }

    // Devuelve una lista paginada de tareas
    public PaginatedResponseDTO<TaskDTO> getPaginatedTasks(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit); // Se ajusta porque las páginas comienzan en 0
        Page<TaskEntity> tasksPage = taskRepository.findAll(pageable); // Consulta paginada

        // Mapea las entidades a DTOs
        List<TaskDTO> taskDTOS = tasksPage.getContent()
                .stream()
                .map(taskMapper::toDTO)
                .toList();

        // Retorna los datos paginados en un objeto DTO customizado
        return PaginatedResponseDTO.<TaskDTO>builder()
                .data(taskDTOS)
                .page(page)
                .limit(limit)
                .total(tasksPage.getTotalElements())
                .build();
    }

    // Busca una tarea por ID y la devuelve como DTO
    public TaskDTO findTaskByID(Long id) {
        TaskEntity taskEntity = taskRepository.findTaskById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found!"));

        return taskMapper.toDTO(taskEntity);
    }

    // Actualiza una tarea existente con nuevos datos
    public TaskDTO updateTask(TaskEntity newTask, Long id) {
        TaskEntity taskEntity = taskRepository.findTaskById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found!"));

        // Copia los datos del nuevo objeto a la entidad persistente (sin cambiar el ID)
        taskMapper.updateTask(taskEntity, newTask);

        return taskMapper.toDTO(taskEntity); // Devuelve el DTO actualizado
    }

    // Elimina una tarea por ID
    public void deleteTask(Long id) {
        TaskEntity taskEntity = taskRepository.findTaskById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found!"));

        taskRepository.deleteById(id); // Elimina la tarea si existe
    }
}

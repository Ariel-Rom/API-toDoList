package com.Ariel_Rom.toDoList.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * DTO para transferir datos básicos de una tarea.
 * Contiene solo los campos públicos que queremos exponer:
 * título y descripción de la tarea.
 */
@Data
public class TaskDTO {

    /*
     * Título de la tarea.
     */
    @NotBlank(message = "please, write a title")
    @Size(max = 100, message = "title must be at most 100 characters")
    private String title;

    /*
     * Descripción detallada de la tarea.
     */
    @NotBlank(message = "please, write a description")
    @Size(max = 500, message = "description must be at most 500 characters")
    private String description;

}

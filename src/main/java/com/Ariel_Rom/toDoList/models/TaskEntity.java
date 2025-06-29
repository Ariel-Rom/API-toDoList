package com.Ariel_Rom.toDoList.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * Entidad JPA que representa una tarea (task) en la base de datos.
 *
 * Cada instancia mapea a una fila en la tabla "tasks".
 */
@Entity
@Data
@Table(name = "tasks")
public class TaskEntity {

    /*
     * Identificador único de la tarea.
     * Se genera automáticamente (auto-incremental).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Título de la tarea.
     * No puede estar vacío ni ser nulo.
     */
    @Column(nullable = false)
    @NotBlank(message = "please, write a title")
    @Size(max = 100, message = "title must be at most 100 characters")
    private String title;

    /*
     * Descripción detallada de la tarea.
     * No puede estar vacío ni ser nulo.
     */
    @Column(nullable = false)
    @NotBlank(message = "please, write a description")
    @Size(max = 500, message = "description must be at most 500 characters")
    private String description;

}

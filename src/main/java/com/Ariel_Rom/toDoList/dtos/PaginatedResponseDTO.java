package com.Ariel_Rom.toDoList.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO genérico para respuestas paginadas.
 * Contiene la lista de datos de tipo T, junto con metadatos de paginación.
 *
 * @param <T> Tipo de datos que contiene la lista `data`.
 */
@Data
@AllArgsConstructor
@Builder
public class PaginatedResponseDTO<T> {

    /**
     * Lista de elementos de la página actual.
     */
    private List<T> data;

    /**
     * Número de página actual (1-based).
     */
    private int page;

    /**
     * Cantidad de elementos por página.
     */
    private int limit;

    /**
     * Cantidad total de elementos disponibles en el recurso.
     */
    private Long total;

}

package com.Ariel_Rom.toDoList.mappers;

import com.Ariel_Rom.toDoList.dtos.TaskDTO;
import com.Ariel_Rom.toDoList.models.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/*
 * Mapper para convertir entre la entidad TaskEntity y su DTO TaskDTO,
 * y para actualizar una entidad existente con datos de otra sin cambiar el ID.
 * Usa MapStruct para generar el código automáticamente.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {

    /*
     * Actualiza un objeto TaskEntity existente con los valores de otro TaskEntity,
     * ignorando el campo "id" para no modificarlo.
     *
     * @param taskEntity entidad destino a actualizar
     * @param newTaskEntity entidad fuente con los nuevos datos
     */
    @Mapping(target = "id", ignore = true)
    void updateTask(@MappingTarget TaskEntity taskEntity, TaskEntity newTaskEntity);

    /*
     * Convierte una entidad TaskEntity en un TaskDTO para exponer solo los datos necesarios.
     *
     * @param taskEntity entidad origen
     * @return DTO con los datos mapeados
     */
    TaskDTO toDTO(TaskEntity taskEntity);

}

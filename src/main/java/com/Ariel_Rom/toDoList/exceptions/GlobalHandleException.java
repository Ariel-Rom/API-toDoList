package com.Ariel_Rom.toDoList.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

/*
 * Manejador global de excepciones para controladores REST.
 * Usa @RestControllerAdvice para interceptar y manejar errores de manera centralizada.
 * Mejora la legibilidad del código y evita repetir lógica en cada controlador.
 */
@RestControllerAdvice
public class GlobalHandleException {

    /*
     * Maneja la excepción EntityNotFoundException.
     * Se lanza normalmente cuando una entidad no existe en la base de datos.
     *
     * Devuelve una respuesta con:
     * - HTTP 404 Not Found
     * - Un JSON con el mensaje de error
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> entityNotFoundHandler(EntityNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "No se encontró el recurso solicitado.");  // Mensaje claro y personalizado
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /*
     * Maneja la excepción MethodArgumentNotValidException.
     * Se lanza cuando fallan las validaciones de los DTOs anotados con @Valid.
     *
     * Devuelve una respuesta con:
     * - HTTP 400 Bad Request
     * - Un JSON con los campos inválidos y sus respectivos mensajes de error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        // Por cada error de validación, se guarda el campo y el mensaje asociado
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    /*
     * Maneja la excepción DataIntegrityViolationException.
     * Ocurre cuando se viola una restricción en la base de datos (ej. valores únicos duplicados).
     *
     * Devuelve una respuesta con:
     * - HTTP 409 Conflict
     * - Un mensaje personalizado indicando que ya existe un registro
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> duplicationHandler(DataIntegrityViolationException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Ya existe un registro con ese dato.");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}

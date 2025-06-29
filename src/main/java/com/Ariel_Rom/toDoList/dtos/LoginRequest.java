package com.Ariel_Rom.toDoList.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO que representa la petición de login.
 * Contiene los datos que el cliente debe enviar para autenticarse.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    /*
     * Nombre de usuario.
     * No puede estar vacío o nulo, validado con @NotBlank.
     */
    @NotBlank(message = "username is required")
    private String username;

    /*
     * Contraseña del usuario.
     * No puede estar vacía o nula, validado con @NotBlank.
     */
    @NotBlank(message = "password is required")
    private String password;

}

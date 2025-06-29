package com.Ariel_Rom.toDoList.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO para manejar los datos que el cliente envía
 * al registrar un nuevo usuario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    /*
     * Nombre de usuario.
     * No puede estar vacío, validado con @NotBlank.
     */
    @NotBlank(message = "username is required")
    private String username;

    /*
     * Email del usuario.
     * No puede estar vacío y debe tener formato válido, validado con @NotBlank y @Email.
     */
    @Email(message = "email must be valid")
    @NotBlank(message = "email is required")
    private String email;

    /*
     * Contraseña del usuario.
     * No puede estar vacía, validado con @NotBlank.
     */
    @NotBlank(message = "password is required")
    private String password;

}

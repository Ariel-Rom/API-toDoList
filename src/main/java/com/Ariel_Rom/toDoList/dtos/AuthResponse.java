package com.Ariel_Rom.toDoList.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO que representa la respuesta de autenticación.
 * Contiene el token JWT generado tras un login o registro exitoso.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    /*
     * Token JWT que se devuelve al cliente para usar en futuras solicitudes autenticadas.
     */
    private String token;

}

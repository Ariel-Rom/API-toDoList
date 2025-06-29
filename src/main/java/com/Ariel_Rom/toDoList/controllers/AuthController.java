package com.Ariel_Rom.toDoList.controllers;

import com.Ariel_Rom.toDoList.dtos.AuthResponse;
import com.Ariel_Rom.toDoList.dtos.LoginRequest;
import com.Ariel_Rom.toDoList.dtos.RegisterRequest;
import com.Ariel_Rom.toDoList.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controlador REST que maneja las rutas relacionadas con
 * la autenticación de usuarios: login y registro.
 *
 * Base path: /app/v1/task/auth
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/v1/task/auth")
public class AuthController {

    private final AuthService authService;

    /*
     * Endpoint para iniciar sesión.
     * Recibe un LoginRequest con credenciales (usuario y contraseña),
     * delega la lógica de autenticación al AuthService y devuelve
     * un AuthResponse con el resultado (por ejemplo, token JWT).
     */
    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    /*
     * Endpoint para registrar un nuevo usuario.
     * Recibe un RegisterRequest con los datos necesarios para el registro,
     * llama al AuthService para crear el usuario y devuelve un AuthResponse
     * con la información resultante (por ejemplo, token o datos del usuario).
     */
    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

}

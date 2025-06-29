package com.Ariel_Rom.toDoList.services;

import com.Ariel_Rom.toDoList.dtos.AuthResponse;
import com.Ariel_Rom.toDoList.dtos.LoginRequest;
import com.Ariel_Rom.toDoList.dtos.RegisterRequest;
import com.Ariel_Rom.toDoList.models.UserEntity;
import com.Ariel_Rom.toDoList.repositories.UserRepository;
import com.Ariel_Rom.toDoList.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase es un servicio de Spring
@RequiredArgsConstructor // Inyecta automáticamente las dependencias por constructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    // Metodo para iniciar sesión
    public AuthResponse login(LoginRequest request) {

        // Verifica las credenciales del usuario (lanza excepción si son inválidas)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Busca el usuario en la base de datos
        UserDetails user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        // Genera el token JWT para el usuario autenticado
        String token = jwtService.getToken(user);

        // Retorna el token dentro del DTO de respuesta
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    // Método para registrar un nuevo usuario
    public AuthResponse register(RegisterRequest request) {

        // Crea el nuevo usuario con los datos recibidos
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // Encripta la contraseña
                .email(request.getEmail())
                .build();

        // Guarda el nuevo usuario en la base de datos
        userRepository.save(user);

        // Retorna un token JWT ya generado para el nuevo usuario registrado
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}

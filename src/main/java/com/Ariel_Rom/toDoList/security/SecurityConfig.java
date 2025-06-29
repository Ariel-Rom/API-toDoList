package com.Ariel_Rom.toDoList.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // Marca esta clase como configuración de Spring
@EnableWebSecurity // Habilita seguridad web en la app
@EnableMethodSecurity // Permite usar anotaciones para seguridad en métodos (@PreAuthorize, etc)
@RequiredArgsConstructor  // Inyecta dependencies automáticamente (authProvider, jwtAuthenticationFilter)
public class SecurityConfig {

    private final AuthenticationProvider authProvider; // Proveedor para autenticación (user/password)
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Filtro para validar JWT en cada request

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                // Deshabilita CSRF porque se usa JWT (sin sesiones con cookies)
                .csrf(csrf -> csrf.disable())

                // Define reglas de acceso a endpoints
                .authorizeHttpRequests(auth -> {
                    // Permite libre acceso a rutas de autenticación (login, register)
                    auth.requestMatchers(
                            "/app/v1/task/auth/**").permitAll();
                    // El resto de rutas requieren estar autenticado
                    auth.anyRequest().authenticated();
                })

                // Configura la app para no usar sesión HTTP (stateless) porque usa JWT
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))

                // Establece el proveedor de autenticación personalizado (userDetails + password encoder)
                .authenticationProvider(authProvider)

                // Añade el filtro JWT antes del filtro de autenticación estándar para validar token en cada request
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}

package com.Ariel_Rom.toDoList.security;

import com.Ariel_Rom.toDoList.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    /*
     * Bean para el AuthenticationManager, encargado de manejar la autenticación en Spring Security.
     * Lo obtiene del AuthenticationConfiguration que ya sabe cómo armarlo.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /*
     * Bean para AuthenticationProvider, que valida usuarios y contraseñas.
     * Usa DaoAuthenticationProvider con:
     * - UserDetailsService personalizado (busca usuario en DB)
     * - PasswordEncoder (BCrypt para encriptar contraseñas)
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /*
     * Bean para codificar contraseñas con BCrypt.
     * Es el metodo recomendado para guardar passwords seguros.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Bean para UserDetailsService.
     * Define cómo cargar usuario por username desde la base de datos usando UserRepository.
     * Lanza excepción si no encuentra el usuario.
     */
    @Bean
    public UserDetailsService userDetailService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

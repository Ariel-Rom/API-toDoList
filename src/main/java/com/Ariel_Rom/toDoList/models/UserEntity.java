package com.Ariel_Rom.toDoList.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*
 * Entidad JPA que representa a un usuario en la base de datos.
 * Implementa UserDetails para integrarse con Spring Security y manejar autenticación/autorización.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity implements UserDetails {

    /*
     * ID autogenerado que identifica al usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Nombre de usuario para login.
     */
    @NotBlank(message = "username is required")
    @Column(unique = true)
    private String username;

    /*
     * Contraseña codificada (hashed).
     */
    @NotBlank(message = "password is required")
    private String password;

    /*
     * Email válido del usuario.
     */
    @Email(message = "email must be valid")
    @NotBlank(message = "email is required")
    @Column(unique = true)
    private String email;

    /*
     * Autoridades (roles) del usuario.
     * Por ahora siempre devuelve ROLE_USER fijo.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /*
     * Indica si la cuenta no está expirada.
     * Siempre true porque no se implementa expiración.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
     * Indica si la cuenta no está bloqueada.
     * Siempre true porque no se implementa bloqueo.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
     * Indica si las credenciales no están expiradas.
     * Siempre true porque no se implementa expiración de credenciales.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
     * Indica si el usuario está habilitado.
     * Siempre true porque no se implementa deshabilitación.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.Ariel_Rom.toDoList.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /*
     * Metodo que intercepta cada request HTTP una vez antes que llegue al controlador.
     * Aquí se valida el JWT (token) para autenticar al usuario en Spring Security.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtener token JWT del header Authorization (Bearer token)
        final String token = getTokenFromRequest(request);

        // Si no hay token, pasar al siguiente filtro sin autenticar
        if (token == null){
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer username del token (payload)
        final String username = jwtService.getUsernameFromToken(token);

        // Si hay username y el usuario aún no está autenticado en el contexto de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Cargar detalles del usuario desde la base (o donde sea)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validar que el token sea válido para ese usuario (firma, expiración, etc)
            if (jwtService.isTokenValid(token, userDetails)){
                // Crear token de autenticación para Spring Security con roles y detalles
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                // Agregar detalles de la request (IP, sesión, etc) al token de autenticación
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Setear el contexto de seguridad con este usuario autenticado
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Seguir con la cadena de filtros para que la request llegue a destino
        filterChain.doFilter(request, response);

    }

    /*
     * Metodo helper para extraer el token JWT del header Authorization.
     * Espera un header con el formato: "Bearer <token>"
     */
    private String getTokenFromRequest(HttpServletRequest request){

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7); // Sacar "Bearer " y devolver solo el token
        }

        return null;

    }

}

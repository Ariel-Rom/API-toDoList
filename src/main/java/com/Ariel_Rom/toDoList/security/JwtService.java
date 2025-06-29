package com.Ariel_Rom.toDoList.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // Clave secreta en base64 para firmar los tokens JWT (debería estar en config segura)
    private final String SECRET_KEY = "6A5D4E3F2C1B0A9F8G7H6I5J4K3L2M1N0P9O8N7M6L5K4J3I2";

    /*
     * Genera un token JWT para un usuario dado, sin claims extra.
     */
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    /*
     * Genera un token JWT agregando claims extra (datos adicionales) si se desean.
     * El token incluye:
     * - claims extra
     * - subject (username)
     * - fecha de emisión
     * - fecha de expiración (24 horas después)
     * - firmado con HS256 y la clave secreta
     */
    private String getToken(Map<String, Object> extraClaims, UserDetails usuario){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24)) // 24 hs
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * Decodifica la clave secreta base64 y devuelve la Key para firmar/verificar JWT.
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*
     * Extrae el username (subject) del token JWT.
     */
    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    /*
     * Valida si el token es válido para el usuario dado:
     * - username del token debe coincidir
     * - token no debe estar expirado
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /*
     * Metodo genérico para extraer cualquier claim del token.
     * Recibe una función para extraer el dato específico de Claims.
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
     * Verifica si el token ya expiró comparando la fecha de expiración con la actual.
     */
    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    /*
     * Extrae la fecha de expiración del token.
     */
    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    /*
     * Extrae todos los claims del token, verificando la firma con la clave secreta.
     */
    private Claims getAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

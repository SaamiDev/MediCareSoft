package com.microservice.auth_management.Utils;

import com.microservice.auth_management.Config.JwtConfig;
import com.microservice.auth_management.Exceptions.Tokens.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    private final JwtConfig jwtConfig;
    private Key signingKey;
    private Claims cachedClaims;


    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * Inicializa la clave de firma para la generación de tokens JWT.
     * Este método se invoca automáticamente después de que se hayan establecido las propiedades del bean.
     */
    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }

    /**
     * Genera un token JWT para un usuario específico.
     *
     * @param username el nombre de usuario para el que se genera el token.
     * @param role el rol del usuario que se incluirá en los claims del token.
     * @return un token JWT firmado como un String.
     */
    public String generateToken(String username, String role) {
        return Jwts.builder() // Comienza la construcción del token
                .setId(UUID.randomUUID().toString()) // Agrega un ID único al token
                .setSubject(username) // Asigna el identificador del token
                .claim("role", role) // Incluye el rol del usuario en los claims del token
                .setIssuedAt(new Date()) // Establece la fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationMs())) // Establece la fecha de expiración (hora actual + periodo de expiración)
                .signWith(signingKey, SignatureAlgorithm.HS256) // Firma el token
                .compact(); // Completa la construcción del token
    }

    /**
     * Extrae el nombre de usuario (subject) del token JWT proporcionado.
     *
     * @param token el token JWT del cual se extrae el nombre de usuario.
     * @return el nombre de usuario como un String.
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); // Recupera el campo 'sub'
    }

    /**
     * Extrae la fecha de expiración del token JWT proporcionado.
     *
     * @param token el token JWT del cual se extrae la fecha de expiración.
     * @return la fecha de expiración como un objeto Date.
     */
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration(); // Recupera la fecha de expiración
    }

    /**
     * Extrae el rol del usuario del token JWT proporcionado.
     *
     * @param token el token JWT del cual se extrae el rol.
     * @return el rol del usuario como un String.
     */
    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role"); // Recupera el claim 'role'
    }

    public Long getRemainingExpirationTime(String token) {
        Date expiration = extractExpiration(token);
        return expiration.getTime() - System.currentTimeMillis();
    }

    public String refreshToken(String token) {
        Claims claims = extractAllClaims(token);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() - jwtConfig.getExpirationMs()))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public void logToken(String token) {
        Claims claims = extractAllClaims(token);
        LOGGER.info("Token used by user: {}, role: {}, expiration: {}",
                claims.getSubject(), claims.get("role"), claims.getExpiration());
    }

    /**
     * Extrae todos los claims del token JWT proporcionado.
     *
     * @param token el token JWT del cual se extraen los claims.
     * @return un objeto Claims que contiene todos los claims del token.
     * @throws io.jsonwebtoken.JwtException si el token es inválido.
     */
    private Claims extractAllClaims(String token) {
        if (cachedClaims == null || !cachedClaims.getId().equals(token)) {
            cachedClaims = Jwts.parserBuilder()
                    .setSigningKey(signingKey) // Configura el parser con la clave de firma
                    .build()
                    .parseClaimsJws(token) // Analiza el token JWT
                    .getBody(); // Devuelve el cuerpo de los claims
        }
        return cachedClaims;
    }

    /**
     * Valida el token JWT proporcionado.
     *
     * @param token el token JWT a validar.
     * @return true si el token es válido, false en caso contrario.
     * @throws InvalidTokenException si el token es inválido o ha expirado.
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder() // Construye un parser que decodificará y validará el token
                    .setSigningKey(signingKey) // Se configura con la clave de firma (si la firma del token coincide con esta clave, el token es válido)
                    .build()
                    .parseClaimsJws(token); // Analiza el token proporcionado
            return true; // El token es válido
        } catch (Exception e) {
            LOGGER.error("Token validation failed: {}", e.getMessage(), e); // Agrega detalles en el log
            throw new InvalidTokenException("The token is invalid or has expired.\n", e); // Lanza una excepción personalizada para el token inválido
        }
    }
}

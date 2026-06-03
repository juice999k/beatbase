package cl.duoc.beatbase.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utilidad para generar, parsear y validar tokens JWT.
 *
 * Un JWT tiene tres partes separadas por puntos:
 *   Header.Payload.Signature
 *
 * - Header: algoritmo de firma (HS256).
 * - Payload: claims → subject (username), role, iat (emitido), exp (vence).
 * - Signature: HMAC-SHA256 firmado con la clave secreta del servidor.
 */
@Component
public class JwtUtil {

    private final SecretKey key;

    /** Duración del token: 24 horas en milisegundos. */
    private static final long EXPIRATION_MS = 86_400_000L;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        // La clave debe tener al menos 32 caracteres (256 bits) para HMAC-SHA256
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /** Genera un token JWT firmado con el username y el rol del usuario. */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    /** Extrae el username (subject) del token. */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /** Extrae el rol almacenado en el claim "role". */
    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    /**
     * Valida que el token sea auténtico y no haya expirado.
     * Si la firma no coincide o el token venció, lanza excepción interna.
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
package cl.duoc.beatbase.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro JWT que se ejecuta una vez por cada request HTTP (OncePerRequestFilter).
 *
 * Flujo:
 *  1. Lee el header "Authorization: Bearer <token>"
 *  2. Valida el token con JwtUtil
 *  3. Si es válido, extrae username y rol y los registra en el SecurityContext
 *  4. Spring Security usa ese contexto para aplicar las reglas de autorización
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                // Construye el objeto de autenticación con el rol del usuario
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );

                // Registra la autenticación en el contexto de seguridad de este hilo
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // Continúa con el siguiente filtro de la cadena
        filterChain.doFilter(request, response);
    }
}
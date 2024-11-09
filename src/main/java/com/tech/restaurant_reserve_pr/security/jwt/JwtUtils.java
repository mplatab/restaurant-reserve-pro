package com.tech.restaurant_reserve_pr.security.jwt;

import com.tech.restaurant_reserve_pr.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    private final static String ACCESS_TOKEN_SECRET="RH1IRp6ajBhkQ5j4Bo2Fw1Qmq699v3NEkspAxSnLQX1iu6VHw5";


    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS= 2_592_00L;


    public String generateJwtToken(Authentication authentication) {

        long expirationTime= ACCESS_TOKEN_VALIDITY_SECONDS*1_000;
        Date expirationDate= new Date(System.currentTimeMillis()+expirationTime);

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(ACCESS_TOKEN_SECRET));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }



    public String extractTokenFromHeaderOrWherever(HttpServletRequest request) {
        // Intenta obtener el token del encabezado "Authorization"
        String bearerToken = request.getHeader("Authorization");

        // Si no está presente en el encabezado, intenta obtenerlo de otro lugar según tu implementación
        if (StringUtils.isEmpty(bearerToken)) {
            // Aquí puedes implementar lógica para extraer el token de otro lugar si es necesario
            // Por ejemplo, de parámetros de consulta, cookies, etc.
            // Si no es aplicable, puedes devolver null o lanzar una excepción según tus necesidades.
            return null;
        }

        // Verifica si el token comienza con "Bearer "
        if (bearerToken.startsWith("Bearer ")) {
            // Retorna el token sin el prefijo "Bearer "
            return bearerToken.substring(7);
        }

        // Si el formato no es el esperado, puedes devolver null o lanzar una excepción
        return null;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }


    public String invalidateToken(String token) {
        try {
            // Obtiene la clave secreta
            Key key = key();

            // Obtiene las claims del token original
            Map<String, Object> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

            // Invalida el token estableciendo una fecha de expiración anterior a la actual
            claims.put("exp", new Date(System.currentTimeMillis() - 1000));

            // Crea un nuevo token con las mismas claims pero con fecha de expiración pasada
            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante el proceso
            throw new RuntimeException("Error al invalidar el token", e);
        }
    }



}

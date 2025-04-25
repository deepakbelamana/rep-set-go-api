package com.semicolon.RepSetGoApi.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

public class AuthorizationFilter extends OncePerRequestFilter {

    private final Environment env;
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    public AuthorizationFilter(Environment env) {
        this.env = env;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String path = request.getRequestURL().toString();
        String method = request.getMethod();
        logger.info("Processing {} request for path: {}", method, path);
        
        // Check if the request is for a public endpoint
        if (isPublicEndpoint(path, method)) {
            logger.info("Skipping authentication for public endpoint: {} {}", method, path);
            filterChain.doFilter(request, response);
            return;
        }
        
        if (header == null || !header.startsWith("Bearer ")) {
            logger.error("Missing or invalid Authorization header for path: {}", path);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = header.replace("Bearer", "").trim();
        logger.info("Token received for path {}: {}", path, token);

        if (!isJwtTokenValid(token)) {
            logger.error("Invalid JWT token for path: {}", path);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            return;
        }

        String userId = extractUserId(token);
        logger.info("Extracted userId from token: {}", userId);

        // Authenticate the request
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("Authentication set in SecurityContext for path: {}", path);
        
        try {
            filterChain.doFilter(request, response);
            logger.info("Request completed successfully for path: {}", path);
        } catch (Exception e) {
            logger.error("Error processing request for path {}: {}", path, e.getMessage());
            throw e;
        }
    }

    private boolean isPublicEndpoint(String path, String method) {
        // Check for user registration
        if (path.endsWith("/rep-set-go/users") && "POST".equals(method)) {
            return true;
        }
        // Check for login
        if (path.endsWith("/rep-set-go/users/login")) {
            return true;
        }
        //check server staus
        if (path.endsWith("/rep-set-go/status")) {
            return true;
        }
        return false;
    }

    private boolean isJwtTokenValid(String jwtToken) {
        try {
            SecretKey secretKey = getSecretKey();
            JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
            Claims claims = jwtParser.parseSignedClaims(jwtToken).getPayload();
            String subject = claims.getSubject();
            logger.info("Token validation - Subject: {}", subject);
            return subject != null && !subject.isEmpty();
        } catch (Exception e) {
            logger.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    private String extractUserId(String jwtToken) {
        try {
            SecretKey secretKey = getSecretKey();
            JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
            Claims claims = jwtParser.parseSignedClaims(jwtToken).getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            logger.error("Error extracting userId from token: {}", e.getMessage());
            throw e;
        }
    }

    private SecretKey getSecretKey() {
        String tokenKey = env.getProperty("token.secret_key");
        if (tokenKey == null) {
            logger.error("token.secret_key is not configured in environment properties");
            throw new RuntimeException("Token secret key not configured");
        }
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenKey.getBytes());
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }
}

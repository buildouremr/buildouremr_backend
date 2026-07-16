package com.ouremr.product.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    private final JWTUtil jwtUtil;

    /**
     * Exact public paths that should skip JWT validation.
     * This is intentionally restrictive to prevent unauthorized access.
     */
    private static final Set<String> PUBLIC_PATHS = Set.of(
        "/api/login/login",
        "/api/login/register",
        "/api/login/forgot-password/send-otp",
        "/api/login/forgot-password/verify-otp",
        "/api/login/forgot-password/reset",
        "/api/login/forgot-password/resend-otp",
        "/api/login/logout",
        "/api/login/me"
    );

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Allow public paths without authentication
        if (PUBLIC_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token from cookie or Authorization header
        String token = jwtUtil.extractTokenFromRequest(request);

        if (token == null || token.isBlank()) {
            sendUnauthorizedError(response, "Authentication required");
            return;
        }

        try {
            if (!jwtUtil.validateToken(token)) {
                sendUnauthorizedError(response, "Token expired or invalid");
                return;
            }

            String username = jwtUtil.extractUsername(token);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            log.warn("JWT validation failed: {}", e.getMessage());
            sendUnauthorizedError(response, "Token expired or invalid");
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Send a proper JSON 401 error response instead of a plain text error.
     */
    private void sendUnauthorizedError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(
            "{\"status\":\"FAILED\",\"data\":\"" + message + "\"}"
        );
    }
}
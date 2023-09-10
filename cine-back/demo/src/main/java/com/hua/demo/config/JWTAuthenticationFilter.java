package com.hua.demo.config;

import com.hua.demo.exceptions.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUserEmail(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException expiredJwtException){
            log.error("Expired JWT token");
            response.setStatus(401);
            response.setContentType("application/json");
            String jsonResponse = "{\"error\": \"Unauthorized: Expired JWT\"}";
            try {
                response.getWriter().write(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UserNotFoundException userNotFoundException) {
            log.error("User from JWT not found");
            response.setStatus(404);
            response.setContentType("application/json");
            String jsonResponse = "{\n\"error\": \"Not Found: User not found\"\n}";
            try {
                response.getWriter().write(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedJwtException e){
        log.error("Not authorized");
        response.setStatus(403);
        response.setContentType("application/json");
            String jsonResponse = "{\"error\": \"Unauthorized: Malformed JWT\"}";
            try {
                response.getWriter().write(jsonResponse);
            } catch (IOException ioException) {
                e.printStackTrace();
            }
    }
    }

}

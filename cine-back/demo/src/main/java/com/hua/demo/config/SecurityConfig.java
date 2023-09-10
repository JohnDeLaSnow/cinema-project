package com.hua.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint=new CustomAuthenticationEntryPoint();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .cors(c->{
                    CorsConfigurationSource cs = r -> {
                        CorsConfiguration cc = new CorsConfiguration();
                        cc.setAllowedOrigins(List.of("*"));
                        cc.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
                        cc.setAllowedHeaders(List.of("*"));
                        return cc;
                    };
                    c.configurationSource(cs);
                })
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/users/{username}","/api/users/{userEmail}/reservations","/api/movies","/api/movies/{title}","/api/movies/{title}/showings","/api/reservations/save/{username}-{id}")
                .hasAnyAuthority("USER","EMPLOYEE","EMPLOYER","ADMIN")
                .requestMatchers("/api/movies","/api/users","/api/reservations","/api/showings")
                .hasAnyAuthority("EMPLOYEE","EMPLOYER","ADMIN")
                .requestMatchers("/**")
                .hasAnyAuthority("EMPLOYER","ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);


        return httpSecurity.build();

    }

}

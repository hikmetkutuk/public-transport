package com.security.config;

import com.security.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    private static final String[] WHITE_LIST = new String[] {
        "/login", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/api-docs/**",
    };
    private static final String[] USER_ROLE_LIST = new String[] {"/vehicle/list", "/station/list", "/route/list"};

    private static final String[] ADMIN_ROLE_LIST = new String[] {
        "/vehicle/add",
        "/vehicle/update/*",
        "/vehicle/delete/*",
        "/station/add",
        "/station/update/*",
        "/station/delete/*",
        "/route/add",
        "/route/update/*",
        "/route/delete/*",
        "/report"
    };

    private static final String[] SUPER_ADMIN_ROLE_LIST = new String[] {"/vehicle/assign-route/*"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(WHITE_LIST)
                        .permitAll()
                        .requestMatchers(USER_ROLE_LIST)
                        .hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                        .requestMatchers(ADMIN_ROLE_LIST)
                        .hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .requestMatchers(SUPER_ADMIN_ROLE_LIST)
                        .hasRole("SUPER_ADMIN")
                        .anyRequest()
                        .authenticated())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

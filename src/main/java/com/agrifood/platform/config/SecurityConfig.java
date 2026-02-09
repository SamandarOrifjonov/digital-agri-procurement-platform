package com.agrifood.platform.config;

import com.agrifood.platform.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, 
                         UserDetailsService userDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                
                // Opportunities - BUYER can create, all authenticated can view
                .requestMatchers(HttpMethod.POST, "/api/opportunities").hasRole("BUYER")
                .requestMatchers(HttpMethod.POST, "/api/opportunities/*/publish").hasRole("BUYER")
                .requestMatchers(HttpMethod.DELETE, "/api/opportunities/*").hasRole("BUYER")
                .requestMatchers(HttpMethod.GET, "/api/opportunities/**").authenticated()
                
                // Bids - SUPPLIER can create, BUYER can view their opportunities' bids
                .requestMatchers(HttpMethod.POST, "/api/bids").hasRole("SUPPLIER")
                .requestMatchers(HttpMethod.GET, "/api/bids/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/bids/*").hasRole("SUPPLIER")
                
                // Suppliers - SUPPLIER can register and update
                .requestMatchers(HttpMethod.POST, "/api/suppliers").hasRole("SUPPLIER")
                .requestMatchers(HttpMethod.PUT, "/api/suppliers/*").hasRole("SUPPLIER")
                .requestMatchers(HttpMethod.GET, "/api/suppliers/**").authenticated()
                
                // Contracts - BUYER can create, both parties can view
                .requestMatchers(HttpMethod.POST, "/api/contracts").hasRole("BUYER")
                .requestMatchers(HttpMethod.GET, "/api/contracts/**").authenticated()
                
                // Admin has access to everything
                .requestMatchers("/api/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

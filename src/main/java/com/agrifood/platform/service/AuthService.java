package com.agrifood.platform.service;

import com.agrifood.platform.domain.Role;
import com.agrifood.platform.domain.User;
import com.agrifood.platform.dto.AuthRequest;
import com.agrifood.platform.dto.AuthResponse;
import com.agrifood.platform.dto.RegisterRequest;
import com.agrifood.platform.exception.BadRequestException;
import com.agrifood.platform.repository.UserRepository;
import com.agrifood.platform.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthService {
    
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                      JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                      UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }
    
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Set roles
        Set<Role> roles = new HashSet<>();
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            request.getRoles().forEach(roleName -> {
                try {
                    roles.add(Role.valueOf(roleName.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    throw new BadRequestException("Invalid role: " + roleName);
                }
            });
        } else {
            roles.add(Role.SUPPLIER); // Default role
        }
        user.setRoles(roles);
        
        userRepository.save(user);
        log.info("User registered successfully: {}", user.getUsername());
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        
        return new AuthResponse(token, user.getUsername(), 
            user.getRoles().stream().map(Role::name).collect(Collectors.toSet()));
    }
    
    public AuthResponse login(AuthRequest request) {
        log.info("User login attempt: {}", request.getUsername());
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new BadRequestException("User not found"));
        
        user.setLastLoginAt(Instant.now());
        userRepository.save(user);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        
        log.info("User logged in successfully: {}", request.getUsername());
        
        return new AuthResponse(token, user.getUsername(),
            user.getRoles().stream().map(Role::name).collect(Collectors.toSet()));
    }
}

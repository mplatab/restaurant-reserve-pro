package com.tech.restaurant_reserve_pr.controllers;

import com.tech.restaurant_reserve_pr.dto.AuthResponseDTO;
import com.tech.restaurant_reserve_pr.dto.UserLoginDTO;
import com.tech.restaurant_reserve_pr.dto.UserRegistrationDTO;
import com.tech.restaurant_reserve_pr.model.Role;
import com.tech.restaurant_reserve_pr.model.User;
import com.tech.restaurant_reserve_pr.repository.RoleRepository;
import com.tech.restaurant_reserve_pr.security.jwt.JwtUtils;
import com.tech.restaurant_reserve_pr.security.response.JwtResponse;
import com.tech.restaurant_reserve_pr.security.services.TokenBlacklist;
import com.tech.restaurant_reserve_pr.security.services.UserDetailsImpl;
import com.tech.restaurant_reserve_pr.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final TokenBlacklist tokenBlacklist;

    public AuthController(TokenBlacklist tokenBlacklist) {
        this.tokenBlacklist = tokenBlacklist;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Validated @RequestBody UserLoginDTO userLoginDTO) {
        Authentication authentication;
        String usernameOrEmail = userLoginDTO.getEmail();

        // Determine if the input is a username or email
        if (usernameOrEmail.contains("@")) {
            // Input is an email, find user by email
            User user = userService.findByEmail(userLoginDTO.getEmail())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + userLoginDTO.getEmail()));
            System.out.println(userLoginDTO.getEmail());
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
        } else {
            // Input is a username, find user by username
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usernameOrEmail, userLoginDTO.getPassword()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());

        // Asignar rol por defecto (USER)
        Role userRole = roleRepository.findByNombre("USER").orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRoles(Collections.singleton(userRole));

        return userService.saveUser(user);
    }



}

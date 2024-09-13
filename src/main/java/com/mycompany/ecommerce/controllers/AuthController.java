package com.mycompany.ecommerce.controllers;

import com.mycompany.ecommerce.config.JwtUtil;
import com.mycompany.ecommerce.dtos.JwtAuthResponseDTO;
import com.mycompany.ecommerce.dtos.LoginRequestDTO;
import com.mycompany.ecommerce.models.CustomUserDetails;
import com.mycompany.ecommerce.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${projeto.jwtExpirationMs}")
    private int jwtExpirationMs;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {

        Integer maxAge = jwtExpirationMs;

        try {
            UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(
                    loginRequest.getDoc(), loginRequest.getPassword()
            );

            Authentication authentication = authenticationManager.authenticate(
                    userAuth
            );

            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(user);
            System.out.println(user.getAuthorities());
            return ResponseEntity.ok(new JwtAuthResponseDTO(token, maxAge));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
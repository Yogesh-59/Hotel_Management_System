package com.api.gateway.controller;

import com.api.gateway.entity.User;
import com.api.gateway.security.JwtUtil;
import com.api.gateway.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        return Mono.fromCallable(() -> userService.findByUsername(username))
                .flatMap(optionalUser -> {
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        if (passwordEncoder.matches(password, user.getPassword())) {
                            String token = jwtUtil.generateToken(username, userService.getUserRoles(user));
                            return Mono.just(ResponseEntity.ok(Map.of("token", token)));
                        }
                    }
                    return Mono.just(ResponseEntity.<Map<String, String>>badRequest().build());
                });
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<User>> register(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        String roles = credentials.getOrDefault("roles", "USER");

        return Mono.fromCallable(() -> userService.findByUsername(username))
                .flatMap(optionalUser -> {
                    if (optionalUser.isPresent()) {
                        return Mono.just(ResponseEntity.status(400).<User>body(null));
                    }
                    return Mono.fromCallable(() -> userService.createUser(username, password, roles))
                            .map(createdUser -> ResponseEntity.ok(createdUser));
                });
    }
}

package com.example.bakerybackend.controller;

import com.example.bakerybackend.entity.AdminUser;
import com.example.bakerybackend.repository.AdminUserRepository;
import com.example.bakerybackend.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AdminUserRepository adminRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AdminUserRepository adminRepo,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("login");
        String password = body.get("password");

        Optional<AdminUser> userOpt = adminRepo.findByUsername(username);

        if (userOpt.isEmpty() ||
                !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Неверный логин или пароль"
            ));
        }

        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token,
                "message", "Добро пожаловать, " + username + "!"
        ));
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestHeader(value = "Authorization", required = false) String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            if (jwtUtil.isValid(token)) {
                return ResponseEntity.ok(Map.of("authenticated", true));
            }
        }
        return ResponseEntity.status(401).body(Map.of("authenticated", false));
    }
}
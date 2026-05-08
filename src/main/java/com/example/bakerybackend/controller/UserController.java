package com.example.bakerybackend.controller;

import com.example.bakerybackend.entity.Order;
import com.example.bakerybackend.entity.User;
import com.example.bakerybackend.repository.OrderRepository;
import com.example.bakerybackend.repository.UserRepository;
import com.example.bakerybackend.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepo, OrderRepository orderRepo,
                          JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // Регистрация
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String name = body.get("name");

        if (username == null || password == null || name == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false, "message", "Заполните все поля"));
        }

        if (userRepo.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false, "message", "Пользователь уже существует"));
        }

        User user = new User(username, passwordEncoder.encode(password), name);
        userRepo.save(user);

        String token = jwtUtil.generateToken("user:" + user.getId());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token,
                "userId", user.getId(),
                "name", user.getName(),
                "message", "Регистрация успешна!"
        ));
    }

    // Вход
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Optional<User> userOpt = userRepo.findByUsername(username);

        if (userOpt.isEmpty() ||
                !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false, "message", "Неверный логин или пароль"));
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken("user:" + user.getId());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token,
                "userId", user.getId(),
                "name", user.getName()
        ));
    }

    // Мои заказы
    @GetMapping("/my-orders")
    public ResponseEntity<?> myOrders(
            @RequestHeader(value = "Authorization", required = false) String auth) {
        Long userId = extractUserId(auth);
        if (userId == null) return ResponseEntity.status(401).body("Не авторизован");
        List<Order> orders = orderRepo.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // Получить профиль
    @GetMapping("/me")
    public ResponseEntity<?> me(
            @RequestHeader(value = "Authorization", required = false) String auth) {
        Long userId = extractUserId(auth);
        if (userId == null) return ResponseEntity.status(401).body("Не авторизован");
        return userRepo.findById(userId)
                .map(u -> ResponseEntity.ok(Map.of(
                        "id", u.getId(), "name", u.getName(), "username", u.getUsername())))
                .orElse(ResponseEntity.notFound().build());
    }

    private Long extractUserId(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) return null;
        String token = auth.substring(7);
        if (!jwtUtil.isValid(token)) return null;
        String subject = jwtUtil.extractUsername(token);
        if (!subject.startsWith("user:")) return null;
        try { return Long.parseLong(subject.substring(5)); }
        catch (NumberFormatException e) { return null; }
    }
}
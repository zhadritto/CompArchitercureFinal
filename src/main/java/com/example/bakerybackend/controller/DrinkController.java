package com.example.bakerybackend.controller;

import com.example.bakerybackend.entity.Drink;
import com.example.bakerybackend.service.DrinkService;
import com.example.bakerybackend.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/drinks")
@CrossOrigin("*")
public class DrinkController {

    private final DrinkService drinkService;
    private final JwtUtil jwtUtil;

    public DrinkController(DrinkService drinkService, JwtUtil jwtUtil) {
        this.drinkService = drinkService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Drink> getAll() {
        return drinkService.getAll();
    }

    @GetMapping("/{id}")
    public Drink getById(@PathVariable Long id) {
        return drinkService.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestHeader(value = "Authorization", required = false) String auth,
            @RequestBody Drink drink) {
        if (!isAdmin(auth)) return ResponseEntity.status(401).body("Нет доступа");
        return ResponseEntity.ok(drinkService.create(drink));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @RequestHeader(value = "Authorization", required = false) String auth,
            @PathVariable Long id, @RequestBody Drink drink) {
        if (!isAdmin(auth)) return ResponseEntity.status(401).body("Нет доступа");
        return ResponseEntity.ok(drinkService.update(id, drink));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @RequestHeader(value = "Authorization", required = false) String auth,
            @PathVariable Long id) {
        if (!isAdmin(auth)) return ResponseEntity.status(401).body("Нет доступа");
        drinkService.delete(id);
        return ResponseEntity.ok().build();
    }

    // ✅ Исправлено — проверяет настоящий JWT токен
    private boolean isAdmin(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) return false;
        String token = auth.substring(7);
        return jwtUtil.isValid(token);
    }
}
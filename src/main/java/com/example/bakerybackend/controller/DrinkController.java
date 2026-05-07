package com.example.bakerybackend.controller;

import com.example.bakerybackend.entity.Drink;
import com.example.bakerybackend.service.DrinkService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/drinks")
@CrossOrigin("*")
public class DrinkController {

    private final DrinkService drinkService;

    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
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
    public Drink create(@RequestBody Drink drink) {
        return drinkService.create(drink);
    }

    @PutMapping("/{id}")
    public Drink update(@PathVariable Long id, @RequestBody Drink drink) {
        return drinkService.update(id, drink);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        drinkService.delete(id);
    }
}
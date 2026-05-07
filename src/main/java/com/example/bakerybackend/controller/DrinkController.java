package com.example.bakerybackend.controller;

import com.example.bakerybackend.entity.Drink;
import com.example.bakerybackend.repository.DrinkRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/drinks")
@CrossOrigin("*")
public class DrinkController {
    private final DrinkRepository drinkRepository;
    public DrinkController(DrinkRepository drinkRepository) { this.drinkRepository = drinkRepository; }

    @GetMapping
    public List<Drink> getAllDrinks() { return drinkRepository.findAll(); }
}
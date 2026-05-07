package com.example.bakerybackend.service;

import com.example.bakerybackend.entity.Drink;
import com.example.bakerybackend.repository.DrinkRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public List<Drink> getAll() {
        return drinkRepository.findAll();
    }

    public Drink getById(Long id) {
        return drinkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Напиток не найден: " + id));
    }

    public Drink create(Drink drink) {
        return drinkRepository.save(drink);
    }

    public Drink update(Long id, Drink updated) {
        Drink drink = getById(id);
        drink.setName(updated.getName());
        drink.setPrice(updated.getPrice());
        drink.setVolume(updated.getVolume());
        return drinkRepository.save(drink);
    }

    public void delete(Long id) {
        drinkRepository.deleteById(id);
    }
}
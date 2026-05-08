package com.example.bakerybackend;

import com.example.bakerybackend.entity.*;
import com.example.bakerybackend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final DrinkRepository drinkRepo;
    private final CategoryRepository catRepo;
    private final AdminUserRepository adminRepo;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(DrinkRepository drinkRepo,
                      CategoryRepository catRepo,
                      AdminUserRepository adminRepo,
                      PasswordEncoder passwordEncoder) {
        this.drinkRepo = drinkRepo;
        this.catRepo = catRepo;
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (adminRepo.count() == 0) {
            adminRepo.save(new AdminUser(
                    "admin",
                    passwordEncoder.encode("jadore2026"),
                    "ADMIN"
            ));
            System.out.println("✅ Администратор создан: admin / jadore2024");
        }


        if (catRepo.count() == 0) {
            Category lim = new Category(); lim.setName("Лимонады");
            Category cof = new Category(); cof.setName("Холодный кофе");
            Category smt = new Category(); smt.setName("Смузи");
            Category tea = new Category(); tea.setName("Холодный чай");
            Category frsh = new Category(); frsh.setName("Фреши");
            catRepo.saveAll(List.of(lim, cof, smt, tea, frsh));

            drinkRepo.saveAll(List.of(
                    new Drink("Классический Мохито", 1200.0, "500мл", lim),
                    new Drink("Клубника-Базилик", 1400.0, "500мл", lim),
                    new Drink("Манго-Маракуйя", 1600.0, "500мл", lim),
                    new Drink("Огуречный с мятой", 1300.0, "500мл", lim),
                    new Drink("Лавандовый лимонад", 1500.0, "500мл", lim),
                    new Drink("Айс Латте", 1500.0, "400мл", cof),
                    new Drink("Бамбл-кофе", 1800.0, "400мл", cof),
                    new Drink("Эспрессо-Тоник", 1600.0, "300мл", cof),
                    new Drink("Фраппучино Карамель", 1900.0, "450мл", cof),
                    new Drink("Колд Брю", 1700.0, "300мл", cof),
                    new Drink("Зеленый Детокс", 2000.0, "400мл", smt),
                    new Drink("Ягодный Микс", 2100.0, "400мл", smt),
                    new Drink("Тропический рай", 2200.0, "400мл", smt),
                    new Drink("Банан-Клубника", 1900.0, "400мл", smt),
                    new Drink("Протеиновый Черничный", 2500.0, "400мл", smt),
                    new Drink("Классический Персик", 1100.0, "500мл", tea),
                    new Drink("Зеленый с жасмином", 1100.0, "500мл", tea),
                    new Drink("Каркаде с малиной", 1200.0, "500мл", tea),
                    new Drink("Матча-Тоник", 1800.0, "400мл", tea),
                    new Drink("Тайский синий чай", 1500.0, "500мл", tea),
                    new Drink("Апельсиновый фреш", 1500.0, "300мл", frsh),
                    new Drink("Яблочный фреш", 1300.0, "300мл", frsh),
                    new Drink("Морковно-яблочный", 1400.0, "300мл", frsh),
                    new Drink("Грейпфрутовый", 1600.0, "300мл", frsh),
                    new Drink("Ананасовый", 2000.0, "300мл", frsh)
            ));
            System.out.println("✨ Меню из 25 напитков загружено!");
        }
    }
}
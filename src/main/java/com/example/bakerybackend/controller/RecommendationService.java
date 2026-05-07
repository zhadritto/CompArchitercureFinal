package com.example.bakerybackend.controller;

import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    public String getRecommendation(String mood, String taste) {
        String moodLower = mood != null ? mood.toLowerCase() : "";
        String tasteLower = taste != null ? taste.toLowerCase() : "";

        if (moodLower.contains("устал") || moodLower.contains("сон")) {
            return "Вам нужно взбодриться! Попробуйте наш 'Бамбл-кофе' — слой карамели, апельсинового сока и эспрессо! 🍊☕";
        }

        if (tasteLower.contains("сладк")) {
            return "Для сладкоежек у нас есть легендарный 'Фраппучино Карамель' или 'Ягодный смузи'! 🥤🍓";
        }

        if (tasteLower.contains("свеж") || tasteLower.contains("кисл")) {
            return "Самое освежающее в нашем меню — 'Огуречный лимонад с мятой' или 'Классический Мохито'! 🧊🌿";
        }

        return "Попробуйте наш хит этого лета — 'Манго-Маракуйя лимонад'! Экзотика в каждом глотке. 🥭☀️";
    }
}
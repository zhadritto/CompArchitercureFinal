package com.example.bakerybackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "drinks")
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String volume; // напр. "500 мл"
    public Drink() {} // Пустой конструктор для Hibernate

    public Drink(String name, Double price, String volume, Category category) {
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.category = category;
    }
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getVolume() { return volume; }
    public void setVolume(String volume) { this.volume = volume; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
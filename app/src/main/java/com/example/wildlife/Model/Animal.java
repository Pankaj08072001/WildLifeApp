package com.example.wildlife.Model;

public class Animal {
    private String name;
    private int image;

    public Animal(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}

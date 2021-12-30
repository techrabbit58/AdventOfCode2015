package com.example.Day22;

public interface Player {

    String getName();
    int get(String property);
    void set(String property, int value);
    void attack(Player opponent);
}

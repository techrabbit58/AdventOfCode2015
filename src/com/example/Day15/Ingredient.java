package com.example.Day15;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * E X A M P L E
 * Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
 */
@Data
@Builder
public class Ingredient {

    private String name;
    private int capacity;
    private int durability;
    private int flavor;
    private int texture;
    private int calories;
}

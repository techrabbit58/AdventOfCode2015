package com.example.Day15;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

@Data
@Builder
public class Recipe {

    @Singular Map<Ingredient, Integer> ingredients;

    int score() {
        return 0;
    }
}

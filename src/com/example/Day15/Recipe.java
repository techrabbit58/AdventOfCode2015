package com.example.Day15;

import java.util.Map;

public class Recipe {

    Map<Ingredient, Integer> ingredients;

    Recipe(Map<Ingredient, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    int score() {

        var tsSum = ingredients.values().stream().reduce(Integer::sum).orElse(0);
        if (tsSum != 100) throw new RuntimeException("mass of ingredients must be 100 teaspoons, was '" + tsSum + "'");

        var capacity = ingredients.entrySet().stream()
                .map(e -> e.getValue() * e.getKey().getCapacity())
                .reduce(Integer::sum).filter(i -> (i >= 0)).orElse(0);
        var durability = ingredients.entrySet().stream()
                .map(e -> e.getValue() * e.getKey().getDurability())
                .reduce(Integer::sum).filter(i -> (i >= 0)).orElse(0);
        var flavor = ingredients.entrySet().stream()
                .map(e -> e.getValue() * e.getKey().getFlavor())
                .reduce(Integer::sum).filter(i -> (i >= 0)).orElse(0);
        var texture = ingredients.entrySet().stream()
                .map(e -> e.getValue() * e.getKey().getTexture())
                .reduce(Integer::sum).filter(i -> (i >= 0)).orElse(0);

        return capacity * durability * flavor * texture;
    }

    public int scoreIf500Calories() {

        var calories = ingredients.entrySet().stream()
                .map(e -> e.getValue() * e.getKey().getCalories())
                .reduce(Integer::sum).filter(i -> (i == 500)).orElse(0);
        return calories > 0 ? score() : 0;
    }
}

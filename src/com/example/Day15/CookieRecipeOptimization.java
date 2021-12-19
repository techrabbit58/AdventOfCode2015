package com.example.Day15;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CookieRecipeOptimization {

    @SneakyThrows
    public static void main(String[] args) {

        var url = CookieRecipeOptimization.class.getResource("/ingredients.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        Map<String, Ingredient> ingredients = new TreeMap<>();
        Files.lines(path).forEach(line -> parse(line, ingredients));

        ingredients.entrySet().forEach(System.out::println);

        var recipe = new Recipe(Map.of(
                ingredients.get("Sprinkles"), 25,
                ingredients.get("Sugar"), 30,
                ingredients.get("Frosting"), 20,
                ingredients.get("PeanutButter"), 25
        ));

        System.out.println("score: " + recipe.score());
    }

    private static void parse(String line, Map<String, Ingredient> ingredients) {

        var parts = line.split("[:,]*[\\s]+");

        var ingredient = Ingredient.builder()
                .name(parts[0])
                .capacity(Integer.parseInt(parts[2]))
                .durability(Integer.parseInt(parts[4]))
                .flavor(Integer.parseInt(parts[6]))
                .texture(Integer.parseInt(parts[8]))
                .calories(Integer.parseInt(parts[10]))
                .build();

        ingredients.put(parts[0], ingredient);
    }
}

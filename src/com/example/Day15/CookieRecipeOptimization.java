package com.example.Day15;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CookieRecipeOptimization {

    @SneakyThrows
    public static void main(String[] args) {

        var url = CookieRecipeOptimization.class.getResource("/ingredients_for_test.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        List<Ingredient> ingredients = new ArrayList<>();
        Files.lines(path).forEach(line -> parse(line, ingredients));

        ingredients.forEach(System.out::println);
    }

    private static void parse(String line, List<Ingredient> ingredients) {

        var parts = line.split("[:,]*[\\s]+");

        var ingredient = Ingredient.builder()
                .name(parts[0].replace(":", ""))
                .capacity(Integer.parseInt(parts[2]))
                .durability(Integer.parseInt(parts[4]))
                .flavor(Integer.parseInt(parts[6]))
                .texture(Integer.parseInt(parts[8]))
                .calories(Integer.parseInt(parts[10]))
                .build();

        ingredients.add(ingredient);
    }
}

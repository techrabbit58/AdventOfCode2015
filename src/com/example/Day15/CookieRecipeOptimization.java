package com.example.Day15;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CookieRecipeOptimization {

    @SneakyThrows
    public static void main(String[] args) {

        var url = CookieRecipeOptimization.class.getResource("/ingredients.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        var ingredients = new ArrayList<Ingredient>();
        Files.lines(path).forEach(line -> parse(line, ingredients));

        var partitioner = new IntPartitioner(100, ingredients.size());

        var recipes = new ArrayList<Recipe>();
        while (partitioner.hasNext()) {

            var quantities = partitioner.next();
            var recipe = new HashMap<Ingredient, Integer>();

            for (var i = 0; i < quantities.size(); i++) recipe.put(ingredients.get(i), quantities.get(i));

            var candidate = new Recipe(recipe);
            if (candidate.score() > 0) recipes.add(candidate);
        }

        var maxScore = recipes.stream().map(Recipe::score).reduce(Integer::max).orElse(0);
        System.out.println("part 1 solution: " + maxScore);

        maxScore = recipes.stream().map(Recipe::scoreIf500Calories).reduce(Integer::max).orElse(0);
        System.out.println("part 2 solution: " + maxScore);
    }

    private static void parse(String line, List<Ingredient> ingredients) {

        var parts = line.split("[:,]*[\\s]+");

        var ingredient = Ingredient.builder()
                .name(parts[0])
                .capacity(Integer.parseInt(parts[2]))
                .durability(Integer.parseInt(parts[4]))
                .flavor(Integer.parseInt(parts[6]))
                .texture(Integer.parseInt(parts[8]))
                .calories(Integer.parseInt(parts[10]))
                .build();

        ingredients.add(ingredient);
    }
}

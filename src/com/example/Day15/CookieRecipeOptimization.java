package com.example.Day15;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class CookieRecipeOptimization {

    @SneakyThrows
    public static void main(String[] args) {

        var url = CookieRecipeOptimization.class.getResource("/ingredients.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        Files.lines(path).forEach(System.out::println);
    }
}

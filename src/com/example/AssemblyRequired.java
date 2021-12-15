package com.example;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class AssemblyRequired {

    @SneakyThrows
    public static void main(String[] args) {

        var url = AssemblyRequired.class.getResource("/simplecircuit.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        Files.lines(path).forEach(line -> {

            System.out.println(line);
        });
    }
}

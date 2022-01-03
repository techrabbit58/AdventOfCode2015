package com.example.Helpers;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Parsers {

    @SneakyThrows
    public static List<Integer> resourceToIntegerList(String fileName) {

        var url = Parsers.class.getResource(fileName);
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        return stringToIntegerList(Files.readString(path));
    }

    public static List<Integer> stringToIntegerList(String text) {
        return Arrays.stream(text.split("\\s+")).map(Integer::parseInt).toList();
    }
}

package com.example.Helpers;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class LineByLineInput {

    private final Path _path;

    @SneakyThrows
    public LineByLineInput(String fileName) {

        var url = getClass().getResource(fileName);
        _path = Paths.get(Objects.requireNonNull(url).toURI());
    }

    @SneakyThrows
    public void forEach(Consumer<? super String> action) {
        Files.lines(_path).forEach(action);
    }

    @SneakyThrows
    public Spliterator<String> spliterator() {
        return Files.lines(_path).spliterator();
    }
}

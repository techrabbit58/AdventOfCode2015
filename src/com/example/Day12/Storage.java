package com.example.Day12;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Storage implements Iterable<String> {

    private final String peculiarStorage;

    @SneakyThrows
    public Storage(@NonNull final String fileName) {

        var uri = Objects.requireNonNull(getClass().getResource(fileName)).toURI();
        var path = Paths.get(uri);
        peculiarStorage = Files.readString(path);
    }

    @Override
    public Iterator<String> iterator() {

        return new Iterator<>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < peculiarStorage.length();
            }

            @Override
            public String next() {
                return String.valueOf(peculiarStorage.charAt(index++));
            }
        };
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return Iterable.super.spliterator();
    }

    public String getPeculiarStorage() {
        return peculiarStorage;
    }
}

package com.example.Day17;

import lombok.SneakyThrows;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class TooMuchEggnog {

    @SneakyThrows
    public static void main(String[] args) {

        var url = TooMuchEggnog.class.getResource("/container_sizes_for_test.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());
        var scanner = new Scanner(path);

        var containers = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            containers.add(scanner.nextInt());
        }

        System.out.println(containers);
    }
}

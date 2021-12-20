package com.example.Day06;

import lombok.Builder;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class LightingConfiguration {

    @SneakyThrows
    public static void main(String[] args) {

        var part1Lights = new boolean[1000][1000];
        var part2Lights = new long[1000][1000];

        var url = LightingConfiguration.class.getResource("/lighting_instructions.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        Files.lines(path).forEach(line -> {

            var instruction = parse(line);

            for (var x = instruction.left; x <= instruction.right; x++) {
                for (var y = instruction.upper; y <= instruction.lower; y++) {

                    part1(part1Lights, instruction.op, x, y);
                    part2(part2Lights, instruction.op, x, y);
                }
            }
        });

        var counter = 0;
        var brightness = 0L;

        for (var x = 0; x < 1000; x++) {
            for (var y = 0; y < 1000; y++) {
                if (part1Lights[y][x]) counter++;
                brightness += part2Lights[y][x];
            }
        }

        System.out.println("part 1 solution = " + counter);
        System.out.println("part 2 solution = " + brightness);
    }

    private static void part2(long[][] lights, String op, Integer x, Integer y) {
        lights[y][x] = switch(op) {
            case "on" -> lights[y][x] + 1;
            case "off" -> lights[y][x] > 0 ? lights[y][x] - 1 : 0;
            case "toggle" -> lights[y][x] + 2;
            default -> lights[y][x];
        };
    }

    private static void part1(boolean[][] lights, String op, Integer x, Integer y) {
        lights[y][x] = switch (op) {
            case "on" -> true;
            case "off" -> false;
            case "toggle" -> !lights[y][x];
            default -> lights[y][x];
        };
    }

    private static Instruction parse(String line) {

        var instruction = Instruction.builder();

        var parts = line.split(" ");
        var bias = 0;

        if (parts.length == 4) {
            instruction.op(parts[0]);
        } else {
            instruction.op(parts[1]);
            bias = 1;
        }

        var pair = parts[bias + 1].split(",");
        instruction.left(Integer.valueOf(pair[0])).upper(Integer.valueOf(pair[1]));

        pair = parts[bias + 3].split(",");
        instruction.right(Integer.valueOf(pair[0])).lower(Integer.valueOf(pair[1]));

        return instruction.build();
    }

    @Builder
    private static class Instruction {

        String op;
        Integer left;
        Integer upper;
        Integer right;
        Integer lower;
    }
}

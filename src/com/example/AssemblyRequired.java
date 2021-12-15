package com.example;

import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.ToString;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AssemblyRequired {

    @SneakyThrows
    public static void main(String[] args) {

        var url = AssemblyRequired.class.getResource("/simplecircuit.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        Map<String, Integer> wires = new HashMap<>();

        Files.lines(path).forEach(line -> {

            var instruction = parse(line);
            System.out.println(instruction);

            switch (instruction.op) {
                case "CONST" -> {
                    var arg = instruction.args.get(0);
                    wires.put(instruction.dest, constant(wires, arg));
                }
                case "NOT" -> {
                    var arg = instruction.args.get(0);
                    wires.put(instruction.dest, not(wires, arg));
                }
                case "AND" -> {
                    var argA = instruction.args.get(0);
                    var argB = instruction.args.get(1);
                    wires.put(instruction.dest, and(wires, argA, argB));
                }
                case "OR" -> {
                    var argA = instruction.args.get(0);
                    var argB = instruction.args.get(1);
                    wires.put(instruction.dest, or(wires, argA, argB));
                }
                case "RSHIFT" -> {
                    var argA = instruction.args.get(0);
                    var argB = instruction.args.get(1);
                    wires.put(instruction.dest, rshift(wires, argA, argB));
                }
                case "LSHIFT" -> {
                    var argA = instruction.args.get(0);
                    var argB = instruction.args.get(1);
                    wires.put(instruction.dest, lshift(wires, argA, argB));
                }
                default -> throw new RuntimeException("Unknown operation: '" + instruction.op + "'");
            }
        });

        System.out.println(wires);
    }

    private static Integer not(Map<String, Integer> wires, String arg) {
        return ~(arg.matches("[0-9]+") ? Integer.valueOf(arg) : wires.get(arg)) & 0xffff;
    }

    private static Integer constant(Map<String, Integer> wires, String arg) {
        return arg.matches("[0-9]+") ? Integer.valueOf(arg) : wires.get(arg);
    }

    private static Integer and(Map<String, Integer> wires, @NonNull String argA, @NonNull String argB) {

        var a = (argA.matches("[0-9]+")) ? Integer.valueOf(argA) : wires.get(argA);
        var b = (argB.matches("[0-9]+")) ? Integer.valueOf(argB) : wires.get(argB);

        return a & b;
    }

    private static Integer or(Map<String, Integer> wires, @NonNull String argA, @NonNull String argB) {

        var a = (argA.matches("[0-9]+")) ? Integer.valueOf(argA) : wires.get(argA);
        var b = (argB.matches("[0-9]+")) ? Integer.valueOf(argB) : wires.get(argB);

        return a | b;
    }

    private static Integer rshift(Map<String, Integer> wires, @NonNull String argA, @NonNull String argB) {

        var a = (argA.matches("[0-9]+")) ? Integer.valueOf(argA) : wires.get(argA);
        var b = (argB.matches("[0-9]+")) ? Integer.valueOf(argB) : wires.get(argB);

        return a >> b;
    }

    private static Integer lshift(Map<String, Integer> wires, @NonNull String argA, @NonNull String argB) {

        var a = (argA.matches("[0-9]+")) ? Integer.valueOf(argA) : wires.get(argA);
        var b = (argB.matches("[0-9]+")) ? Integer.valueOf(argB) : wires.get(argB);

        return a << b;
    }

    private static Instruction parse(String line) {

        var parts = line.split(" ");
        var instruction = Instruction.builder();

        instruction.dest(parts[parts.length - 1].toLowerCase());

        switch (parts.length) {
            case 3 -> {
                instruction.op("CONST");
                instruction.args(List.of(parts[0].toLowerCase()));
            }
            case 4 -> {
                instruction.op(parts[0].toUpperCase());
                instruction.args(List.of(parts[1].toLowerCase()));
            }
            case 5 -> {
                instruction.args(List.of(parts[0].toLowerCase(), parts[2].toLowerCase()));
                instruction.op(parts[1].toUpperCase());
            }
            default -> throw new RuntimeException("Unknown instruction: '" + line + "'");
        }

        return instruction.build();
    }

    @Builder @ToString
    private static class Instruction {

        String op;          // Instruction type: CONST|NOT|AND|OR|LSHIFT|RSHIFT
        List<String> args;  // one or two arguments for the op, may be a constant or a wire label
        String dest;        // the destination wire of the instruction, always a wire label
    }
}

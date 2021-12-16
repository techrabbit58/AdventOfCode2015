package com.example;

import lombok.Builder;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day07AssemblyRequired {

    @SneakyThrows
    public static void main(String[] args) {

        var url = Day07AssemblyRequired.class.getResource("/assembly_instructions.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        Map<String, Instruction> wires = new HashMap<>();

        Files.lines(path).forEach(line -> parse(wires, line));

        int result = evaluate(wires, "a");

        System.out.println("part 1 solution: " + result);

        wires.values().forEach(v -> v.value = RESET);
        wires.get("b").value = result;

        result = evaluate(wires, "a");

        System.out.println("part 2 solution: " + result);
    }

    private static int evaluate(Map<String, Instruction> wires, String thisWire) {

        var ins = wires.get(thisWire);

        if (ins.value == RESET) {

            var op = ins.op;

            var arg0 = 0;
            if (ins.args.size() > 0) {
                arg0 = isValue(ins.args.get(0)) ? Integer.parseInt(ins.args.get(0)) : evaluate(wires, ins.args.get(0));
            }

            var arg1 = 0;
            if (ins.args.size() > 1) {
                arg1 = isValue(ins.args.get(1)) ? Integer.parseInt(ins.args.get(1)) : evaluate(wires, ins.args.get(1));
            }

            ins.value = switch (op) {
                case "ASSIGN" -> arg0;
                case "NOT" -> ~arg0 & 0xffff;
                case "OR" -> (arg0 | arg1) & 0xffff;
                case "AND" -> (arg0 & arg1) & 0xffff;
                case "RSHIFT" -> (arg0 >> arg1) & 0xffff;
                case "LSHIFT" -> (arg0 << arg1) & 0xffff;
                default -> throw new RuntimeException("Unknown operation: '" + op + "'");
            };
        }

        return ins.value;
    }

    private static boolean isValue(String a) {
        return a.matches("[0-9]+");
    }

    private static void parse(Map<String, Instruction> wires, String line) {

        var parts = line.split(" ");
        var instruction = Instruction.builder();

        var destination = (parts[parts.length - 1].toLowerCase());

        switch (parts.length) {
            case 3 -> {
                instruction.op("ASSIGN");
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

        wires.put(destination, instruction.build());
    }

    private static final Integer RESET = null;

    @Builder
    private static class Instruction {

        @Builder.Default
        String op = "";        // Instruction type: ASSIGN|NOT|AND|OR|LSHIFT|RSHIFT

        @Builder.Default
        List<String> args = List.of();  // one or two arguments for the op

        @Builder.Default
        Integer value = RESET; // RESET, if not evaluated; otherwise a positive 32-bit integer, or 0
    }
}

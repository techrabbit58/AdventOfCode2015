package com.example.Day23;

record Instruction(String operation, String register, int offset) {

    public static Instruction fromCode(String line) {

        var parts = line.replace(",", " ").split("\\s+");

        return switch (parts[0]) {
            case "hlf", "tpl", "inc" -> new Instruction(parts[0], parts[1], 0);
            case "jmp" -> new Instruction(parts[0], "none", Integer.parseInt(parts[1]));
            case "jio", "jie" -> new Instruction(parts[0], parts[1], Integer.parseInt(parts[2]));
            default -> throw new RuntimeException("unknown operation '" + parts[0] + "'");
        };
    }
}

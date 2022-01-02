package com.example.Day23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Computer {

    private int pc = 0;
    private final Map<String, Long> registers = new HashMap<>();
    private final List<Instruction> program = new ArrayList<>();

    {
        registers.put("a", 0L);
        registers.put("b", 0L);
    }

    Computer(String program) {
        program.lines().forEach(line -> this.program.add(Instruction.fromCode(line)));
    }

    long getRegister(String reg) {
        return registers.get(reg);
    }

    void run() {

        while (pc < program.size()) {

            var instruction = program.get(pc);

            System.out.print(pc + ": " + registers + "\r");

            var op = instruction.operation();
            var r = instruction.register();
            var offset = instruction.offset();

            switch (op) {
                case "hlf" -> {
                    registers.put(r, registers.get(r) / 2);
                    pc++;
                }
                case "tpl" -> {
                    registers.put(r, registers.get(r) * 3);
                    pc++;
                }
                case "inc" -> {
                    registers.put(r, registers.get(r) + 1);
                    pc++;
                }
                case "jmp" -> pc += offset;
                case "jie" -> pc += (((registers.get(r) % 2) == 0) ? offset : 1);
                case "jio" -> pc += ((registers.get(r) == 1) ? offset : 1);
            }
        }
    }

    void resetForPart2() {

        pc = 0;
        registers.put("a", 1L);
        registers.put("b", 0L);
    }
}

package com.example.Day23;

import com.example.Helpers.Assertions;
import com.example.Helpers.Miscellaneous;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class TuringLock {

    @SneakyThrows
    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 23: Opening the Turing Lock");

        // TEST PART 1

        var computer = new Computer("inc a\njio a, +2\ntpl a\ninc a");
        computer.run();
        Assertions.assertEquals(2L, computer.getRegister("a"));

        // PUZZLE PART 1

        var url = TuringLock.class.getResource("/register_machine_program.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        computer = new Computer(Files.readString(path));

        computer.run();
        System.out.println("part 1 solution: " + computer.getRegister("b"));

        // PUZZLE PART 2

        computer.resetForPart2();
        computer.run();
        System.out.println("part 2 solution: " + computer.getRegister("b"));
    }
}

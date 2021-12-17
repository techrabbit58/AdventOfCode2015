package com.example.Day12;

public class AbacusFramework {

    public static void main(String[] args) {

        var storage = new Storage("/peculiar_storage.json");
        var summingUnit = new SummingUnit();

        for (var ch : storage) summingUnit.accept(ch);

        var result = summingUnit.getAllNumbers().stream().reduce(Integer::sum).orElse(0);

        System.out.println("part 1 solution = " + result);

        result = summingUnit.getAllNonRedNumbers().stream().reduce(Integer::sum).orElse(0);

        System.out.println("part 2 solution = " + result);
    }
}

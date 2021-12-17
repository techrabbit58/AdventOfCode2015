package com.example.Day12;

public class AbacusFramework {

    public static void main(String[] args) {

        var storage = new Storage("/peculiar_storage.json");
        var part1SummingUnit = new PushDownSummingUnit();
        var part2SummingUnit = new RecursiveSummingUnit();
        int result;

        for (var ch : storage) {
            part1SummingUnit.accept(ch);
            part2SummingUnit.accept(ch);
        }

        result = part1SummingUnit.getAllNumbers().stream().reduce(Integer::sum).orElse(0);
        System.out.println("part 1 solution = " + result);

        result = part2SummingUnit.getAllNumbers().stream().reduce(Integer::sum).orElse(0);
        System.out.println("part 2 solution = " + result);
    }
}

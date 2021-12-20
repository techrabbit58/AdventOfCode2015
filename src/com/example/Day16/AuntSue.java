package com.example.Day16;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class AuntSue {

    public static void main(String[] args) {

        var input = new LineByLineInput("/aunt_sue_inventories.txt");

        var sueRecords = new ArrayList<AuntSueRecord>();
        input.forEach(line -> parse(line, sueRecords));

        var sue = sueRecords.stream()
                .filter(rec -> rec.propertyMatches("children", 3))
                .filter(rec -> rec.propertyMatches("cats", 7))
                .filter(rec -> rec.propertyMatches("samoyeds", 2))
                .filter(rec -> rec.propertyMatches("pomeranians", 3))
                .filter(rec -> rec.propertyMatches("akitas", 0))
                .filter(rec -> rec.propertyMatches("vizslas", 0))
                .filter(rec -> rec.propertyMatches("goldfish", 5))
                .filter(rec -> rec.propertyMatches("trees", 3))
                .filter(rec -> rec.propertyMatches("cars", 2))
                .filter(rec -> rec.propertyMatches("perfumes", 1))
                .toList();

        sue.forEach(System.out::println);
        System.out.println();

        System.out.println("part 1 solution: " + sue.get(0).sueId());
    }

    private static void parse(String line, List<AuntSueRecord> sueRecords) {

        var parts = line.split("[:,]*[\\s]+");

        if (!"Sue".equals(parts[0])) throw new InputMismatchException("unexpected name: '" + parts[0] + "'");

        var sue = new AuntSueRecord(Integer.parseInt(parts[1]));

        for (var i = 2; i < parts.length - 1; i += 2) {
            sue.setProperty(parts[i], Integer.parseInt(parts[i + 1]));
        }

        sueRecords.add(sue);
    }
}

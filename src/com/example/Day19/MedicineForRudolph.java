package com.example.Day19;

import com.example.Helpers.Assertions;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MedicineForRudolph {

    @SneakyThrows
    public static void main(String[] args) {

        // T E S T   P A R T 1
        Assertions.assertEquals(4, calibrate("H => HO\nH => OH\nO => HH\n\nHOH"));
        Assertions.assertEquals(7, calibrate("H => HO\nH => OH\nO => HH\n\nHOHOHO"));

        // T E S T   P A R T 2
        Assertions.assertEquals(3,
                generateMedicineMolecule("e => H\ne => O\nH => HO\nH => OH\nO => HH\n\nHOH"));
        Assertions.assertEquals(6,
                generateMedicineMolecule("e => H\ne => O\nH => HO\nH => OH\nO => HH\n\nHOHOHO"));

        // S O L U T I O N   P A R T 1

        var url = MedicineForRudolph.class.getResource("/molecule_grammar.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        var part1Solution = calibrate(Files.readString(path));
        System.out.println("part 1 solution: " + part1Solution);

        var part2Solution = generateMedicineMolecule(Files.readString(path));
        System.out.println("part 2 solution: " + part2Solution);
    }

    private static int calibrate(String input) {

        List<Pair> grammar = new ArrayList<>();
        var word = parse(input, grammar);

        Set<String> molecules = new HashSet<>();
        findDistinctMolecules(grammar, word, molecules);

        return molecules.size();
    }

    private static int generateMedicineMolecule(String input) {

        List<Pair> grammar = new ArrayList<>();
        var word = parse(input, grammar);

        int counter = 0;
        List<Set<String>> molecules = new ArrayList<>();
        molecules.add(new HashSet<>());
        molecules.get(0).add("e");

        while (!molecules.get(counter).contains(word)) {
            counter += 1;
            var words = molecules.get(counter - 1);
            var newMolecules = new HashSet<String>();
            for (var m : words) findDistinctMolecules(grammar, m, newMolecules);
            var maxCommonPrefix = 0;
            for (var s : newMolecules) maxCommonPrefix = Integer.max(maxCommonPrefix, commonPrefix(word, s));
            Set<String> candidates = new HashSet<>();
            for (var s : newMolecules) if (maxCommonPrefix == commonPrefix(s, word)) candidates.add(s);
            molecules.add((candidates.size() < 4) ? newMolecules : candidates);
            System.out.println("counter=" + counter +
                    ", commonPrefix=" + maxCommonPrefix + ", numCandidates=" + candidates.size());
        }

        return counter;
    }

    private static int commonPrefix(String s, String t) {

        var sLen = s.length();
        var tLen = t.length();
        var len = Math.min(sLen, tLen);

        for (var i = 0; i < len; i++) if (s.charAt(i) != t.charAt(i)) return i + 1;

        return len;
    }

    private static void findDistinctMolecules(List<Pair> grammar, String word, Set<String> molecules) {
        grammar.forEach(pair -> {
            var index = 0;
            while ((index = word.indexOf(pair.a(), index)) >= 0) {
                var head = word.substring(0, index);
                var tail = word.substring(index + pair.a().length());
                var newMolecule = head + pair.b() + tail;
                molecules.add(newMolecule);
                index += pair.a().length();
            }
        });
    }

    private static String parse(String input, List<Pair> grammar) {

        String word;

        var scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            if (line.length() == 0) break;
            grammar.add(Pair.fromString(line));
        }

        Assertions.assertTrue(scanner.hasNextLine(), "scanner: missing word");

        word = scanner.nextLine();
        return word;
    }
}

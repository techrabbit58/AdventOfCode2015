package com.example.Day22;

import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

@ToString
final class Participant {

    final String name;
    private final Map<String, Integer> properties;

    private Participant(String name) {
        this.properties = new LinkedHashMap<>();
        this.name = name;
    }

    static Participant fromString(String name, String init) {

        var scanner = new Scanner(init);
        var participant = new Participant(name);

        participant.set("Armor", 0);

        while (scanner.hasNextLine()) {
            var parts = scanner.nextLine().trim().split(":\\s+");
            var property = parts[0];
            var value = Integer.parseInt(parts[1]);
            participant.set(property, value);
        }

        return participant;
    }

    void set(String property, int value) {
        properties.put(property, value);
    }

    int get(String property) {
        return properties.getOrDefault(property, 0);
    }
}

package com.example;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Objects;

public class Day03Directions {

    private static record Waypoint(long x, long y) {

        Waypoint goTo(char direction) {
            return switch (direction) {
                case '^' -> new Waypoint(x, y + 1);
                case 'v' -> new Waypoint(x, y - 1);
                case '>' -> new Waypoint(x + 1, y);
                case '<' -> new Waypoint(x - 1, y);
                default -> new Waypoint(x, y);
            };
        }
    }

    @SneakyThrows
    public static void main(String[] args) {

        var file = new File(Objects.requireNonNull(Day03Directions.class.getResource("/directions.txt")).toURI());
        var directions = Files.readString(file.toPath());

        var suppliedHouses = new HashSet<Waypoint>();
        var waypoint = new Waypoint(0, 0);

        var teamVisits = new HashSet<Waypoint>();

        var santaWaypoint = new Waypoint(0, 0);
        var robotWaypoint = new Waypoint(0, 0);

        suppliedHouses.add(waypoint);
        teamVisits.add(waypoint);

        var alternator = 0;

        for (var ch : directions.toCharArray()) {
            waypoint = waypoint.goTo(ch);
            suppliedHouses.add(waypoint);
            if (alternator == 0) {
                santaWaypoint = santaWaypoint.goTo(ch);
                teamVisits.add(santaWaypoint);
            } else {
                robotWaypoint = robotWaypoint.goTo(ch);
                teamVisits.add(robotWaypoint);
            }
            alternator = (alternator + 1) % 2;
        }

        System.out.println("part 1 solution: " + suppliedHouses.size());
        System.out.println("part 2 solution: " + teamVisits.size());
    }
}

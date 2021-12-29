package com.example.Day21;

import com.example.Helpers.Assertions;
import com.example.Helpers.Miscellaneous;

import java.util.ArrayList;

public class RpgSimulator {

    private static final String PUZZLE_INPUT = "Hit Points: 100\nDamage: 8\nArmor: 2";
    private static final String TEST_INPUT = "Hit Points: 12\nDamage: 7\nArmor: 2";

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 21: RPG Simulator 20XX");

        // T E S T   P A R T 1

        var boss = Status.fromString(TEST_INPUT);
        var player = new Status("Player", 8, 5, 5, 0);
        var itemShop = new ItemShop();

        Status winner = simulateOneFight(player, Status.fromString(TEST_INPUT));
        Assertions.assertEquals(player, winner);

        // S O L U T I O N   P A R T 1

        var debtIfWon = new ArrayList<Integer>();
        var debtIfNotWon = new ArrayList<Integer>();

        var weapons = itemShop.weaponNames();
        while (weapons.hasNext()) {

            var nextWeapon = weapons.next();

            var armors = itemShop.armorNames();
            while (armors.hasNext()) {

                var nextArmor = armors.next();

                var rings = itemShop.ringNames();
                while (rings.hasNext()) {

                    boss = Status.fromString(PUZZLE_INPUT);
                    player = nextPlayer(nextWeapon, nextArmor, rings.next());

                    simulateOneFight(player, boss);

                    if (player.getHitPoints() > 0) debtIfWon.add(player.getDebt());
                    if (boss.getHitPoints() > 0) debtIfNotWon.add(player.getDebt());
                }
            }
        }

        System.out.println("part 1 solution: " + debtIfWon.stream().reduce(Integer::min).orElse(Integer.MAX_VALUE));
        System.out.println("part 2 solution: " + debtIfNotWon.stream().reduce(Integer::max).orElse(Integer.MIN_VALUE));
    }

    private static Status nextPlayer(String nextWeapon, String nextArmor, String nextRings) {

        var player = Status.builder().name("Player").hitPoints(100).build();

        player.getWeapon(nextWeapon);
        player.getArmor(nextArmor);
        player.getRings(nextRings);

        return player;
    }

    private static Status simulateOneFight(Status player, Status opponent) {
        var attacker = player;
        var defender = opponent;

        while (!(hasLost(player) || hasLost(opponent))) {

            attack(attacker, defender);

            var bucket = attacker;
            attacker = defender;
            defender = bucket;
        }

        return hasLost(opponent) ? player : opponent;
    }

    private static boolean hasLost(Status player) {
        return player.getHitPoints() <= 0;
    }

    private static void attack(Status attacker, Status defender) {

        var deal = attacker.getDamage() - defender.getArmor();
        if (deal <= 0) deal = 1;

        defender.setHitPoints(defender.getHitPoints() - deal);
    }
}

package com.example.Day21;

import com.example.Helpers.Assertions;
import com.example.Helpers.Miscellaneous;

public class RpgSimulator {

    private static final Status PUZZLE_INPUT = Status.fromString("Hit Points: 100\nDamage: 8\nArmor: 2");
    private static final Status TEST_INPUT = Status.fromString("Hit Points: 12\nDamage: 7\nArmor: 2");

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 21: RPG Simulator 20XX");

        // T E S T   P A R T 1

        var player = new Status("Player", 8, 5, 5, 0);

        Status winner = getWinner(player, TEST_INPUT);
        Assertions.assertEquals(player, winner);

        // S O L U T I O N   P A R T 1

        var boss = PUZZLE_INPUT;

        player.setHitPoints(100);
        player.setDamage(0);
        player.setArmor(0);

        player.buyWeapon("Greataxe");
        winner = getWinner(player, boss);
        System.out.println(winner.getName() + " won!");
        System.out.println("Boss=" + boss + ", Player=" + player);

        System.out.println("part 1 solution: " + 0);
    }

    private static Status getWinner(Status player, Status opponent) {
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

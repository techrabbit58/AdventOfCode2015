package com.example.Day22;

import com.example.Helpers.Miscellaneous;

import java.util.Map;
import java.util.stream.Collectors;

public class WizardSimulator {

    private static final Globals global = Globals.getInstance();

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 22: Wizard Simulator 20XX");

        // T E S T   P A R T 1

        var battleState = parse(global.TEST_INPUT_PLAYER[0], global.TEST_INPUT_BOSS[0]);

        System.out.println(battleState);
    }

    private static BattleState parse(String playerInit, String bossInit) {

        var playerProperties = parseInput(playerInit);
        var bossProperties = parseInput(bossInit);

        return new BattleState()
                .setPlayerHealthPoints(playerProperties.getOrDefault("Hit Points", 0))
                .setPlayerMana(playerProperties.getOrDefault("Mana", 0))
                .setBossHealthPoints(bossProperties.getOrDefault("Hit Points", 0))
                .setBossDamage(bossProperties.getOrDefault("Damage", 0));
    }

    private static Map<String, Integer> parseInput(String init) {

        return init.lines()
                .map(s -> s.split(":\\s+"))
                .collect(Collectors.toMap(
                        a -> a[0],
                        a -> Integer.parseInt(a[1])
                ));
    }

    private static boolean isPlayerWinner(BattleState battleState) {
        return battleState.hasBattleEnded() && battleState.getPlayerHealthPoints() > 0;
    }
}

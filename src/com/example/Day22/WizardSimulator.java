package com.example.Day22;

import com.example.Helpers.Assertions;
import com.example.Helpers.Miscellaneous;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WizardSimulator {

    private static final Globals global = Globals.getInstance();

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 22: Wizard Simulator 20XX");

        // T E S T   P A R T 1

        BattleState battleState;
        List<Integer> manaSpent;

        battleState = parse(global.TEST_INPUT_PLAYER[0], global.TEST_INPUT_BOSS[0]);
        manaSpent = new ArrayList<>();
        dfs(battleState, manaSpent);
        Assertions.assertEquals(226, manaSpent.stream().reduce(Integer::min).orElse(Integer.MAX_VALUE));

        System.out.println("-".repeat(15));

        battleState = parse(global.TEST_INPUT_PLAYER[1], global.TEST_INPUT_BOSS[1]);
        manaSpent = new ArrayList<>();
        dfs(battleState, manaSpent);
        System.out.println(manaSpent);
        Assertions.assertTrue(manaSpent.contains(641), "did not contain desired result");
    }

    private static void dfs(BattleState state, List<Integer> manaSpent) {

        if (manaSpent.contains(state.getManaSpent())) {
            // The result of this battle will create same or higher cost as a previous battle.
            return;
        }

        if (state.hasBattleEnded()) {
            if (state.hasPlayerHealthPoints()) {
                manaSpent.add(state.getManaSpent());
                System.out.println(manaSpent);
            }
        } else {
            for (var spell : state.getAvailableSpells()) {
                dfs(state.roundOfBattle(spell), manaSpent);
                System.out.println(manaSpent);
            }
        }
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
}

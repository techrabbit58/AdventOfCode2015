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
        Assertions.assertEquals(226, min(manaSpent));

        battleState = parse(global.TEST_INPUT_PLAYER[1], global.TEST_INPUT_BOSS[1]);
        manaSpent = new ArrayList<>();
        dfs(battleState, manaSpent);
        Assertions.assertEquals(641, min(manaSpent));

        // P U Z Z L E   P A R T 1

        battleState = parse(global.PUZZLE_INPUT_PLAYER, global.PUZZLE_INPUT_BOSS);
        manaSpent = new ArrayList<>();
        dfs(battleState, manaSpent);
        System.out.println("part 1 solution: " + min(manaSpent));

        // P U Z Z L E   P A R T 2

        battleState = parse(global.PUZZLE_INPUT_PLAYER, global.PUZZLE_INPUT_BOSS);
        manaSpent = new ArrayList<>();
        dfs(battleState, manaSpent, global.HARD_MODE);
        System.out.println("part 2 solution: " + min(manaSpent));
    }

    private static void dfs(BattleState battleState, List<Integer> manaSpent) {
        dfs(battleState, manaSpent, global.EASY_MODE);
    }

    private static int min(List<Integer> manaSpent) {
        return manaSpent.stream().reduce(Integer::min).orElse(Integer.MAX_VALUE);
    }

    private static void dfs(BattleState state, List<Integer> manaSpent, boolean isHardMode) {

        if (state.getAvailableSpells().size() == 0) {
            if (state.hasBattleEnded() && state.hasPlayerHealthPoints()) {
                manaSpent.add(state.getManaSpent());
            }
            return;
        }

        for (var spell : state.getAvailableSpells()) {
            var bucket = deepCopy(state.getActiveEffects());
            if (isHardMode) dfs(state.roundOfBattleHardMode(spell), manaSpent, global.HARD_MODE);
            else dfs(state.roundOfBattle(spell), manaSpent, global.EASY_MODE);
            state.setActiveEffects(bucket);
        }
    }

    private static List<Spell> deepCopy(List<Spell> activeEffects) {
        return activeEffects.stream().map(effect -> effect.toBuilder().build()).toList();
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

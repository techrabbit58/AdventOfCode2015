package com.example.Day22;

import com.example.Helpers.Assertions;
import com.example.Helpers.Miscellaneous;

import java.util.Map;
import java.util.stream.Collectors;

public class WizardSimulator {

    private static final Globals global = Globals.getInstance();

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 22: Wizard Simulator 20XX");

        // T E S T   P A R T 1

        var battleState = parse(global.TEST_INPUT_PLAYER[0], global.TEST_INPUT_BOSS[0]);
        var spellsToGo = new Spell[]{
                global.getSpellByName("Poison"),
                global.getSpellByName("Magic Missile")
        };
        for (var spell : spellsToGo) {
            battleState = battleState.roundOfBattle(spell);
            if (battleState.hasBattleEnded()) break;
        }
        Assertions.assertEquals(226, battleState.getManaSpent());

        battleState = parse(global.TEST_INPUT_PLAYER[1], global.TEST_INPUT_BOSS[1]);
        spellsToGo = new Spell[]{
                global.getSpellByName("Recharge"),
                global.getSpellByName("Shield"),
                global.getSpellByName("Drain"),
                global.getSpellByName("Poison"),
                global.getSpellByName("Magic Missile")
        };
        for (var spell : spellsToGo) {
            battleState = battleState.roundOfBattle(spell);
            if (battleState.hasBattleEnded()) break;
        }
        Assertions.assertEquals(641, battleState.getManaSpent());

        // S O L U T I O N   P A R T 1

        battleState = parse(global.PUZZLE_INPUT_PLAYER, global.PUZZLE_INPUT_BOSS);
        var minMana = dfs(battleState, Integer.MAX_VALUE);

        System.out.println(minMana);
    }

    private static int dfs(BattleState state, int minManaSoFar) {

        if (state.getManaSpent() > minManaSoFar) {
            return minManaSoFar;
        }

        if (state.getAvailableSpells().size() == 0) {
            return isPlayerWinner(state) ? state.getManaSpent() : minManaSoFar;
        }

        for (var spell : state.getAvailableSpells()) {
            var bucket = dfs(state.roundOfBattle(spell), minManaSoFar);
            minManaSoFar = Math.min(bucket, minManaSoFar);
        }

        return minManaSoFar;
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

    private static boolean isPlayerWinner(BattleState state) {
        return state.hasBattleEnded() && state.hasPlayerHealthPoints();
    }
}

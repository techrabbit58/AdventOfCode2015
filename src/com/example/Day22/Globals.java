package com.example.Day22;

import java.util.Arrays;
import java.util.List;

final class Globals {

    private static final Globals _instance = new Globals();

    private Globals() {
    }

    static Globals getInstance() {
        return _instance;
    }

    final String[] TEST_INPUT_PLAYER = {
            "Hit Points: 10\nMana: 250",
            "Hit Points: 10\nMana: 250"
    };

    final String[] TEST_INPUT_BOSS = {
            "Hit Points: 13\nDamage: 8",
            "Hit Points: 14\nDamage: 8"
    };

    final String PUZZLE_INPUT_PLAYER = "Hit Points: 50\nMana: 500";
    final String PUZZLE_INPUT_BOSS = "Hit Points: 71\nDamage: 10";

    private static final Spell[] spells = new Spell[] {
            Spell.builder().name("Magic Missile").consumedMana(53).instantDamage(4).build(),
            Spell.builder().name("Drain").consumedMana(73).instantDamage(2).healing(2).build(),
            Spell.builder().name("Shield").consumedMana(113).effectArmor(7).effectDuration(6).build(),
            Spell.builder().name("Poison").consumedMana(173).effectDamage(3).effectDuration(6).build(),
            Spell.builder().name("Recharge").consumedMana(229).effectMana(101).effectDuration(5).build()
    };

    Spell getSpellByName(String name) {
        return Arrays.stream(spells).dropWhile(spell -> !name.equals(spell.getName())).findFirst().orElseThrow();
    }

    List<String> getSpells() {
        return Arrays.stream(spells).map(Spell::getName).toList();
    }
}

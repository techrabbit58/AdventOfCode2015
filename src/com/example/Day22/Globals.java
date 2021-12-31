package com.example.Day22;

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

    final Spell[] spells = new Spell[] {
            Spell.builder().name("Magic Missile").cost(53).instantDamage(4).effectDuration(1).build(),
            Spell.builder().name("Drain").cost(73).instantDamage(2).healing(2).effectDuration(1).build(),
            Spell.builder().name("Shield").cost(113).effectArmor(7).effectDuration(6).build(),
            Spell.builder().name("Poison").cost(173).effectDamage(3).effectDuration(6).build(),
            Spell.builder().name("Recharge").cost(229).effectMana(101).effectDuration(5).build()
    };
}

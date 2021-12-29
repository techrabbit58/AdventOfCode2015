package com.example.Day22;

import com.example.Helpers.Miscellaneous;

public class WizardSimulator {

    private static final String[] TEST_INPUT_PLAYER = {
            "Hit Points: 10\nMana: 250",
            "Hit Points: 10\nMana: 250"
    };

    private static final String[] TEST_INPUT_BOSS = {
            "Hit Points: 13\nDamage: 8",
            "Hit Points: 14\nDamage: 8"
    };

    private static final String PUZZLE_INPUT_PLAYER = "Hit Points: 50\nMana: 500";
    private static final String PUZZLE_INPUT_BOSS = "Hit Points: 71\nDamage: 10";

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 22: Wizard Simulator 20XX");
    }
}

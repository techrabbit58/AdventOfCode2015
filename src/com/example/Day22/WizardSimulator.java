package com.example.Day22;

import com.example.Helpers.Miscellaneous;

public class WizardSimulator {

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 22: Wizard Simulator 20XX");

        var globals = Globals.getInstance();

        globals.getSpells().forEach(n -> System.out.println(globals.getSpellByName(n)));

        var player = new Wizard(globals.TEST_INPUT_PLAYER[0]);
        player.addSpell(globals.getSpellByName("Poison"));

        var boss = new Boss(globals.TEST_INPUT_BOSS[0]);

        System.out.println();
        System.out.println(player);
        System.out.println(boss);

        player.attack(boss);
        boss.attack(player);

        System.out.println();
        System.out.println(player);
        System.out.println(boss);
    }
}

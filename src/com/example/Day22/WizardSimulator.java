package com.example.Day22;

import com.example.Helpers.Miscellaneous;

public class WizardSimulator {

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 22: Wizard Simulator 20XX");

        var globals = Globals.getInstance();

        var player = new Wizard(globals.TEST_INPUT_PLAYER[0]);
        player.addSpellToScript("Poison");

        var boss = new Boss(globals.TEST_INPUT_BOSS[0]);

        var gameMaster = new GameMaster();
        gameMaster.addPlayer(player);
        gameMaster.addPlayer(boss);

        gameMaster.run();
    }
}

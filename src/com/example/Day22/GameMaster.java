package com.example.Day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GameMaster {

    final List<Player> players;
    final Map<Command, Integer> activeEffects;
    final Globals globals;

    GameMaster() {
        players = new ArrayList<>();
        activeEffects = new HashMap<>();
        globals = Globals.getInstance();
    }

    void addPlayer(Player player) {
        if (player instanceof Wizard) ((Wizard) player).allow(this::submit);
        players.add(player);
    }

    Integer submit(Command command) {

        if (!globals.getSpells().contains(command.spell()))
            throw new RuntimeException("unrecognized spell: '" + command.spell() + "'");

        if (activeEffects.containsKey(command)) return 0;

        var spell = globals.getSpellByName(command.spell());
        System.out.println(command.player().getName() + " casts " + command.spell() + ".");

        var instantDamage = spell.getInstantDamage();
        if (instantDamage > 0) proxyAttack(command.player(), command.opponent(), spell.getName(), instantDamage);

        var effectDuration = spell.getEffectDuration();
        if (effectDuration > 0) activeEffects.put(command, effectDuration);

        return spell.getConsumedMana();
    }

    private void proxyAttack(Player player, Player opponent, String spell, int damage) {

        System.out.println(player.getName() + " casts " + spell + ", dealing " + damage + " damage.");
        opponent.set("Hit Points", opponent.get("Hit Points") - damage);
    }

    private void doActiveEffects() {

        activeEffects.entrySet().forEach(e -> {
            System.out.println("k=" + e.getKey() + ", v=" + e.getValue());
            e.setValue(e.getValue() - 1);
        });
    }

    void run() {

        System.out.println("-- " + players.get(0).getName() + " turn --");
        System.out.println(players.get(0));
        System.out.println(players.get(1));
        doActiveEffects();
        players.get(0).attack(players.get(1));
        System.out.println();

        System.out.println("-- " + players.get(1).getName() + " turn --");
        System.out.println(players.get(0));
        System.out.println(players.get(1));
        doActiveEffects();
        players.get(1).attack(players.get(0));
        System.out.println();
    }
}

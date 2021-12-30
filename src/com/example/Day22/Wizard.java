package com.example.Day22;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Wizard implements Player {

    private final Participant self;
    private final List<String> spellScript;
    private int nextSpell;
    private Function<Command, Integer> cast;

    public Wizard(String init) {
        self = Participant.fromString("Player", init);
        spellScript = new ArrayList<>();
        nextSpell = 0;
    }

    void addSpellToScript(String spell) {
        spellScript.add(spell);
    }

    @Override
    public void set(String property, int value) {
        self.set(property, value);
    }

    @Override
    public void attack(Player opponent) {
        // cast spell via game master, if currently allowed and affordable
        var cost = cast.apply(new Command(this, opponent, spellScript.get(nextSpell)));
        if (cost > 0){
            self.set("Expense", self.get("Expense") + cost);
            self.set("Mana", self.get("Mana") - cost);
            nextSpell += 1;
        }
    }

    @Override
    public String getName() {
        return self.name;
    }

    @Override
    public int get(String property) {
        return self.get(property);
    }

    void allow(Function<Command, Integer> callback) {
        cast = callback;
    }

    @Override
    public String toString() {
        return "- " + self.name + " has " + self.get("Hit Points") + " hit points, "
                + self.get("Armor") + " armor, " + self.get("Mana") + " mana";
    }
}

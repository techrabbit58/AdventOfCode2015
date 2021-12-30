package com.example.Day22;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ToString
public final class Wizard implements Player {

    private final Participant self;
    private final List<Spell> script;
    private int cursor;

    public Wizard(String init) {
        self = Participant.fromString("Player", init);
        script = new ArrayList<>();
        cursor = 0;
    }

    void addSpell(Spell spell) {
        script.add(spell);
    }

    @Override
    public void set(String property, int value) {
        self.set(property, value);
    }

    @Override
    public void attack(Player opponent) {
        System.out.println("-- " + self.name + " turn --");
        // cast spell via game master, if currently allowed
        System.out.println(self.name + " casts " + script.get(cursor).getName() + ".");
    }

    @Override
    public String getName() {
        return self.name;
    }

    @Override
    public int get(String property) {
        return self.get(property);
    }
}

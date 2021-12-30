package com.example.Day22;

import lombok.ToString;

@ToString
public final class Boss implements Player {

    private final Participant self;

    Boss(String init) {
        this.self = Participant.fromString("Boss", init);
    }

    @Override
    public void set(String property, int value) {
        self.set(property, value);
    }

    @Override
    public String getName() {
        return self.name;
    }

    @Override
    public int get(String property) {
        return self.get(property);
    }

    @Override
    public void attack(Player opponent) {

        System.out.println("-- " + self.name + " turn --");
        var damage = self.get("Damage") - opponent.get("Armor");
        if (damage < 1) damage = 1;
        System.out.println(self.name + " attacks for " + damage + " damage.");
        opponent.set("Hit Points", opponent.get("Hit Points") - damage);
    }
}

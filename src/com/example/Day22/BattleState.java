package com.example.Day22;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ToString(exclude = {"global", "activeEffects"})
public class BattleState {

    private int playerHealthPoints = 0;
    private int playerMana = 0;
    private int playerArmor = 0;
    private int bossHealthPoints = 0;
    private int bossDamage = 0;
    private int manaSpent = 0;
    private List<Spell> activeEffects = new ArrayList<>();
    private final Globals global = Globals.getInstance();

    BattleState() {
    }

    boolean hasBattleEnded() {
        return bossHealthPoints <= 0 || playerHealthPoints <= 0;
    }

    List<Spell> getAvailableSpells() {

        if (hasBattleEnded()) return new ArrayList<>();

        return Arrays.stream(global.spells).filter(spell -> {
            var active = activeEffects.stream()
                    .filter(effect -> effect.getName().equals(spell.getName()))
                    .findFirst().orElse(null);
            return spell.getCost() <= playerMana && (active == null || active.getEffectDuration() == 1);
        }).toList();
    }

    BattleState enactEffects() {

        if (hasBattleEnded()) return this;

        activeEffects.forEach(effect -> {
            switch (effect.getName()) {
                case "Poison" -> System.out.println(effect.getName() + " deals " + effect.getEffectDamage()
                        + " damage; its timer is now " + effect.getEffectDuration() + ".");
                case "Shield" -> System.out.println(effect.getName() + "'s timer is now "
                        + effect.getEffectDuration() + ".");
                case "Recharge" -> System.out.println(effect.getName() + " provides " + effect.getEffectMana()
                        + " mana; its timer is now " + effect.getEffectDuration() + ".");
                default -> throw new RuntimeException("unexpected effect: " + effect.getName());
            }
        });

        return new BattleState()
                .setPlayerHealthPoints(playerHealthPoints)
                .setPlayerMana(playerMana
                        + activeEffects.stream().map(Spell::getHealing).reduce(Integer::sum).orElse(0))
                .setPlayerArmor(activeEffects.stream().map(Spell::getEffectArmor).reduce(Integer::sum).orElse(0))
                .setBossHealthPoints(bossHealthPoints
                        - activeEffects.stream().map(Spell::getEffectDamage).reduce(Integer::sum).orElse(0))
                .setBossDamage(bossDamage)
                .setManaSpent(manaSpent)
                .setActiveEffects(activeEffects.stream()
                        .filter(effect -> effect.getEffectDuration() > 1)
                        .peek(effect -> effect.setEffectDuration(effect.getEffectDuration() - 1))
                        .toList());
    }

    BattleState playerTurn(Spell spell) {

        if (hasBattleEnded()) return this;

        System.out.print("Player casts " + spell.getName());

        var isSpellEffect = spell.getEffectDuration() > 1;

        if (isSpellEffect) {
            var newList = new ArrayList<>(activeEffects);
            newList.add(spell.toBuilder().build());
            activeEffects = newList;
            System.out.println(".");
        } else {
            switch (spell.getName()) {
                case "Magic Missile" -> System.out.println(", dealing " + spell.getInstantDamage() + " damage.");
                case "Drain" -> System.out.println(", dealing " + spell.getInstantDamage()
                        + " damage, and healing " + spell.getHealing() + " hit points.");
                default -> throw new RuntimeException("unexpected spell '" + spell.getName());
            }
        }

        return new BattleState()
                .setPlayerHealthPoints(playerHealthPoints + (isSpellEffect ? 0 : spell.getHealing()))
                .setPlayerMana(playerMana - spell.getCost())
                .setPlayerArmor(playerArmor)
                .setBossHealthPoints(bossHealthPoints - (isSpellEffect ? 0 : spell.getInstantDamage()))
                .setBossDamage(bossDamage)
                .setManaSpent(manaSpent + spell.getCost())
                .setActiveEffects(List.copyOf(activeEffects));
    }

    BattleState bossTurn() {

        if (hasBattleEnded()) return this;

        System.out.println("Boss attacks for " + Math.max(bossDamage - playerArmor, 1) + " damage.");

        return new BattleState()
                .setPlayerHealthPoints(playerHealthPoints - Math.max(bossDamage - playerArmor, 1))
                .setPlayerMana(playerMana)
                .setPlayerArmor(playerArmor)
                .setBossHealthPoints(bossHealthPoints)
                .setBossDamage(bossDamage)
                .setManaSpent(manaSpent)
                .setActiveEffects(List.copyOf(activeEffects));
    }

    BattleState roundOfBattle(Spell spell) {

        System.out.println("-- Player turn ---");
        System.out.println(
                "- Player has " + playerHealthPoints + " hit points, "
                        + playerArmor + " armor, " + playerMana + " mana");
        System.out.println("- Boss has " + bossHealthPoints + " hit points");

        var state = enactEffects();

        System.out.println();

        state = state.playerTurn(spell);

        System.out.println("-- Boss turn --");
        System.out.println(
                "- Player has " + playerHealthPoints + " hit points, "
                        + playerArmor + " armor, " + playerMana + " mana");
        System.out.println("- Boss has " + bossHealthPoints + " hit points");

        state = state.enactEffects();

        System.out.println();

        state = state.bossTurn();

        return state;
    }

    public BattleState setPlayerHealthPoints(int playerHealthPoints) {
        this.playerHealthPoints = playerHealthPoints;
        return this;
    }

    public BattleState setPlayerMana(int playerMana) {
        this.playerMana = playerMana;
        return this;
    }

    public BattleState setPlayerArmor(int playerArmor) {
        this.playerArmor = playerArmor;
        return this;
    }

    public BattleState setBossHealthPoints(int bossHealthPoints) {
        this.bossHealthPoints = bossHealthPoints;
        return this;
    }

    public BattleState setBossDamage(int bossDamage) {
        this.bossDamage = bossDamage;
        return this;
    }

    public BattleState setManaSpent(int manaSpent) {
        this.manaSpent = manaSpent;
        return this;
    }

    public BattleState setActiveEffects(List<Spell> activeEffects) {
        this.activeEffects = activeEffects;
        return this;
    }

    public int getPlayerHealthPoints() {
        return playerHealthPoints;
    }

    public int getPlayerMana() {
        return playerMana;
    }

    public int getPlayerArmor() {
        return playerArmor;
    }

    public int getBossHealthPoints() {
        return bossHealthPoints;
    }

    public int getBossDamage() {
        return bossDamage;
    }

    public int getManaSpent() {
        return manaSpent;
    }

    public List<Spell> getActiveEffects() {
        return activeEffects;
    }
}

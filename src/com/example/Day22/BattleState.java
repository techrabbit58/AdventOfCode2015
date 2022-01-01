package com.example.Day22;

import lombok.ToString;

import java.util.*;

@ToString(exclude = {"global"})
public class BattleState {

    private int playerHealthPoints = 0;
    private int playerMana = 0;
    private int playerArmor = 0;
    private int bossHealthPoints = 0;
    private int bossDamage = 0;
    private int manaSpent = 0;
    private List<Spell> activeEffects = Collections.emptyList();
    private final Globals global = Globals.getInstance();

    BattleState() {
    }

    boolean hasBattleEnded() {
        return bossHealthPoints <= 0 || playerHealthPoints <= 0;
    }

    boolean hasPlayerHealthPoints() {
        return playerHealthPoints > 0;
    }

    List<Spell> getAvailableSpells() {

        if (hasBattleEnded()) return Collections.emptyList();

        return Arrays.stream(global.spells)
                .filter(spell -> {
                    var isActive = activeEffects.stream()
                            .filter(effect -> effect.getEffectDuration() > 1)
                            .anyMatch(effect -> effect.getName().equals(spell.getName()));
                    return spell.getCost() <= playerMana && (spell.getEffectDuration() == 1 || !isActive);
                }).toList();
    }

    BattleState enactEffects() {

        if (hasBattleEnded()) return this;

        return new BattleState()
                .setPlayerHealthPoints(playerHealthPoints)
                .setPlayerMana(playerMana
                        + activeEffects.stream().map(Spell::getEffectMana).reduce(Integer::sum).orElse(0))
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

    boolean isPlayerBroke() {

        var availableSpells = getAvailableSpells();
        var minCost = availableSpells.stream().map(Spell::getCost).reduce(Integer::min).orElse(0);
        var healthUpdate = activeEffects.stream().map(Spell::getEffectMana).reduce(Integer::sum).orElse(0);

        return minCost <= playerMana || healthUpdate > 0;
    }

    BattleState playerTurn(Spell spell) {

        if (hasBattleEnded()) return this;

        var isSpellEffect = spell.getEffectDuration() > 1;

        if (isSpellEffect) {
            var newList = new ArrayList<>(activeEffects);
            newList.add(spell.toBuilder().build());
            activeEffects = newList;
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
        return enactEffects().playerTurn(spell).enactEffects().bossTurn();
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

    public int getManaSpent() {
        return manaSpent;
    }

}

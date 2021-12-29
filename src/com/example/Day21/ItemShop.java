package com.example.Day21;

import com.example.Helpers.IntCombinations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ItemShop {

    Map<String, Map<String, Item>> articles;

    public ItemShop() {

        articles = new HashMap<>();

        articles.put("Weapons", new HashMap<>());

        articles.get("Weapons").put("Dagger", new Item(8, 4, 0));
        articles.get("Weapons").put("Shortsword", new Item(10, 5, 0));
        articles.get("Weapons").put("Warhammer", new Item(25, 6, 0));
        articles.get("Weapons").put("Longsword", new Item(40, 7, 0));
        articles.get("Weapons").put("Greataxe", new Item(74, 8, 0));

        articles.put("Armor", new HashMap<>());

        articles.get("Armor").put("No Armor", new Item(0, 0, 0));
        articles.get("Armor").put("Leather", new Item(13, 0, 1));
        articles.get("Armor").put("Chainmail", new Item(31, 0, 2));
        articles.get("Armor").put("Splintmail", new Item(53, 0, 3));
        articles.get("Armor").put("Bandedmail", new Item(75, 0, 4));
        articles.get("Armor").put("Platemail", new Item(102, 0, 5));

        articles.put("Rings", new HashMap<>());

        articles.get("Rings").put("Damage +1", new Item(25, 1, 0));
        articles.get("Rings").put("Damage +2", new Item(50, 2, 0));
        articles.get("Rings").put("Damage +3", new Item(100, 3, 0));
        articles.get("Rings").put("Defense +1", new Item(20, 0, 1));
        articles.get("Rings").put("Defense +2", new Item(40, 0, 2));
        articles.get("Rings").put("Defense +3", new Item(80, 0, 3));
    }

    public Iterator<String> weaponNames() {
        return List.copyOf(articles.get("Weapons").keySet()).listIterator();
    }

    public Item getWeaponByName(String weaponName) {
        return articles.get("Weapons").get(weaponName);
    }

    public Item getArmorByName(String armorName) {
        return articles.get("Armor").get(armorName);
    }

    public Iterator<String> armorNames() {
        return List.copyOf(articles.get("Armor").keySet()).listIterator();
    }

    public Iterator<String> ringNames() {
        return new Iterator<>() {

            boolean terminated = false;
            int numRings = 0;
            final List<String> ringNames = List.copyOf(articles.get("Rings").keySet());
            int cursor;
            List<int[]> combined;

            @Override
            public boolean hasNext() {

                return !terminated;
            }

            @Override
            public String next() {

                if (numRings == 0) {
                    numRings = 1;
                    cursor = 0;
                    return "None";
                } else if (numRings == 1) {
                    if (cursor == ringNames.size()) {
                        numRings = 2;
                        combined = IntCombinations.generate(ringNames.size(), 2);
                        cursor = 0;
                        var current = combined.get(cursor++);
                        return ringNames.get(current[0]) + "/" + ringNames.get(current[1]);
                    }
                    return ringNames.get(cursor++);
                } else {
                    if (cursor == combined.size() - 1) terminated = true;
                    if (cursor < combined.size()) {
                        var current = combined.get(cursor++);
                        return ringNames.get(current[0]) + "/" + ringNames.get(current[1]);
                    }
                }
                return "";
            }
        };
    }

    public Item getRingByName(String ringName) {
        return articles.get("Rings").getOrDefault(ringName, new Item(0, 0, 0));
    }
}
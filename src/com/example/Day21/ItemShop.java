package com.example.Day21;

import java.util.HashMap;
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

    public Item getWeapon(String itemName) {
        return articles.get("Weapons").get(itemName);
    }
}
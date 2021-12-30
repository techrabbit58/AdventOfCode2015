package com.example.Day21;

import lombok.*;

import java.util.Scanner;

@Data
@Builder
public class Participant {

    @Builder.Default private String name = "Boss";
    @Builder.Default private int hitPoints = 0;
    @Builder.Default private int damage = 0;
    @Builder.Default private int armor = 0;
    @Builder.Default private int debt = 0;

    private static ItemShop itemShop = new ItemShop();

    public static Participant fromString(String s) {

        var scanner = new Scanner(s);
        var statusBuilder = Participant.builder();

        while (scanner.hasNextLine()) {

            var parts = scanner.nextLine().split("\s*:\s*");

            switch (parts[0].toLowerCase()) {
                case "hit points" -> statusBuilder.hitPoints(Integer.parseInt(parts[1]));
                case "damage" -> statusBuilder.damage(Integer.parseInt(parts[1]));
                case "armor" -> statusBuilder.armor(Integer.parseInt(parts[1]));
                default -> throw new RuntimeException("unexpected keyword: '" + parts[0] + "'");
            }
        }

        return statusBuilder.build();
    }

    public void getWeapon(String weapon) {
        update(itemShop.getWeaponByName(weapon));
    }

    public void getArmor(String armor) {
        update(itemShop.getArmorByName(armor));
    }

    public void getRings(String rings) {

        var parts = rings.split("/");

        update(itemShop.getRingByName(parts[0]));
        if (parts.length == 2) update(itemShop.getRingByName(parts[1]));
    }

    private void update(Item item) {

        debt += item.cost();
        armor += item.armor();
        damage += item.damage();
    }
}

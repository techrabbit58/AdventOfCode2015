package com.example.Day22;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Spell {

    private String name;
    @Builder.Default private int healing = 0;
    private int consumedMana;
    @Builder.Default private int instantDamage = 0;
    @Builder.Default private int effectDamage = 0;
    @Builder.Default private int effectArmor = 0;
    @Builder.Default private int effectMana = 0;
    @Builder.Default private int effectDuration = 0;
}

package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.Warrior;
import com.enlil.universe.civilization.occupation.Weapon;

import java.util.Set;

/**
 * A samurai, the hereditary warrior class of Japan from the Kamakura shogunate in 1185 until the Haitorei edict
 * forbade them to wear swords in 1876. Mounted archers first, swordsmen later, bound by bushido.
 */
public class Samurai extends Warrior {

    public Samurai() {
        super("Samurai", 1185, 1876, Set.of(Weapon.KATANA, Weapon.BOW), "Bushido", true);
    }
}

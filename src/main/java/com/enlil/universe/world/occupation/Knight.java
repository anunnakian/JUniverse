package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.Warrior;
import com.enlil.universe.civilization.occupation.Weapon;

import java.util.Set;

/**
 * A medieval knight, a mounted warrior of Europe from around 1000, bound by chivalry and made a knight by an
 * accolade, not by birth. Gunpowder drove knights off the battlefield by around 1600; knighthood lives on only as
 * an honour.
 */
public class Knight extends Warrior {

    public Knight() {
        super("Knight", 1000, 1600, Set.of(Weapon.SWORD, Weapon.LANCE), "Chivalry", false);
    }
}

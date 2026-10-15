package com.enlil.universe.civilization.occupation;

/** Weapons carried by armed occupations, from the blade to the firearm. */
public enum Weapon {
    SWORD,
    KATANA,
    SPEAR,
    LANCE,
    BOW,
    BATON,
    MUSKET,
    HANDGUN,
    RIFLE;

    public boolean isFirearm() {
        return this == MUSKET || this == HANDGUN || this == RIFLE;
    }
}

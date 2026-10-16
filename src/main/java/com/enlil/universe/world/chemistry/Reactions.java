package com.enlil.universe.world.chemistry;

import com.enlil.universe.matter.chemistry.Reaction;

import java.util.List;

import static com.enlil.universe.world.chemistry.Compounds.*;

/**
 * Real reactions, each balanced by {@link Reaction#balance}.
 */
public final class Reactions {

    /** Burning natural gas: CH4 + 2 O2 → CO2 + 2 H2O. */
    public static final Reaction METHANE_COMBUSTION =
            Reaction.balance(List.of(METHANE, OXYGEN_GAS), List.of(CARBON_DIOXIDE, WATER));
    /** Cellular respiration, the energy of every animal: C6H12O6 + 6 O2 → 6 CO2 + 6 H2O. */
    public static final Reaction GLUCOSE_COMBUSTION =
            Reaction.balance(List.of(GLUCOSE, OXYGEN_GAS), List.of(CARBON_DIOXIDE, WATER));
    /** How plants store sunlight: 6 CO2 + 6 H2O → C6H12O6 + 6 O2. */
    public static final Reaction PHOTOSYNTHESIS =
            Reaction.balance(List.of(CARBON_DIOXIDE, WATER), List.of(GLUCOSE, OXYGEN_GAS));
    /** 4 Fe + 3 O2 → 2 Fe2O3. */
    public static final Reaction RUSTING = Reaction.balance(List.of(IRON, OXYGEN_GAS), List.of(IRON_III_OXIDE));
    /** Ammonia synthesis, behind most of the world's fertilizer: N2 + 3 H2 → 2 NH3. */
    public static final Reaction HABER_PROCESS =
            Reaction.balance(List.of(NITROGEN_GAS, HYDROGEN_GAS), List.of(AMMONIA));
    /** HCl + NaOH → NaCl + H2O. */
    public static final Reaction NEUTRALIZATION =
            Reaction.balance(List.of(HYDROCHLORIC_ACID, SODIUM_HYDROXIDE), List.of(SODIUM_CHLORIDE, WATER));

    private Reactions() {
    }
}

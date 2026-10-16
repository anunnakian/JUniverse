package com.enlil.universe.cosmology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RedshiftTest {

    private static final double H_ALPHA_NANOMETERS = 656.3;

    @Test
    @DisplayName("Hα observed at 721.9 nm should be redshifted by z = 0.1")
    void from_wavelengths() {
        Redshift redshift = Redshift.fromWavelengths(721.9, H_ALPHA_NANOMETERS);

        Assertions.assertEquals(0.1, redshift.getZ(), 0.001);
        Assertions.assertFalse(redshift.isBlueshift());
        Assertions.assertEquals(721.9, Redshift.of(0.1).observedWavelengthNanometers(H_ALPHA_NANOMETERS), 0.1);
    }

    @Test
    @DisplayName("Andromeda, coming closer, should be blueshifted")
    void blueshift() {
        Redshift andromeda = Redshift.of(-0.001);

        Assertions.assertTrue(andromeda.isBlueshift());
        Assertions.assertEquals(0.999, andromeda.stretchFactor(), 1e-12);
    }

    @Test
    @DisplayName("should reject z = -1")
    void reject_minus_one() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Redshift.of(-1));
    }
}

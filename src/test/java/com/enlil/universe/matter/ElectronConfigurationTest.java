package com.enlil.universe.matter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElectronConfigurationTest {

    @Test
    @DisplayName("should fill subshells in the Madelung order")
    void madelung_order() {
        Assertions.assertEquals("1s1", new ElectronConfiguration(1).toString());
        Assertions.assertEquals("1s2 2s2 2p2", new ElectronConfiguration(6).toString());
        Assertions.assertEquals("1s2 2s2 2p6 3s2 3p6 4s2 3d6", new ElectronConfiguration(26).toString());
    }

    @Test
    @DisplayName("noble gases should have closed shells")
    void noble_gases_closed() {
        Assertions.assertTrue(new ElectronConfiguration(2).isClosedShell());
        Assertions.assertTrue(new ElectronConfiguration(10).isClosedShell());
        Assertions.assertFalse(new ElectronConfiguration(11).isClosedShell());
    }

    @Test
    @DisplayName("should count the occupied shells")
    void shell_count() {
        Assertions.assertEquals(0, new ElectronConfiguration(0).getShellCount());
        Assertions.assertEquals(3, new ElectronConfiguration(17).getShellCount());
        Assertions.assertEquals(4, new ElectronConfiguration(26).getShellCount());
    }

    @Test
    @DisplayName("should reject more electrons than the subshells hold")
    void reject_too_many_electrons() {
        Assertions.assertDoesNotThrow(() -> new ElectronConfiguration(118));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ElectronConfiguration(119));
    }
}

package com.enlil.universe.life.human.identity;

import com.enlil.universe.life.human.Sex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IdentityTest {

    @Test
    @DisplayName("a woman assigned female at birth should be cisgender, a woman assigned male transgender")
    void cisgender_and_transgender() {
        Identity woman = new Identity(GenderIdentity.WOMAN, SexualOrientation.HETEROSEXUAL, Pronouns.SHE_HER);

        Assertions.assertTrue(woman.isCisgender(Sex.FEMALE));
        Assertions.assertFalse(woman.isTransgender(Sex.FEMALE));
        Assertions.assertTrue(woman.isTransgender(Sex.MALE));
        Assertions.assertFalse(woman.isLgbtq(Sex.FEMALE));
        Assertions.assertTrue(woman.isLgbtq(Sex.MALE));
    }

    @Test
    @DisplayName("a non-binary person should be neither cisgender woman nor man, whatever their sex at birth")
    void non_binary() {
        Identity nonBinary = new Identity(GenderIdentity.NON_BINARY, SexualOrientation.PANSEXUAL, Pronouns.THEY_THEM);

        Assertions.assertTrue(GenderIdentity.NON_BINARY.isNonBinary());
        Assertions.assertTrue(GenderIdentity.AGENDER.isNonBinary());
        Assertions.assertFalse(GenderIdentity.MAN.isNonBinary());
        Assertions.assertTrue(nonBinary.isTransgender(Sex.FEMALE));
        Assertions.assertTrue(nonBinary.isTransgender(Sex.MALE));
    }

    @Test
    @DisplayName("every orientation but heterosexual should belong to the LGBTQ+ community")
    void orientations() {
        for (SexualOrientation orientation : SexualOrientation.values()) {
            Assertions.assertEquals(orientation != SexualOrientation.HETEROSEXUAL, orientation.isLgbtq());
        }
        Identity gayMan = new Identity(GenderIdentity.MAN, SexualOrientation.GAY, Pronouns.HE_HIM);
        Assertions.assertTrue(gayMan.isCisgender(Sex.MALE));
        Assertions.assertTrue(gayMan.isLgbtq(Sex.MALE));
    }

    @Test
    @DisplayName("pronouns should have three forms, and neopronouns should be welcome")
    void pronouns() {
        Pronouns xe = new Pronouns("xe", "xem", "xyr");

        Assertions.assertEquals("they/them", Pronouns.THEY_THEM.toString());
        Assertions.assertEquals("xyr", xe.getPossessive());
        Assertions.assertEquals(new Pronouns("she", "her", "her"), Pronouns.SHE_HER);
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Pronouns("ze", "", "zir"));
        Assertions.assertThrows(NullPointerException.class,
                () -> new Identity(GenderIdentity.WOMAN, null, Pronouns.SHE_HER));
    }

    @Test
    @DisplayName("a non-binary person should decide whether they call themselves trans, and stay gender-diverse")
    void non_binary_self_description() {
        Identity notTrans = new Identity(GenderIdentity.NON_BINARY, SexualOrientation.QUEER, Pronouns.THEY_THEM, false);
        Identity trans = new Identity(GenderIdentity.AGENDER, SexualOrientation.ASEXUAL, Pronouns.THEY_THEM, true);

        Assertions.assertFalse(notTrans.isTransgender(Sex.FEMALE));
        Assertions.assertTrue(notTrans.isGenderDiverse(Sex.FEMALE));
        Assertions.assertTrue(notTrans.isLgbtq(Sex.FEMALE));
        Assertions.assertTrue(trans.isTransgender(Sex.MALE));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Identity(GenderIdentity.WOMAN, SexualOrientation.LESBIAN, Pronouns.SHE_HER, false));
    }
}

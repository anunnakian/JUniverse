package com.enlil.universe.life.human.identity;

import com.enlil.universe.life.human.Sex;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Objects;

/**
 * Who a person is beyond their body: their gender identity, their sexual orientation and their pronouns. Being
 * cisgender or transgender is not a property of the identity alone but of how it relates to the sex assigned at
 * birth. Non-binary people may or may not call themselves trans: they can say so, and are counted as trans when they
 * do not, as most surveys do.
 */
@Getter
public class Identity {

    private final GenderIdentity genderIdentity;
    private final SexualOrientation sexualOrientation;
    private final Pronouns pronouns;
    /** Whether a non-binary person calls themselves trans; null when they did not say, or for a binary gender. */
    @Getter(AccessLevel.NONE)
    private final Boolean callsSelfTrans;

    public Identity(GenderIdentity genderIdentity, SexualOrientation sexualOrientation, Pronouns pronouns) {
        this(genderIdentity, sexualOrientation, pronouns, null);
    }

    /**
     * A non-binary identity, saying whether its person calls themselves trans.
     *
     * @throws IllegalArgumentException for a binary gender, whose trans status follows from the sex assigned at birth
     */
    public Identity(GenderIdentity genderIdentity, SexualOrientation sexualOrientation, Pronouns pronouns,
                    boolean callsSelfTrans) {
        this(genderIdentity, sexualOrientation, pronouns, Boolean.valueOf(callsSelfTrans));
        if (!genderIdentity.isNonBinary()) {
            throw new IllegalArgumentException("only non-binary people choose whether they call themselves trans");
        }
    }

    private Identity(GenderIdentity genderIdentity, SexualOrientation sexualOrientation, Pronouns pronouns,
                     Boolean callsSelfTrans) {
        this.genderIdentity = Objects.requireNonNull(genderIdentity, "gender identity");
        this.sexualOrientation = Objects.requireNonNull(sexualOrientation, "sexual orientation");
        this.pronouns = Objects.requireNonNull(pronouns, "pronouns");
        this.callsSelfTrans = callsSelfTrans;
    }

    /** Whether this gender identity matches the given sex assigned at birth: a woman assigned female, a man male. */
    public boolean isCisgender(Sex sexAssignedAtBirth) {
        return genderIdentity == GenderIdentity.WOMAN && sexAssignedAtBirth == Sex.FEMALE
                || genderIdentity == GenderIdentity.MAN && sexAssignedAtBirth == Sex.MALE;
    }

    /** Whether this gender identity differs from the given sex assigned at birth, trans or not: the umbrella term. */
    public boolean isGenderDiverse(Sex sexAssignedAtBirth) {
        return !isCisgender(sexAssignedAtBirth);
    }

    /**
     * Whether this person is trans: a binary gender other than the sex assigned at birth, or a non-binary gender
     * unless they said they do not call themselves trans.
     */
    public boolean isTransgender(Sex sexAssignedAtBirth) {
        if (genderIdentity.isNonBinary()) {
            return callsSelfTrans == null || callsSelfTrans;
        }
        return !isCisgender(sexAssignedAtBirth);
    }

    /** Whether this person belongs to the LGBTQ+ community, by their orientation or their gender. */
    public boolean isLgbtq(Sex sexAssignedAtBirth) {
        return sexualOrientation.isLgbtq() || isGenderDiverse(sexAssignedAtBirth);
    }
}

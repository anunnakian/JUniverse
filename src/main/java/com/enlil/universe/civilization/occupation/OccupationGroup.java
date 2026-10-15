package com.enlil.universe.civilization.occupation;

/** The ten major groups of the International Standard Classification of Occupations (ISCO-08) of the ILO. */
public enum OccupationGroup {
    ARMED_FORCES(0),
    MANAGERS(1),
    PROFESSIONALS(2),
    TECHNICIANS_AND_ASSOCIATE_PROFESSIONALS(3),
    CLERICAL_SUPPORT_WORKERS(4),
    SERVICE_AND_SALES_WORKERS(5),
    SKILLED_AGRICULTURAL_WORKERS(6),
    CRAFT_AND_TRADES_WORKERS(7),
    PLANT_AND_MACHINE_OPERATORS(8),
    ELEMENTARY_OCCUPATIONS(9);

    private final int iscoCode;

    OccupationGroup(int iscoCode) {
        this.iscoCode = iscoCode;
    }

    /** The one-digit ISCO-08 code of this major group. */
    public int getIscoCode() {
        return iscoCode;
    }
}

package com.enlil.universe.civilization.science;

/** The scientific disciplines, each in its branch with what it studies. */
public enum Science {
    MATHEMATICS(ScienceBranch.FORMAL, "quantity, structure, space and change"),
    LOGIC(ScienceBranch.FORMAL, "valid reasoning"),
    COMPUTER_SCIENCE(ScienceBranch.FORMAL, "computation and information"),
    PHYSICS(ScienceBranch.NATURAL, "matter, energy, space and time"),
    CHEMISTRY(ScienceBranch.NATURAL, "substances, their composition and their reactions"),
    BIOLOGY(ScienceBranch.NATURAL, "living organisms"),
    ASTRONOMY(ScienceBranch.NATURAL, "celestial objects and the universe"),
    GEOLOGY(ScienceBranch.NATURAL, "the Earth, its rocks and its history"),
    PSYCHOLOGY(ScienceBranch.SOCIAL, "the mind and behaviour"),
    SOCIOLOGY(ScienceBranch.SOCIAL, "societies and social relations"),
    ECONOMICS(ScienceBranch.SOCIAL, "the production, distribution and consumption of goods"),
    LINGUISTICS(ScienceBranch.SOCIAL, "language"),
    MEDICINE(ScienceBranch.APPLIED, "health, disease and its treatment"),
    ENGINEERING(ScienceBranch.APPLIED, "the design and building of machines and structures");

    private final ScienceBranch branch;
    private final String subject;

    Science(ScienceBranch branch, String subject) {
        this.branch = branch;
        this.subject = subject;
    }

    public ScienceBranch getBranch() {
        return branch;
    }

    /** What this science studies. */
    public String getSubject() {
        return subject;
    }
}

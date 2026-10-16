package com.enlil.universe.matter.chemistry;

import com.enlil.universe.matter.ChemicalElement;
import com.enlil.universe.matter.StateOfMatter;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A covalent compound whose atoms and bonds are listed; its formula derives from its atoms. Ionic solids (salt,
 * limestone, rust) stay plain {@link Compound}s.
 */
@Getter
public class Molecule extends Compound {

    /** The usual number of bonds of elements with a single standard valence; other elements are not checked. */
    private static final Map<ChemicalElement, Integer> VALENCES = Map.of(
            ChemicalElement.HYDROGEN, 1, ChemicalElement.OXYGEN, 2, ChemicalElement.NITROGEN, 3,
            ChemicalElement.CARBON, 4, ChemicalElement.FLUORINE, 1, ChemicalElement.CHLORINE, 1,
            ChemicalElement.BROMINE, 1, ChemicalElement.IODINE, 1);

    /** Atom labels, in the order they were added, with their element. */
    private final Map<String, ChemicalElement> atoms;
    private final List<Bond> bonds;

    private Molecule(Compound compound, Map<String, ChemicalElement> atoms, List<Bond> bonds) {
        super(compound);
        this.atoms = Collections.unmodifiableMap(new LinkedHashMap<>(atoms));
        this.bonds = List.copyOf(bonds);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Energy needed to break every bond of one mole of the molecule in the gas phase, in kJ/mol, from average bond
     * enthalpies.
     *
     * @throws IllegalStateException if a bond has no tabulated average energy
     */
    public double atomizationEnergyKjPerMole() {
        return bonds.stream()
                .mapToDouble(bond -> bond.averageEnergyKjPerMole().orElseThrow(() ->
                        new IllegalStateException("no average energy for the bond " + bond)))
                .sum();
    }

    /** Builds a molecule atom by atom and bond by bond, then checks it is connected and its valences hold. */
    public static class Builder extends CompoundBuilder {

        private final Map<String, ChemicalElement> atoms = new LinkedHashMap<>();
        private final List<Bond> bonds = new ArrayList<>();

        Builder() {
        }

        /** Adds an atom, whose label starts with its element symbol and is unique in the molecule. */
        public Builder atom(String label) {
            if (atoms.putIfAbsent(label, Bond.elementOf(label)) != null) {
                throw new IllegalArgumentException("duplicate atom label " + label);
            }
            return this;
        }

        public Builder bond(String first, String second, BondOrder order) {
            bonds.add(new Bond(first, second, order));
            return this;
        }

        @Override
        public Builder name(String name) {
            super.name(name);
            return this;
        }

        /** Not allowed: a molecule's formula derives from its atoms. */
        @Override
        public Builder formula(Formula formula) {
            throw new UnsupportedOperationException("a molecule's formula derives from its atoms");
        }

        @Override
        public Builder standardState(StateOfMatter standardState) {
            super.standardState(standardState);
            return this;
        }

        @Override
        public Builder enthalpyOfFormationKjPerMole(Double enthalpyOfFormationKjPerMole) {
            super.enthalpyOfFormationKjPerMole(enthalpyOfFormationKjPerMole);
            return this;
        }

        @Override
        public Builder acidDissociationConstant(Double acidDissociationConstant) {
            super.acidDissociationConstant(acidDissociationConstant);
            return this;
        }

        @Override
        public Builder baseDissociationConstant(Double baseDissociationConstant) {
            super.baseDissociationConstant(baseDissociationConstant);
            return this;
        }

        @Override
        public Builder strongAcid(boolean strongAcid) {
            super.strongAcid(strongAcid);
            return this;
        }

        @Override
        public Builder strongBase(boolean strongBase) {
            super.strongBase(strongBase);
            return this;
        }

        @Override
        public Molecule build() {
            if (atoms.isEmpty()) {
                throw new IllegalArgumentException("a molecule needs at least one atom");
            }
            checkBonds();
            checkConnected();
            checkValences();
            Map<ChemicalElement, Integer> counts = new EnumMap<>(ChemicalElement.class);
            atoms.values().forEach(element -> counts.merge(element, 1, Integer::sum));
            super.formula(new Formula(counts, 0));
            return new Molecule(super.build(), atoms, bonds);
        }

        private void checkBonds() {
            Set<Set<String>> pairs = new HashSet<>();
            for (Bond bond : bonds) {
                if (!atoms.containsKey(bond.getFirstLabel()) || !atoms.containsKey(bond.getSecondLabel())) {
                    throw new IllegalArgumentException("bond " + bond + " links an unknown atom");
                }
                if (!pairs.add(Set.of(bond.getFirstLabel(), bond.getSecondLabel()))) {
                    throw new IllegalArgumentException("more than one bond between the atoms of " + bond);
                }
            }
        }

        private void checkConnected() {
            Set<String> reached = new HashSet<>();
            Deque<String> toVisit = new ArrayDeque<>(List.of(atoms.keySet().iterator().next()));
            while (!toVisit.isEmpty()) {
                String atom = toVisit.pop();
                if (reached.add(atom)) {
                    bonds.stream().filter(bond -> bond.involves(atom)).map(bond -> bond.otherEnd(atom))
                            .forEach(toVisit::push);
                }
            }
            if (reached.size() != atoms.size()) {
                throw new IllegalArgumentException("a molecule must be connected");
            }
        }

        private void checkValences() {
            atoms.forEach((label, element) -> {
                Integer valence = VALENCES.get(element);
                int bondOrders = bonds.stream().filter(bond -> bond.involves(label))
                        .mapToInt(bond -> bond.getOrder().getOrder()).sum();
                if (valence != null && bondOrders != valence) {
                    throw new IllegalArgumentException(label + " makes " + bondOrders + " bonds instead of "
                            + valence);
                }
            });
        }
    }
}

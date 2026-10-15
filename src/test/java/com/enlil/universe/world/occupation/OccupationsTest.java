package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.Force;
import com.enlil.universe.civilization.occupation.Occupation;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.occupation.Weapon;
import com.enlil.universe.civilization.science.Science;
import com.enlil.universe.world.AncientRome;
import com.enlil.universe.world.Humanity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class OccupationsTest {

    private static List<String> names(List<Occupation> occupations) {
        return occupations.stream().map(Occupation::getName).toList();
    }

    @Test
    @DisplayName("samurai should be born samurai, knights should be dubbed")
    void warriors() {
        Assertions.assertTrue(new Samurai().isHereditary());
        Assertions.assertFalse(new Knight().isHereditary());
        Assertions.assertEquals("Bushido", new Samurai().getCodeOfHonour());
        Assertions.assertEquals("Chivalry", new Knight().getCodeOfHonour());
        Assertions.assertEquals(Set.of(Weapon.KATANA, Weapon.BOW), new Samurai().getWeapons());
        Assertions.assertEquals(Force.MILITARY, new Knight().getForce());
    }

    @Test
    @DisplayName("knights and samurai should be history, doctors and teachers still at work")
    void extant_occupations() {
        Assertions.assertFalse(new Knight().isExtant());
        Assertions.assertFalse(new Samurai().existedIn(2026));
        Assertions.assertTrue(new Samurai().existedIn(1600));
        Assertions.assertTrue(new Doctor().isExtant());
        Assertions.assertTrue(new Teacher().isExtant());
    }

    @Test
    @DisplayName("a doctor should rely on medicine and biology, an engineer on physics and mathematics")
    void sciences() {
        Assertions.assertTrue(new Doctor().getSciences().containsAll(Set.of(Science.MEDICINE, Science.BIOLOGY)));
        Assertions.assertTrue(new Engineer().getSciences().containsAll(Set.of(Science.PHYSICS, Science.MATHEMATICS)));
        Assertions.assertTrue(new FbiSpecialAgent().getSciences().contains(Science.PSYCHOLOGY));
    }

    @Test
    @DisplayName("the armed should carry weapons, the firefighter and the doctor should not")
    void armed() {
        Assertions.assertTrue(new Soldier().carriesFirearms());
        Assertions.assertTrue(new PoliceOfficer().carriesFirearms());
        Assertions.assertFalse(new Legionary().carriesFirearms());
        Assertions.assertEquals(Force.INTELLIGENCE, new SecretAgent().getForce());
        Assertions.assertEquals(Force.LAW_ENFORCEMENT, new FbiSpecialAgent().getForce());
        Assertions.assertFalse(new Firefighter().isArmed());
        Assertions.assertFalse(new Doctor().isArmed());
    }

    @Test
    @DisplayName("each occupation should sit in its ISCO-08 major group")
    void isco_groups() {
        Assertions.assertEquals(OccupationGroup.PROFESSIONALS, new Doctor().getGroup());
        Assertions.assertEquals(OccupationGroup.ARMED_FORCES, new Soldier().getGroup());
        Assertions.assertEquals(OccupationGroup.SERVICE_AND_SALES_WORKERS, new PoliceOfficer().getGroup());
        Assertions.assertEquals(OccupationGroup.SERVICE_AND_SALES_WORKERS, new Firefighter().getGroup());
    }

    @Test
    @DisplayName("in 1600, a samurai and a knight could both still be met, but no FBI agent")
    void humanity_through_history() {
        Humanity humanity = new Humanity();

        List<String> in1600 = names(humanity.occupationsIn(1600));
        Assertions.assertTrue(in1600.containsAll(List.of("Samurai", "Knight", "Doctor", "Soldier")));
        Assertions.assertFalse(in1600.contains("FBI special agent"));
        Assertions.assertFalse(names(humanity.occupationsIn(2026)).contains("Samurai"));
        Assertions.assertEquals(8, humanity.occupationsIn(2026).size());
    }

    @Test
    @DisplayName("Rome under Trajan should have had legionaries and vigiles, but no police officers")
    void ancient_rome() {
        List<String> in117 = names(new AncientRome().occupationsIn(117));

        Assertions.assertEquals(List.of("Legionary", "Doctor", "Engineer", "Teacher", "Firefighter"), in117);
    }
}

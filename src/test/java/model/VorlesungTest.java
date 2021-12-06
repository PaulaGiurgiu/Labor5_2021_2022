package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VorlesungTest {
    private final Vorlesung vorlesung1 = new Vorlesung("BD", 1, 11, 30, 5);

    @Test
    void getName() {
        assertEquals("BD", vorlesung1.getName());
    }

    @Test
    void getLehrer() {
        assertEquals(1, vorlesung1.getLehrer());
    }

    @Test
    void getVorlesungID() {
        assertEquals(11, vorlesung1.getVorlesungID());
    }

    @Test
    void getMaxEnrollment() {
        assertEquals(30, vorlesung1.getMaxEnrollment());
    }

    @Test
    void getStudentsEnrolled() {
        assertEquals(0, vorlesung1.getStudentsEnrolled().size());
}

    @Test
    void getCredits() {
        assertEquals(5, vorlesung1.getCredits());
    }

    @Test
    void setName() {
        vorlesung1.setName("BD2");
        assertEquals("BD2", vorlesung1.getName());
    }

    @Test
    void setLehrer() {
        vorlesung1.setLehrer(2);
        assertEquals(2, vorlesung1.getLehrer());
    }

    @Test
    void setVorlesungID() {
        vorlesung1.setVorlesungID(12);
        assertEquals(12, vorlesung1.getVorlesungID());
    }

    @Test
    void setMaxEnrollment() {
        vorlesung1.setMaxEnrollment(60);
        assertEquals(60, vorlesung1.getMaxEnrollment());
    }

    @Test
    void setStudentsEnrolled() {
        List<Long> list = new ArrayList<>();
        list.add(12L);
        vorlesung1.setStudentsEnrolled(list);
        assertEquals(1, vorlesung1.getStudentsEnrolled().size());
    }

    @Test
    void setCredits() {
        vorlesung1.setCredits(6);
        assertEquals(6, vorlesung1.getCredits());
    }
}
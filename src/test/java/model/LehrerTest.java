package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LehrerTest {
    private final Person person1 = new Person(10L, "Alice", "Hart");
    private final Lehrer lehrer1 = new Lehrer(person1, 1L);

    @Test
    void getPersonID() {
        assertEquals(10L, lehrer1.getPersonID());
    }

    @Test
    void getVorname() {
        assertEquals("Alice", lehrer1.getVorname());
    }

    @Test
    void getNachname() {
        assertEquals("Hart", lehrer1.getNachname());
    }

    @Test
    void getLehrerID() {
        assertEquals(1L, lehrer1.getLehrerID());
    }

    @Test
    void getVorlesungen() {
        assertEquals(0, lehrer1.getVorlesungen().size());
    }

    @Test
    void setLehrerID() {
        lehrer1.setLehrerID(2L);
        assertEquals(2L, lehrer1.getLehrerID());
    }

    @Test
    void setVorlesungen() {
        lehrer1.setVorlesungen(Arrays.asList(100L, 200L, 300L));
        assertEquals(3, lehrer1.getVorlesungen().size());
    }
}
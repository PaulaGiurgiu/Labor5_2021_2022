package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {
    private final Person person1 = new Person(10L, "Alice", "Hart");

    @Test
    void getID() {
        assertEquals(10L, person1.getPersonID());
    }

    @Test
    void getVorname() {
        assertEquals("Alice", person1.getVorname());
    }

    @Test
    void getNachname() {
        assertEquals("Hart", person1.getNachname());
    }

    @Test
    void setID() {
        person1.setPersonID(100L);
        assertEquals(100L, person1.getPersonID());
    }

    @Test
    void setVorname() {
        person1.setVorname("Zoe");
        assertEquals("Zoe", person1.getVorname());
    }

    @Test
    void setNachname() {
        person1.setNachname("Miller");
        assertEquals("Miller", person1.getNachname());
    }
}
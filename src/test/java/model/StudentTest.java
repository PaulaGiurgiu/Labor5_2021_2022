package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private final Person person1 = new Person(10L, "Alice", "Hart");
    private final Student student1 = new Student(person1, 1L);

    @Test
    void getPersonID() {
        assertEquals(10L, student1.getPersonID());
    }

    @Test
    void getVorname() {
        assertEquals("Alice", student1.getVorname());
    }

    @Test
    void getNachname() {
        assertEquals("Hart", student1.getNachname());
    }

    @Test
    void getStudentID() {
        assertEquals(1L, student1.getStudentID());
    }

    @Test
    void getTotalCredits() {
        assertEquals(0, student1.getTotalCredits());
    }

    @Test
    void getEnrolledCourses() {
        assertEquals(0, student1.getEnrolledCourses().size());
    }

    @Test
    void setPersonID() {
        student1.setPersonID(11L);
        assertEquals(11L, student1.getPersonID());
    }

    @Test
    void setStudentID() {
        student1.setStudentID(2L);
        assertEquals(2L, student1.getStudentID());
    }

    @Test
    void setTotalCredits() {
        student1.setTotalCredits(18);
        assertEquals(18, student1.getTotalCredits());
    }

    @Test
    void setEnrolledCourses() {
        student1.setEnrolledCourses(Arrays.asList(100L, 200L, 300L));
        assertEquals(3, student1.getEnrolledCourses().size());
    }
}
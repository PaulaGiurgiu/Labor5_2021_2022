package repository;

import model.Person;
import model.Student;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryTest {
    private final Person person = new Person(-100L, "Olive", "Stone");
    private PersonRepository personRepository;

    private final Student student = new Student(person, -200L);
    private StudentRepository studentRepository;

    @Test
    void tests(){
        try {
            this.personRepository = new PersonRepository();
            this.personRepository.save(person);

            this.studentRepository = new StudentRepository();
            this.studentRepository.save(student);

            assertEquals(student.getStudentID(), this.studentRepository.findOne(-200L).getStudentID());
            assertEquals(student.getPersonID(), this.studentRepository.findOne(-200L).getPersonID());
            assertEquals(student.getTotalCredits(), this.studentRepository.findOne(-200L).getTotalCredits());
            assertEquals(student.getEnrolledCourses(), this.studentRepository.findOne(-200L).getEnrolledCourses());

            this.studentRepository.delete(student);
            this.personRepository.delete(person);
        } catch (SQLException e) {
            System.out.println("Test Student");
        }

    }

}
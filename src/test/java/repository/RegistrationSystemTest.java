package repository;

import exception.ExistException;
import exception.RegisterException;
import model.Lehrer;
import model.Person;
import model.Student;
import model.Vorlesung;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationSystemTest {
    private final Person personStudent = new Person(-100L, "Olive", "Stone");
    private final Person personLehrer = new Person(-111L, "Tyler", "Stone");
    private PersonRepository personRepository;

    private final Student student = new Student(personStudent, -200L);
    private StudentRepository studentRepository;

    private final Lehrer lehrer = new Lehrer(personLehrer, -200L);
    private LehrerRepository lehrerRepository;

    private final Vorlesung vorlesung = new Vorlesung("BD_0", lehrer.getLehrerID(), -300L, 30, 5);
    private VorlesungRepository vorlesungRepository;

    private EnrolledRepository enrolledRepository;
    private RegistrationSystem registrationSystem;

    @Test
    void tests() {
        try {
            this.personRepository = new PersonRepository();
            this.personRepository.save(personStudent);
            this.personRepository.save(personLehrer);

            this.studentRepository = new StudentRepository();
            this.studentRepository.save(student);

            this.lehrerRepository = new LehrerRepository();
            this.lehrerRepository.save(lehrer);

            this.vorlesungRepository = new VorlesungRepository();
            this.vorlesungRepository.save(vorlesung);

            this.enrolledRepository = new EnrolledRepository();
            this.registrationSystem = new RegistrationSystem();

            this.registrationSystem.register(vorlesung.getVorlesungID(), student.getStudentID());
            assertEquals(1, this.registrationSystem.getVorlesungRepository().enrolledStudents(vorlesung).size());
            assertEquals(1, this.studentRepository.enrolledCourses(student).size());
            assertEquals(vorlesung.getCredits(), this.registrationSystem.getStudentRepository().credits(student));
            assertEquals(1, this.registrationSystem.retrieveStudentsEnrolledForACourse(vorlesung.getVorlesungID()).size());
            this.registrationSystem.unregister(vorlesung.getVorlesungID(), student.getStudentID());
            assertEquals(0, this.registrationSystem.retrieveStudentsEnrolledForACourse(vorlesung.getVorlesungID()).size());

            this.vorlesungRepository.delete(vorlesung);
            this.lehrerRepository.delete(lehrer);
            this.studentRepository.delete(student);
            this.personRepository.delete(personLehrer);
            this.personRepository.delete(personStudent);
        } catch (SQLException | RegisterException | ExistException e) {
            System.out.println("Test Registration System");
        }
    }

}
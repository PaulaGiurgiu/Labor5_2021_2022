package repository;

import model.Lehrer;
import model.Person;
import model.Student;
import model.Vorlesung;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnrolledRepositoryTest {
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


    @Test
    void tests(){
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
            assertEquals(false, this.enrolledRepository.findOne(vorlesung.getVorlesungID(), student.getStudentID()));
            this.enrolledRepository.save(vorlesung.getVorlesungID(), student.getStudentID());
            assertEquals(true, this.enrolledRepository.findOne(vorlesung.getVorlesungID(), student.getStudentID()));
            this.enrolledRepository.delete(vorlesung.getVorlesungID(), student.getStudentID());
            assertEquals(false, this.enrolledRepository.findOne(vorlesung.getVorlesungID(), student.getStudentID()));



            this.vorlesungRepository.delete(vorlesung);
            this.lehrerRepository.delete(lehrer);
            this.studentRepository.delete(student);
            this.personRepository.delete(personLehrer);
            this.personRepository.delete(personStudent);
        } catch (SQLException e) {
            System.out.println("Test Enrolled");
        }
    }
}
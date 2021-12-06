package repository;

import model.Lehrer;
import model.Person;
import model.Vorlesung;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class VorlesungRepositoryTest {
    private final Person person = new Person(-100L, "Olive", "Stone");
    private PersonRepository personRepository;

    private final Lehrer lehrer = new Lehrer(person, -200L);
    private LehrerRepository lehrerRepository;

    private final Vorlesung vorlesung = new Vorlesung("BD_0", lehrer.getLehrerID(), -300L, 30, 5);
    private VorlesungRepository vorlesungRepository;

    @Test
    void tests(){
        try {
            this.personRepository = new PersonRepository();
            this.personRepository.save(person);

            this.lehrerRepository = new LehrerRepository();
            this.lehrerRepository.save(lehrer);

            this.vorlesungRepository = new VorlesungRepository();
            this.vorlesungRepository.save(vorlesung);

            assertEquals(vorlesung.getVorlesungID(), this.vorlesungRepository.findOne(-300L).getVorlesungID());
            assertEquals(vorlesung.getName(), this.vorlesungRepository.findOne(-300L).getName());
            assertEquals(vorlesung.getLehrer(), this.vorlesungRepository.findOne(-300L).getLehrer());
            assertEquals(vorlesung.getCredits(), this.vorlesungRepository.findOne(-300L).getCredits());
            assertEquals(vorlesung.getMaxEnrollment(), this.vorlesungRepository.findOne(-300L).getMaxEnrollment());

            this.vorlesungRepository.delete(vorlesung);
            this.lehrerRepository.delete(lehrer);
            this.personRepository.delete(person);
        } catch (SQLException e) {
            System.out.println("Test Vorlesung");
        }
    }

}
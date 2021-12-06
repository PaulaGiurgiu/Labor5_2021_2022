package repository;

import model.Lehrer;
import model.Person;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LehrerRepositoryTest {
    private final Person person = new Person(-100L, "Olive", "Stone");
    private PersonRepository personRepository;

    private final Lehrer lehrer = new Lehrer(person, -200L);
    private LehrerRepository lehrerRepository;

    @Test
    void tests(){
        try {
            this.personRepository = new PersonRepository();
            this.personRepository.save(person);

            this.lehrerRepository = new LehrerRepository();
            this.lehrerRepository.save(lehrer);

            assertEquals(lehrer.getLehrerID(), this.lehrerRepository.findOne(-200L).getLehrerID());
            assertEquals(lehrer.getPersonID(), this.lehrerRepository.findOne(-200L).getPersonID());
            assertEquals(lehrer.getVorlesungen(), this.lehrerRepository.findOne(-200L).getVorlesungen());

            this.lehrerRepository.delete(lehrer);
            this.personRepository.delete(person);
        } catch (SQLException e) {
            System.out.println("Test Lehrer");
        }
    }

}
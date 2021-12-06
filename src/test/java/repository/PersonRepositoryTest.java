package repository;

import model.Person;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {
    private final Person person = new Person(-100L, "Olive", "Stone");
    private PersonRepository personRepository;

    @Test
    void tests(){
        try {
            this.personRepository = new PersonRepository();
            this.personRepository.save(person);
            assertEquals(person.getPersonID(), this.personRepository.findOne(-100L).getPersonID());
            assertEquals(person.getVorname(),this.personRepository.findOne(-100L).getVorname());
            assertEquals(person.getNachname(), this.personRepository.findOne(-100L).getNachname());

            this.personRepository.delete(person);
        } catch (SQLException e) {
            System.out.println("Test Person");
        }
    }

}
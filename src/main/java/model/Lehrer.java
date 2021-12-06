package model;

import java.util.ArrayList;
import java.util.List;

public class Lehrer extends model.Person {
    private long lehrerID;
    private List<Long> vorlesungen;
    /**
     * //wir erstellen ein neues Objekt von Typ "Lehrer"
     * @param person ein Objekt von Typ "Person"
     * @param lehrerID eine "long" Zahl
     */
    public Lehrer(Person person, long lehrerID) {
        super(person.getPersonID(), person.getVorname(), person.getNachname());
        this.lehrerID = lehrerID;
        this.vorlesungen = new ArrayList<>();
    }

    public Lehrer(Person person, long lehrerID, List<Long> vorlesungen) {
        super(person.getPersonID(), person.getVorname(), person.getNachname());
        this.lehrerID = lehrerID;
        this.vorlesungen = vorlesungen;
    }

    @Override
    public String toString() {
        return "Lehrer{" +
                "lehrerID=" + lehrerID +
                ", vorlesungen=" + vorlesungen +
                "} " + super.toString();
    }

    /* Getters */
    @Override
    public Long getPersonID() {
        return super.getPersonID();
    }

    @Override
    public String getVorname() {
        return super.getVorname();
    }

    @Override
    public String getNachname() {
        return super.getNachname();
    }

    public long getLehrerID() { return lehrerID; }

    public List<Long> getVorlesungen() { return vorlesungen; }

    /* Setters */
    @Override
    public void setPersonID(Long personID) {
        super.setPersonID(personID);
    }

    @Override
    public void setVorname(String vorname) {
        super.setVorname(vorname);
    }

    @Override
    public void setNachname(String nachname) {
        super.setNachname(nachname);
    }

    public void setLehrerID(long lehrerID) { this.lehrerID = lehrerID; }

    public void setVorlesungen(List<Long> vorlesungen) { this.vorlesungen = vorlesungen; }
}

package model;

public class Person {
    private Long personID;
    private String vorname;
    private String nachname;

    public Person() {
    }

    /**
     * wir erstellen ein neues Objekt von Typ Person
     * @param personID eine "Long"-Zahl
     * @param vorname ein String
     * @param nachname ein String
     */

    public Person(Long personID, String vorname, String nachname) {
        this.personID = personID;
        this.vorname = vorname;
        this.nachname = nachname;
    }


    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                '}';
    }



    /* Getters */

    public Long getPersonID() {
        return personID;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    /* Setters */

    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }


}

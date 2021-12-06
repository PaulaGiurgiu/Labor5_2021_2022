package repository;

import connection.ConnectionMySQL;
import model.Lehrer;
import model.Person;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LehrerRepository implements CrudRepository<Lehrer>{
    static final String QUERY_SELECT = "SELECT * FROM LEHRER INNER JOIN PERSON ON LEHRER.personID = PERSON.personID";
    static final String QUERY_FIND = "SELECT * FROM LEHRER INNER JOIN PERSON ON LEHRER.personID = PERSON.personID WHERE lehrerID = ?";
    static final String QUERY_INSERT = "INSERT INTO LEHRER VALUES(?, ?)";
    static final String QUERY_DELETE = "DELETE FROM LEHRER WHERE lehrerID = ?";
    static final String QUERY_UPDATE = "UPDATE LEHRER SET personID = ? where lehrerID = ?";
    static final String QUERY_COURSES = "SELECT * FROM LEHRER " +
                                        "INNER JOIN VORLESUNG ON VORLESUNG.lehrerID = LEHRER.lehrerID " +
                                        "WHERE LEHRER.lehrerID = ?";
    private java.sql.Connection connection;
    private java.sql.Statement statement;
    private java.sql.ResultSet resultSet;

    public LehrerRepository() throws SQLException {
        super();
        this.connection = new ConnectionMySQL().getConnection();
        this.statement = connection.createStatement();
    }

    @Override
    public Lehrer findOne(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
        preparedStatement.setLong(1, id);
        this.resultSet = preparedStatement.executeQuery();

        Lehrer lehrer = null;
        while (resultSet.next()){
            lehrer = new Lehrer(new Person(resultSet.getLong("personID"), resultSet.getString("vorname"), resultSet.getString("nachname")), resultSet.getLong("lehrerID"));
        }

        return lehrer;
    }

    @Override
    public List<Lehrer> findAll() throws SQLException {
        List<Lehrer> lehrerList = new ArrayList<>();
        this.resultSet = statement.executeQuery(QUERY_SELECT);
        Lehrer lehrer;
        while (resultSet.next()){
            lehrer = new Lehrer(new Person(resultSet.getLong("personID"), resultSet.getString("vorname"), resultSet.getString("nachname")), resultSet.getLong("lehrerID"));
            lehrerList.add(lehrer);
        }

        return lehrerList;
    }

    @Override
    public Lehrer save(Lehrer entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
        preparedStatement.setLong(1, entity.getPersonID());
        preparedStatement.setLong(2, entity.getLehrerID());

        preparedStatement.executeUpdate();

        return entity;
    }

    @Override
    public void delete(Lehrer entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        preparedStatement.setLong(1, entity.getLehrerID());

        preparedStatement.executeUpdate();
    }

    @Override
    public Lehrer update(Lehrer entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
        preparedStatement.setLong(1, entity.getPersonID());
        preparedStatement.setLong(2, entity.getLehrerID());

        preparedStatement.executeUpdate();

        return entity;
    }

    public List<Long> enrolledCourses(Lehrer entity) throws SQLException{
        List<Long> list = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_COURSES);
        preparedStatement.setLong(1, entity.getLehrerID());
        this.resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            list.add(resultSet.getLong("vorlesungID"));
        }

        return list;
    }
}

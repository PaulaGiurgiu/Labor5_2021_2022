package repository;

import connection.ConnectionMySQL;
import model.Vorlesung;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VorlesungRepository implements CrudRepository<Vorlesung>{
    static final String QUERY_SELECT = "SELECT * FROM VORLESUNG";
    static final String QUERY_FIND = "SELECT * FROM VORLESUNG WHERE vorlesungID = ?";
    static final String QUERY_INSERT = "INSERT INTO VORLESUNG VALUES(?, ?, ?, ?, ?)";
    static final String QUERY_DELETE = "DELETE FROM VORLESUNG WHERE vorlesungID = ?";
    static final String QUERY_UPDATE = "UPDATE VORLESUNG SET name = ?, lehrerID = ?, maxEnrollment = ?, credits = ? where vorlesungID = ?";
    static final String QUERY_STUDENTS = "SELECT * FROM VORLESUNG " +
                                        "INNER JOIN ENROLLED ON VORLESUNG.vorlesungID = ENROLLED.vorlesungID " +
                                        "WHERE VORLESUNG.vorlesungID = ?";
    private java.sql.Connection connection;
    private java.sql.Statement statement;
    private ResultSet resultSet;

    public VorlesungRepository() throws SQLException {
        super();
        this.connection = new ConnectionMySQL().getConnection();
        this.statement = connection.createStatement();
    }

    @Override
    public Vorlesung findOne(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
        preparedStatement.setLong(1, id);
        this.resultSet = preparedStatement.executeQuery();

        Vorlesung vorlesung = null;
        while (resultSet.next()){
            vorlesung = new Vorlesung(resultSet.getString("name"), resultSet.getLong("lehrerID"), resultSet.getLong("vorlesungID"), resultSet.getInt("maxEnrollment"), resultSet.getInt("credits"));
        }


        return vorlesung;
    }

    @Override
    public List<Vorlesung> findAll() throws SQLException {
        List<Vorlesung> vorlesungList = new ArrayList<>();
        this.resultSet = statement.executeQuery(QUERY_SELECT);

        Vorlesung vorlesung = null;
        while (resultSet.next()){
            vorlesung = new Vorlesung(resultSet.getString("name"), resultSet.getLong("lehrerID"), resultSet.getLong("vorlesungID"), resultSet.getInt("maxEnrollment"), resultSet.getInt("credits"));
            vorlesungList.add(vorlesung);
        }

        return vorlesungList;
    }

    @Override
    public Vorlesung save(Vorlesung entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setLong(2, entity.getLehrer());
        preparedStatement.setLong(3, entity.getVorlesungID());
        preparedStatement.setInt(4, entity.getMaxEnrollment());
        preparedStatement.setInt(5, entity.getCredits());

        preparedStatement.executeUpdate();

        return entity;
    }

    @Override
    public void delete(Vorlesung entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        preparedStatement.setLong(1, entity.getVorlesungID());

        preparedStatement.executeUpdate();
    }

    @Override
    public Vorlesung update(Vorlesung entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setLong(2, entity.getLehrer());
        preparedStatement.setInt(3, entity.getMaxEnrollment());
        preparedStatement.setInt(4, entity.getCredits());
        preparedStatement.setLong(5, entity.getVorlesungID());

        preparedStatement.executeUpdate();

        return entity;
    }

    public List<Long> enrolledStudents(Vorlesung entity) throws SQLException{
        List<Long> list = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_STUDENTS);
        preparedStatement.setLong(1, entity.getVorlesungID());
        this.resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            list.add(resultSet.getLong("studentID"));
        }

        return list;
    }
}

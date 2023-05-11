package ptmk.service;

import ptmk.business.RandomPerson;
import ptmk.business.Util;
import ptmk.entity.Person;
import ptmk.dao.PersonDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonService extends Util implements PersonDAO {

    Connection connection = getConnection();

    @Override
    public void dropTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        String sqlDropTable = "DROP TABLE IF EXISTS People;";

        try {
            preparedStatement = connection.prepareStatement(sqlDropTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on drop table");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void createTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        String sqlCreateTable = "CREATE TABLE People(" +
                "id serial primary key," +
                "surname varchar(30)," +
                "first_name varchar(30)," +
                "second_name varchar(30)," +
                "date_of_birth date," +
                "gender bool" +
                ");";

        try {
            preparedStatement = connection.prepareStatement(sqlCreateTable);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on creating table");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void add(Person person) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO People(surname, first_name, second_name, date_of_birth, gender) VALUES(?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, person.getSurname());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getSecondName());
            preparedStatement.setDate(4, person.getDateOfBirth());
            preparedStatement.setBoolean(5, person.getGender());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on inserting new person");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Override
    public List<Person> getUniquePersons() throws SQLException{
        List<Person> personList = new ArrayList<>();

        String sqlGetAllUniquePersons = "SELECT DISTINCT(surname, first_name, second_name, date_of_birth) as person, gender FROM people;";

        Statement statement = null;

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sqlGetAllUniquePersons);

            while (resultSet.next()) {
                String[] result = resultSet.getString("person")
                        .replaceAll("[\\[\\](){}]","")
                        .split(",");
                Person person = new Person(result[0], result[1], result[2], Date.valueOf(result[3]), resultSet.getBoolean("gender"));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return personList;
    }

    @Override
    public void addAMillionRandomPersons() throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO People(surname, first_name, second_name, date_of_birth, gender) VALUES(?,?,?,?,?)";
        preparedStatement = connection.prepareStatement(sql);

        List<String> list = new ArrayList<>();
        for (int i = 0; i <= 1_000_000; i++) {
            Person person;
            boolean isFemale = true;
            if (i % 2 == 0) {
                isFemale = false;
                if (i % 10_000 == 0) {
                    person = RandomPerson.create(isFemale, true);
                } else {
                    person = RandomPerson.create(isFemale, false);
                }
            } else {
                person = RandomPerson.create(isFemale, false);
            }

            preparedStatement.setString(1, person.getSurname());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getSecondName());
            preparedStatement.setDate(4, person.getDateOfBirth());
            preparedStatement.setBoolean(5, person.getGender());

            preparedStatement.addBatch();

            if (i % 25 == 0) {
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
        }
    }

    @Override
    public List<Person> getAllMalesWhoseNameStartsWithF() throws SQLException{
        List<Person> personList = new ArrayList<>();

        String sqlGetAllMalesWhoseNameStartsWithF = "SELECT * FROM people WHERE surname LIKE 'F%' AND gender = false;";

        Statement statement = null;

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sqlGetAllMalesWhoseNameStartsWithF);

            while (resultSet.next()) {
                String surname = resultSet.getString("surname");
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                Date dateOfBirth = resultSet.getDate("date_of_birth");
                boolean gender = resultSet.getBoolean("gender");

                Person person = new Person(surname, firstName, secondName, dateOfBirth, gender);
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return personList;
    }

    @Override
    public void createIndexOnSurnameAndGender() throws SQLException {
        PreparedStatement statement = null;
        String sqlCreateIndex = "CREATE INDEX surnames_and_gender_index ON people(surname, gender);";

        try {
            statement = connection.prepareStatement(sqlCreateIndex);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
}

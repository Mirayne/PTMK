package ptmk.dao;

import ptmk.entity.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonDAO {
    void dropTable() throws SQLException;
    void createTable() throws SQLException;
    void add(Person person) throws SQLException;
    List<Person> getUniquePersons() throws SQLException;
    void addAMillionRandomPersons() throws SQLException;
    List<Person> getAllMalesWhoseNameStartsWithF() throws SQLException;
    void createIndexOnSurnameAndGender() throws SQLException;
}

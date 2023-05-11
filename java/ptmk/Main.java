package ptmk;

import ptmk.entity.Person;
import ptmk.service.PersonService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersonService personService = new PersonService();

        long start;
        long complete;

        List<Person> personList;

        try {
            switch (args[0]) {
                case "0": //Drop table
                    personService.dropTable();
                    break;
                case "1": //Create a table "People"
                    personService.createTable();
                    break;
                case "2": //Add custom row into table
                    Person person = Person.createPersonFromAString(args[1], args[2], args[3], args[4], args[5]);
                    personService.add(person);
                    break;
                case "3": //Print all people with unique patronymic and date of birth combination
                    personList = personService.getUniquePersons();
                    personList.forEach(System.out::println);
                    break;
                case "4": //Fill table with a million random generated rows
                    personService.addAMillionRandomPersons();
                    break;
                case "5": //Select all males whose names starts with "F"
                    start = System.currentTimeMillis();
                    personList = personService.getAllMalesWhoseNameStartsWithF();
                    complete = System.currentTimeMillis() - start;
                    personList.forEach(System.out::println);
                    System.out.println("Время выполнения: " + complete + " мсек.");
                    break;
                case "6": //Create an index and do case 5 to check the run time difference
                    start = System.currentTimeMillis();
                    personService.createIndexOnSurnameAndGender();
                    System.out.println("Время создания индекса: " + (System.currentTimeMillis() - start) + " мсек.");
                    start = System.currentTimeMillis();
                    personList = personService.getAllMalesWhoseNameStartsWithF();
                    complete = System.currentTimeMillis() - start;
                    personList.forEach(System.out::println);
                    System.out.println("Время выполнения: " + complete + " мсек.");
                    break;
                default: //To work with the application, add a launch argument - a digit from 1 to 6.
                    System.out.println("Для работы с приложением добавьте аргумент запуска - цифру от 1 до 6.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

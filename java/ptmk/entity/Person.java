package ptmk.entity;

import java.sql.Date;

public class Person {
    private long id;
    private String surname;
    private String firstName;
    private String secondName;
    private Date dateOfBirth;
    //false - male, true -female
    private boolean gender;

    public Person(String surname, String firstName, String secondName, Date dateOfBirth, boolean gender) {
        this.surname = surname;
        this.firstName = firstName;
        this.secondName = secondName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean getGender() {
        return gender;
    }

    @Override
    public String toString() {
        String gender = this.gender ? "female" : "male";
        return getSurname() + " " + getFirstName() + " " + getSecondName() + " " + getDateOfBirth() + " " + gender;
    }

    public static Person createPersonFromAString(String surname, String firstName, String secondName, String birthDate, String gender) {
        return new Person(surname, firstName, secondName, Date.valueOf(birthDate), getGenderFromString(gender));
    }

    private static boolean getGenderFromString(String str) {
        String[] male = {"male", "m", "м", "муж", "мужской", "мужчина"};
        String[] female = {"female", "f", "ж", "жен", "женский", "женщина"};

        for (int i = 0; i < male.length; i++) {
            if (str.equals(male[i])) return false;
        }

        for (int i = 0; i < female.length; i++) {
            if (str.equals(female[i])) return true;
        }

        throw new IllegalArgumentException("Неверно введен пол.");
    }
}

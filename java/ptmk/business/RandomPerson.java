package ptmk.business;

import ptmk.entity.Person;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPerson {

    private static Random random = new Random();
    private static List<String> surnames;
    private static List<String> namesM;
    private static List<String> namesF;
    private static List<String> secondNames;

    private static List<String> surnamesF;

    public static List<String> getFileRows(String fileName) {
        List<String> result = new ArrayList<>();

        InputStream is = new RandomPerson().getFileFromResourceAsStream(fileName);

        try (InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr);)
        {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Person create(boolean gender, boolean withF) {
        if (surnames == null) {
            surnames = getFileRows("surnames.txt");
            namesM = getFileRows("namesM.txt");
            namesF = getFileRows("namesF.txt");
            secondNames = getFileRows("lastNames.txt");
            surnamesF = getFileRows("surnamesF.txt");
        }
        String surname;

        if (withF && !gender) {
            surname = surnamesF.get(random.nextInt(surnamesF.size()));
        } else {
            surname = surnames.get(random.nextInt(surnames.size()));
        }
        String firstName;
        if (gender) {
            firstName = namesF.get(random.nextInt(namesF.size()));
        } else {
            firstName = namesM.get(random.nextInt(namesM.size()));
        }
        String secondName = secondNames.get(random.nextInt(secondNames.size()));
        Date birthDate = generateRandomDate(Date.valueOf("1950-01-01"), Date.valueOf("2004-12-31"));

        return new Person(surname, firstName, secondName, birthDate, gender);
    }

    private static Date generateRandomDate(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
}

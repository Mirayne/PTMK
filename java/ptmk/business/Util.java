package ptmk.business;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Util {
    private static String DB_DRIVER;// = "org.postgresql.Driver";
    private static String DB_USERNAME;// = "postgres";
    private static String DB_PASSWORD;// = "12345";
    private static String DB_URL;// = "jdbc:postgresql://localhost:5432/PTMK";

    public Connection getConnection() {
        getConnectionInfo();

        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

    private void getConnectionInfo() {
        List<String> info = getFileRows("connectionInfo.txt");
        DB_DRIVER = info.get(0).split("=")[1];
        DB_USERNAME = info.get(1).split("=")[1];
        DB_PASSWORD = info.get(2).split("=")[1];
        DB_URL = info.get(3).split("=")[1];
    }

    private static List<String> getFileRows(String fileName) {
        List<String> result = new ArrayList<>();

        String currentDir = System.getProperty("user.dir");
        try (BufferedReader reader = new BufferedReader(new FileReader(currentDir + "/" + fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }
}

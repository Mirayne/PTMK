package ptmk.business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String DB_DRIVER = "org.postgresql.Driver";
    private final static String DB_USERNAME = "postgres";
    private final static String DB_PASSWORD = "q1w2e3asd";
    private final static String DB_URL = "jdbc:postgresql://localhost:5432/PTMK";

    public Connection getConnection() {
        //getConnectionInfo();
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
}

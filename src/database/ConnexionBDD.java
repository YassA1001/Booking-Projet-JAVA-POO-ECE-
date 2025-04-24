package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/booking_db";
    private static final String USER = "root"; // ou ton user
    private static final String PASSWORD = ""; // mot de passe MySQL si t'en as mis un

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

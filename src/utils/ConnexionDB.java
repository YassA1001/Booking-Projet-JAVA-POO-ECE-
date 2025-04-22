package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/booking_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // ou ton mot de passe XAMPP

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Assure que le driver est bien chargé
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connexion à la base de données réussie !");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Driver JDBC non trouvé !");
                e.printStackTrace();
            }
        }
        return connection;
    }
}

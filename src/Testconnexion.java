import utils.ConnexionDB;

public class Testconnexion {
    public static void main(String[] args) {
        try {
            ConnexionDB.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

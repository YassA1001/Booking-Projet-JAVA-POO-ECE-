import model.Utilisateur;
import dao.UtilisateurDAO;

public class TestUtilisateur {
    public static void main(String[] args) {
        Utilisateur utilisateur = new Utilisateur("Yassine", "yass@test.com", "1234", "client");

        boolean success = UtilisateurDAO.ajouterUtilisateur(utilisateur);

        if (success) {
            System.out.println("Utilisateur ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout de l'utilisateur.");
        }
    }
}

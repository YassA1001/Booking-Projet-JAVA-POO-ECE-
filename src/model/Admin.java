package model;

public class Admin extends Utilisateur {

    public Admin(int id, String nom, String email, String motDePasse) {
        super(id, nom, email, motDePasse);
    }

    // Méthodes spécifiques à l'administrateur peuvent être ajoutées ici
}

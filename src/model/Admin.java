package model;

public class Admin extends Utilisateur {

    public Admin(int id, String nom, String email, String motDePasse, String type) {
        super(id, nom, email, motDePasse, type); // Ajout du champ 'type'
    }

    // Ajoute ici tes méthodes spécifiques à Admin si besoin
}

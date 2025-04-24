package model;

public class Client extends Utilisateur {
    private boolean estAncien;

    public Client(int id, String nom, String email, String motDePasse, String type, boolean estAncien) {
        super(id, nom, email, motDePasse, type); // Ajout du champ 'type'
        this.estAncien = estAncien;
    }

    public boolean isEstAncien() {
        return estAncien;
    }

    public void setEstAncien(boolean estAncien) {
        this.estAncien = estAncien;
    }
}

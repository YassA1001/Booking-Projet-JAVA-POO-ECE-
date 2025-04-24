package model;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private String type;

    public Utilisateur(String nom, String email, String motDePasse, String type) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.type = type;
    }

    public Utilisateur(int id, String nom, String email, String motDePasse, String type) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

// Hebergement.java
package model;

public class Hebergement {
    private int id;
    private String nom;
    private String type;
    private String description;
    private double prixParNuit;
    private String adresse;
    private int nbChambres;
    private int nbAdultes;
    private int nbEnfants;
    private String image;
    private int nbEtoiles;
    private String repas;


    public Hebergement(int id, String nom, String type, double prixParNuit, String adresse, int nbChambres, int nbAdultes, int nbEnfants, String image) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.prixParNuit = prixParNuit;
        this.adresse = adresse;
        this.nbChambres = nbChambres;
        this.nbAdultes = nbAdultes;
        this.nbEnfants = nbEnfants;
        this.image = image;
    }
    public Hebergement() {
    }

    public Hebergement(int id, String titre, String type, String descriptionGénérique, double prixParNuit, String adresse, int nbAdultes, int nbEnfants, int i, String imagePath) {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrixParNuit() { return prixParNuit; }
    public void setPrixParNuit(double prixParNuit) { this.prixParNuit = prixParNuit; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public int getNbChambres() { return nbChambres; }
    public void setNbChambres(int nbChambres) { this.nbChambres = nbChambres; }

    public int getNbAdultes() { return nbAdultes; }
    public void setNbAdultes(int nbAdultes) { this.nbAdultes = nbAdultes; }

    public int getNbEnfants() { return nbEnfants; }
    public void setNbEnfants(int nbEnfants) { this.nbEnfants = nbEnfants; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean estDisponible() {
        // À implémenter plus tard avec les réservations
        return true;
    }

    public String toString() {
        return nom + " - " + type + " - " + prixParNuit + "€/nuit";
    }
    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public String getRepas() {
        return repas;
    }

    public void setRepas(String repas) {
        this.repas = repas;
    }
}

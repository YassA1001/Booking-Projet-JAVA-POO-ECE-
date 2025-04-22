// Avis.java
package model;

public class Avis {
    private int id;
    private Client client;
    private Hebergement hebergement;
    private int note; // 1 à 5
    private String commentaire;

    public Avis(int id, Client client, Hebergement hebergement, int note, String commentaire) {
        this.id = id;
        this.client = client;
        this.hebergement = hebergement;
        this.note = note;
        this.commentaire = commentaire;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Hebergement getHebergement() { return hebergement; }
    public void setHebergement(Hebergement hebergement) { this.hebergement = hebergement; }

    public int getNote() { return note; }
    public void setNote(int note) {
        if (note >= 1 && note <= 5) {
            this.note = note;
        } else {
            throw new IllegalArgumentException("La note doit être comprise entre 1 et 5");
        }
    }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    @Override
    public String toString() {
        return "Avis de " + client.getNom() + " : " + note + "/5 - " + commentaire;
    }
} 
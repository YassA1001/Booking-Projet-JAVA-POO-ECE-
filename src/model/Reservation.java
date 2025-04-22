// Reservation.java
package model;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private Client client;
    private Hebergement hebergement;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private int nbAdultes;
    private int nbEnfants;

    public Reservation(int id, Client client, Hebergement hebergement, LocalDate dateArrivee,
                       LocalDate dateDepart, int nbAdultes, int nbEnfants) {
        this.id = id;
        this.client = client;
        this.hebergement = hebergement;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.nbAdultes = nbAdultes;
        this.nbEnfants = nbEnfants;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Hebergement getHebergement() { return hebergement; }
    public void setHebergement(Hebergement hebergement) { this.hebergement = hebergement; }

    public LocalDate getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(LocalDate dateArrivee) { this.dateArrivee = dateArrivee; }

    public LocalDate getDateDepart() { return dateDepart; }
    public void setDateDepart(LocalDate dateDepart) { this.dateDepart = dateDepart; }

    public int getNbAdultes() { return nbAdultes; }
    public void setNbAdultes(int nbAdultes) { this.nbAdultes = nbAdultes; }

    public int getNbEnfants() { return nbEnfants; }
    public void setNbEnfants(int nbEnfants) { this.nbEnfants = nbEnfants; }

    public long calculerNuits() {
        return java.time.temporal.ChronoUnit.DAYS.between(dateArrivee, dateDepart);
    }

    public double calculerTotal() {
        return calculerNuits() * hebergement.getPrixParNuit();
    }
}

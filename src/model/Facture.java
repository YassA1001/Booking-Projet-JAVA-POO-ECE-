// Facture.java
package model;

import java.time.LocalDate;

public class Facture {
    private int id;
    private Reservation reservation;
    private double montantTotal;
    private boolean reductionAppliquee;
    private LocalDate dateEmission;

    public Facture(int id, Reservation reservation, double montantTotal,
                   boolean reductionAppliquee, LocalDate dateEmission) {
        this.id = id;
        this.reservation = reservation;
        this.montantTotal = montantTotal;
        this.reductionAppliquee = reductionAppliquee;
        this.dateEmission = dateEmission;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }

    public boolean isReductionAppliquee() { return reductionAppliquee; }
    public void setReductionAppliquee(boolean reductionAppliquee) { this.reductionAppliquee = reductionAppliquee; }

    public LocalDate getDateEmission() { return dateEmission; }
    public void setDateEmission(LocalDate dateEmission) { this.dateEmission = dateEmission; }

    public String genererFactureTexte() {
        return "FACTURE #" + id + "\n" +
                "Client : " + reservation.getClient().getNom() + "\n" +
                "Hebergement : " + reservation.getHebergement().getNom() + "\n" +
                "Dates : " + reservation.getDateArrivee() + " -> " + reservation.getDateDepart() + "\n" +
                "Montant : " + montantTotal + " €" + (reductionAppliquee ? " (Réduction appliquée)" : "") + "\n" +
                "Émise le : " + dateEmission;
    }
}

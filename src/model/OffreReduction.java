// OffreReduction.java
package model;

import java.time.LocalDate;

public class OffreReduction {
    private int id;
    private String nom;
    private double pourcentage;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public OffreReduction(int id, String nom, double pourcentage, LocalDate dateDebut, LocalDate dateFin) {
        this.id = id;
        this.nom = nom;
        this.pourcentage = pourcentage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPourcentage() { return pourcentage; }
    public void setPourcentage(double pourcentage) { this.pourcentage = pourcentage; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public boolean estValide() {
        LocalDate today = LocalDate.now();
        return (today.isEqual(dateDebut) || today.isAfter(dateDebut)) && today.isBefore(dateFin);
    }
} 
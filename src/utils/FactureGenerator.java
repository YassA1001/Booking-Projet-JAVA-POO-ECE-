package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import model.Reservation;

import java.io.FileOutputStream;
import java.io.IOException;

public class FactureGenerator {

    public static void genererFacturePDF(Reservation reservation) {
        Document document = new Document();
        try {
            String nomFichier = "facture_" + reservation.getClient().getNom().replace(" ", "_") + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
            document.open();
            document.add(new Paragraph("Facture de réservation"));
            document.add(new Paragraph(" ")); // Saut de ligne
            document.add(new Paragraph("Nom du client : " + reservation.getClient().getNom()));
            document.add(new Paragraph("Hébergement : " + reservation.getHebergement().getNom()));
            document.add(new Paragraph("Adresse : " + reservation.getHebergement().getAdresse()));
            document.add(new Paragraph("Date d'arrivée : " + reservation.getDateArrivee()));
            document.add(new Paragraph("Date de départ : " + reservation.getDateDepart()));
            document.add(new Paragraph("Nombre d'adultes : " + reservation.getNbAdultes()));
            document.add(new Paragraph("Nombre d'enfants : " + reservation.getNbEnfants()));
            document.add(new Paragraph("Prix total : " + reservation.calculerTotal() + " €"));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}

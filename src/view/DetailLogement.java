package view;

import dao.HebergementDAO;
import dao.ReservationDAO;
import dao.ClientDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Hebergement;
import model.Client;
import model.Reservation;
import model.Utilisateur;
import utils.Session;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DetailLogement {

    public static void afficher(String titre, String adresse, double prix, String imagePath, String description, int hebergementId) {
        Stage detailStage = new Stage();
        detailStage.setTitle("Détail du Logement");

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label titreLabel = new Label(titre);
        titreLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ImageView imageView = new ImageView(new Image(new File(imagePath).toURI().toString()));
        imageView.setFitWidth(500);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);

        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-text-alignment: justify;");

        Label adresseLabel = new Label("Adresse : " + adresse);
        Label prixLabel = new Label("Prix par nuit : " + prix + " €");

        DatePicker dateArrivee = new DatePicker();
        dateArrivee.setPromptText("Date d'arrivée");
        DatePicker dateDepart = new DatePicker();
        dateDepart.setPromptText("Date de départ");

        Label nuitsLabel = new Label("Nombre de nuits : 0");
        Label prixTotalLabel = new Label("Prix total : 0 €");

        Spinner<Integer> nbAdultesSpinner = new Spinner<>(1, 10, 1);
        Spinner<Integer> nbEnfantsSpinner = new Spinner<>(0, 10, 0);
        nbAdultesSpinner.setEditable(true);
        nbEnfantsSpinner.setEditable(true);

        HBox spinnersBox = new HBox(10, new Label("Adultes:"), nbAdultesSpinner, new Label("Enfants:"), nbEnfantsSpinner);
        spinnersBox.setAlignment(Pos.CENTER);

        dateArrivee.valueProperty().addListener((obs, oldVal, newVal) -> recalculer(dateArrivee, dateDepart, prix, nuitsLabel, prixTotalLabel));
        dateDepart.valueProperty().addListener((obs, oldVal, newVal) -> recalculer(dateArrivee, dateDepart, prix, nuitsLabel, prixTotalLabel));

        Button reserverBtn = new Button("Réserver ce logement");
        reserverBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px;");

        reserverBtn.setOnAction(e -> {
            if (dateArrivee.getValue() != null && dateDepart.getValue() != null && !dateDepart.getValue().isBefore(dateArrivee.getValue())) {
                Utilisateur user = Session.getUtilisateur();
                ClientDAO clientDAO = new ClientDAO();
                Client client = clientDAO.findByEmail(user.getEmail());

                if (client == null) {
                    clientDAO.ensureClientExists(user.getId());
                    client = clientDAO.findByEmail(user.getEmail());
                }

                Reservation reservation = new Reservation();
                reservation.setClient(client);
                reservation.setHebergement(new Hebergement(hebergementId, "", "", 0, "", 0, 0, 0, ""));
                reservation.setDateArrivee(dateArrivee.getValue());
                reservation.setDateDepart(dateDepart.getValue());
                reservation.setNbAdultes(nbAdultesSpinner.getValue());
                reservation.setNbEnfants(nbEnfantsSpinner.getValue());

                ReservationDAO dao = new ReservationDAO();
                boolean success = dao.enregistrerReservation(reservation);

                Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
                alert.setHeaderText(success ? "Réservation réussie !" : "Erreur");
                alert.setContentText(success ? "Votre réservation a bien été enregistrée." : "Échec de la réservation.");
                alert.showAndWait();

                if (success) detailStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Dates invalides");
                alert.setContentText("Veuillez sélectionner des dates valides.");
                alert.showAndWait();
            }
        });

        root.getChildren().addAll(
                titreLabel,
                imageView,
                descriptionLabel,
                adresseLabel,
                prixLabel,
                dateArrivee,
                dateDepart,
                spinnersBox,
                nuitsLabel,
                prixTotalLabel,
                reserverBtn
        );

        Scene scene = new Scene(root, 600, 800);
        detailStage.setScene(scene);
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.show();
    }

    private static void recalculer(DatePicker arrivee, DatePicker depart, double prixParNuit, Label nuitsLabel, Label prixTotalLabel) {
        if (arrivee.getValue() != null && depart.getValue() != null && !depart.getValue().isBefore(arrivee.getValue())) {
            long nuits = ChronoUnit.DAYS.between(arrivee.getValue(), depart.getValue());
            double total = nuits * prixParNuit;
            nuitsLabel.setText("Nombre de nuits : " + nuits);
            prixTotalLabel.setText("Prix total : " + total + " €");
        } else {
            nuitsLabel.setText("Nombre de nuits : 0");
            prixTotalLabel.setText("Prix total : 0 €");
        }
    }
}

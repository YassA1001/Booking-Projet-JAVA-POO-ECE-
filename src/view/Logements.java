package view;

import dao.HebergementDAO;
import dao.ReservationDAO;
import dao.ClientDAO;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Hebergement;
import model.Client;
import model.Reservation;
import model.Utilisateur;
import utils.ConnexionDB;
import utils.Session;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Logements {

    public Logements(Stage stage) {
        VBox root = new VBox(0);

        VBox headerBox = new VBox();
        headerBox.setStyle("-fx-background-color: #005FAD;");
        headerBox.setPadding(new Insets(30));
        headerBox.setSpacing(20);

        Label bonjourLabel = new Label("Bonjour, Yassine !");
        bonjourLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        HBox barreRecherche = new HBox(10);
        barreRecherche.setAlignment(Pos.CENTER);
        TextField destinationField = new TextField();
        destinationField.setPromptText("Où allez-vous ?");
        DatePicker dateArrivee = new DatePicker();
        dateArrivee.setPromptText("Date d'arrivée");
        DatePicker dateDepart = new DatePicker();
        dateDepart.setPromptText("Date de départ");
        Spinner<Integer> adultesSpinner = new Spinner<>(1, 10, 1);
        Spinner<Integer> enfantsSpinner = new Spinner<>(0, 10, 0);
        Button rechercher = new Button("Rechercher");

        barreRecherche.getChildren().addAll(destinationField, dateArrivee, dateDepart, adultesSpinner, enfantsSpinner, rechercher);
        headerBox.getChildren().addAll(bonjourLabel, barreRecherche);

        Label introText = new Label("Les hébergements que les clients adorent");
        introText.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 20 0 10 20;");

        VBox introContainer = new VBox(introText);
        introContainer.setStyle("-fx-background-color: #005FAD; -fx-padding: 0 0 20 0;");

        FlowPane logementsPane = new FlowPane();
        logementsPane.setHgap(15);
        logementsPane.setVgap(15);
        logementsPane.setPrefWrapLength(1100);
        logementsPane.setPadding(new Insets(10, 20, 20, 20));

        VBox sectionLogements = new VBox(10);
        sectionLogements.getChildren().addAll(introContainer, logementsPane);

        root.getChildren().addAll(headerBox, sectionLogements);

        afficherTousLesLogements(logementsPane, stage);

        rechercher.setOnAction(e -> {
            logementsPane.getChildren().clear();
            String destination = destinationField.getText().trim().toLowerCase();
            int nbAdultes = adultesSpinner.getValue();
            int nbEnfants = enfantsSpinner.getValue();

            String url = "jdbc:mysql://localhost:3306/booking_db";
            String user = "root";
            String password = "";

            String sql = "SELECT id, nom, adresse, prix_par_nuit, image, description, nb_etoiles, repas FROM hebergement WHERE LOWER(adresse) LIKE ? AND nb_adultes >= ? AND nb_enfants >= ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, "%" + destination + "%");
                stmt.setInt(2, nbAdultes);
                stmt.setInt(3, nbEnfants);

                ResultSet rs = stmt.executeQuery();
                boolean hasResult = false;

                while (rs.next()) {
                    hasResult = true;
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String adresse = rs.getString("adresse");
                    double prix = rs.getDouble("prix_par_nuit");
                    String imagePath = rs.getString("image");
                    String description = rs.getString("description");
                    int nbEtoiles = rs.getInt("nb_etoiles");
                    String repas = rs.getString("repas");

                    VBox card = creerCarteLogement(stage, nom, adresse, prix, imagePath, description, id, nbEtoiles, repas);
                    logementsPane.getChildren().add(card);
                }

                if (!hasResult) {
                    Label noResults = new Label("Aucun hébergement ne correspond à votre recherche.");
                    noResults.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");
                    logementsPane.getChildren().add(noResults);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Tous les logements");
        stage.show();
    }

    private void afficherTousLesLogements(FlowPane logementsPane, Stage stage) {
        String url = "jdbc:mysql://localhost:3306/booking_db";
        String user = "root";
        String password = "";

        String sql = "SELECT id, nom, adresse, prix_par_nuit, image, description, nb_etoiles, repas FROM hebergement";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String adresse = rs.getString("adresse");
                double prix = rs.getDouble("prix_par_nuit");
                String imagePath = rs.getString("image");
                String description = rs.getString("description");
                int nbEtoiles = rs.getInt("nb_etoiles");
                String repas = rs.getString("repas");

                VBox card = creerCarteLogement(stage, nom, adresse, prix, imagePath, description, id, nbEtoiles, repas);
                logementsPane.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox creerCarteLogement(Stage parentStage, String titre, String adresse, double prix, String imagePath, String description, int hebergementId, int nbEtoiles, String repas) {
        Image image = new Image(new File(imagePath).toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(320);
        imageView.setFitHeight(200);

        Label titreLabel = new Label(titre);
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label adresseLabel = new Label(adresse);
        adresseLabel.setStyle("-fx-font-size: 14px;");
        Label prixLabel = new Label(prix + " € / nuit");
        prixLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");

        // Étoiles dorées
        HBox starsBox = new HBox(2);
        for (int i = 0; i < nbEtoiles; i++) {
            Label star = new Label("⭐");
            star.setStyle("-fx-font-size: 14px;");
            starsBox.getChildren().add(star);
        }

        // Etiquette repas
        Label repasLabel = new Label(repas);
        repasLabel.setStyle("""
            -fx-background-color: #FFD700;
            -fx-text-fill: black;
            -fx-font-size: 12px;
            -fx-padding: 2 6 2 6;
            -fx-background-radius: 5px;
        """);

        VBox box = new VBox(imageView, titreLabel, starsBox, adresseLabel, repasLabel, prixLabel);
        box.setSpacing(5);
        box.setPadding(new Insets(5));
        box.setStyle("-fx-border-color: #dddddd; -fx-border-width: 1px; -fx-border-radius: 5px;");

        FadeTransition ft = new FadeTransition(Duration.millis(500), box);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        box.setOnMouseClicked(e -> {
            DetailLogement.afficher(titre, adresse, prix, imagePath, description, hebergementId);
        });

        return box;
    }
}

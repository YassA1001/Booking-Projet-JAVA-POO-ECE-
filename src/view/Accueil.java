package view;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Utilisateur;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Accueil {

    private Utilisateur utilisateur;
    private VBox root;
    private FlowPane hebergementsPane;

    public Accueil(Stage primaryStage, Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.root = new VBox();
        root.setSpacing(20);
        root.setStyle("-fx-background-color: #ffffff;");

        afficherBarreNavigation();
        afficherHeaderEtRecherche();
        afficherDestinationsEnVogue();
        afficherNosHebergements();

        if (utilisateur.getType().equals("admin")) {
            afficherBoutonAjoutHébergement(primaryStage);
        }

        afficherPopUpOffre(primaryStage);

        Scene scene = new Scene(root, 1200, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Accueil - Bienvenue");
        primaryStage.show();
    }

    private void afficherBarreNavigation() {
        HBox navBar = new HBox(20);
        navBar.setPadding(new Insets(10));
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-background-color: #eeeeee; -fx-border-color: #cccccc;");

        Button accueilBtn = new Button("Accueil");
        Button logementsBtn = new Button("Logements");
        Button reservationsBtn = new Button("Mes réservations");
        Button profilBtn = new Button("Profil");

        logementsBtn.setOnAction(e -> new Logements(new Stage()));

        reservationsBtn.setOnAction(e -> {
            ClientDashboard dashboard = new ClientDashboard(utilisateur.getNom());
            dashboard.setVisible(true);
        });

        profilBtn.setOnAction(e -> new ProfilForm().setVisible(true));



        navBar.getChildren().addAll(accueilBtn, logementsBtn, reservationsBtn, profilBtn);
        root.getChildren().add(navBar);
    }

    private void afficherHeaderEtRecherche() {
        VBox headerBox = new VBox();
        headerBox.setStyle("-fx-background-color: #005FAD;");
        headerBox.setPadding(new Insets(30));
        headerBox.setSpacing(20);

        Label bonjourLabel = new Label("Bonjour, " + utilisateur.getNom() + " !");
        bonjourLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        Label presentation = new Label("Explorez les meilleures destinations et offres pour vos prochaines vacances !");
        presentation.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        HBox barreRecherche = new HBox(10);
        barreRecherche.setAlignment(Pos.CENTER);
        TextField destinationField = new TextField();
        destinationField.setPromptText("Où allez-vous ?");
        DatePicker dateArrivee = new DatePicker();
        dateArrivee.setPromptText("Date d'arrivée");
        DatePicker dateDepart = new DatePicker();
        dateDepart.setPromptText("Date de départ");
        Spinner<Integer> personnesSpinner = new Spinner<>(1, 10, 1);
        Button rechercher = new Button("Rechercher");

        barreRecherche.getChildren().addAll(destinationField, dateArrivee, dateDepart, personnesSpinner, rechercher);
        headerBox.getChildren().addAll(bonjourLabel, presentation, barreRecherche);
        root.getChildren().add(headerBox);
    }

    private void afficherDestinationsEnVogue() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10, 20, 10, 20));

        Label titre = new Label("Destinations en vogue");
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER);
        topRow.getChildren().addAll(
                creerDestinationCard("Paris", "paris.jpg", true),
                creerDestinationCard("Londres", "londres.jpg", true)
        );

        HBox bottomRow = new HBox(10);
        bottomRow.setAlignment(Pos.CENTER);
        bottomRow.getChildren().addAll(
                creerDestinationCard("Lisbonne", "lisbonne.jpg", false),
                creerDestinationCard("Japon", "japon.jpg", false),
                creerDestinationCard("Marrakech", "marrakech.jpg", false)
        );

        section.getChildren().addAll(titre, topRow, bottomRow);
        root.getChildren().add(section);
    }

    private StackPane creerDestinationCard(String nom, String imageName, boolean grande) {
        Image image = new Image(getClass().getResource("/images/" + imageName).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(grande ? 300 : 180);
        imageView.setFitHeight(grande ? 180 : 120);
        imageView.setPreserveRatio(false);

        Label label = new Label(nom);
        label.setStyle("""
            -fx-background-color: rgba(0, 0, 0, 0.5);
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-padding: 5 10 5 10;
        """);

        StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
        StackPane card = new StackPane(imageView, label);
        card.setStyle("-fx-border-radius: 8px; -fx-background-radius: 8px; -fx-overflow: hidden;");
        return card;
    }

    private void afficherNosHebergements() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10, 20, 10, 20));

        Label titre = new Label("Nos hébergements");
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        hebergementsPane = new FlowPane();
        hebergementsPane.setHgap(15);
        hebergementsPane.setVgap(15);
        hebergementsPane.setPrefWrapLength(1100);

        // Connexion BDD
        String url = "jdbc:mysql://localhost:3306/booking_db";
        String user = "root";
        String password = "";

        String sql = "SELECT nom, adresse, prix_par_nuit, image FROM hebergement";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nom = rs.getString("nom");
                String adresse = rs.getString("adresse");
                double prix = rs.getDouble("prix_par_nuit");
                String imagePath = rs.getString("image");

                VBox card = creerCarteHebergement(nom, imagePath, adresse, prix);
                hebergementsPane.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        section.getChildren().addAll(titre, hebergementsPane);
        root.getChildren().add(section);
    }


    private void afficherBoutonAjoutHébergement(Stage stage) {
        Button ajouterBtn = new Button("+");
        ajouterBtn.setStyle("""
            -fx-background-color: #6200EE;
            -fx-text-fill: white;
            -fx-font-size: 24px;
            -fx-background-radius: 25px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 4);
        """);
        ajouterBtn.setPrefSize(50, 50);
        ajouterBtn.setOnAction(e -> {
            Stage popup = new Stage();
            popup.initOwner(stage);
            popup.setTitle("Ajouter un logement");

            VBox content = new VBox(10);
            content.setPadding(new Insets(20));
            content.setAlignment(Pos.CENTER);

            TextField nomField = new TextField();
            nomField.setPromptText("Nom");

            TextField typeField = new TextField();
            typeField.setPromptText("Type (ex: Appartement, Villa...)");

            TextArea descriptionField = new TextArea();
            descriptionField.setPromptText("Description");
            descriptionField.setPrefRowCount(3);

            TextField prixField = new TextField();
            prixField.setPromptText("Prix par nuit (€)");

            TextField adresseField = new TextField();
            adresseField.setPromptText("Adresse");

            Spinner<Integer> chambresSpinner = new Spinner<>(1, 10, 1);
            Spinner<Integer> adultesSpinner = new Spinner<>(1, 10, 1);
            Spinner<Integer> enfantsSpinner = new Spinner<>(0, 10, 0);

            Button choisirImageBtn = new Button("Choisir une image");
            ImageView imageView = new ImageView();
            imageView.setFitWidth(200);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);

            final String[] cheminImage = {null};

            choisirImageBtn.setOnAction(ev -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choisir une image");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
                );
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    imageView.setImage(new Image(selectedFile.toURI().toString()));
                    cheminImage[0] = selectedFile.getAbsolutePath();
                }
            });

            Button ajouterBtnForm = new Button("Ajouter");
            ajouterBtnForm.setOnAction(event -> {
                String nom = nomField.getText();
                String type = typeField.getText();
                String description = descriptionField.getText();
                double prix = Double.parseDouble(prixField.getText());
                String adresse = adresseField.getText();
                int nbChambres = chambresSpinner.getValue();
                int nbAdultes = adultesSpinner.getValue();
                int nbEnfants = enfantsSpinner.getValue();

                VBox newCard = creerCarteHebergement(nom, cheminImage[0], adresse, prix);
                hebergementsPane.getChildren().add(newCard);

                ajouterHebergementEnBase(nom, type, description, prix, adresse, nbChambres, nbAdultes, nbEnfants, cheminImage[0]);
                popup.close();
            });

            content.getChildren().addAll(nomField, typeField, descriptionField,
                    prixField, adresseField,
                    new Label("Chambres"), chambresSpinner,
                    new Label("Adultes"), adultesSpinner,
                    new Label("Enfants"), enfantsSpinner,
                    choisirImageBtn, imageView, ajouterBtnForm);

            Scene scene = new Scene(content, 450, 650);
            popup.setScene(scene);
            popup.show();
        });

        StackPane boutonContainer = new StackPane(ajouterBtn);
        boutonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        boutonContainer.setPadding(new Insets(0, 30, 40, 0));
        root.getChildren().add(boutonContainer);
    }

    private VBox creerCarteHebergement(String titre, String imagePath, String adresse, double prix) {
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

        VBox box = new VBox(imageView, titreLabel, adresseLabel, prixLabel);
        box.setSpacing(5);
        box.setPadding(new Insets(5));
        box.setStyle("-fx-border-color: #dddddd; -fx-border-width: 1px; -fx-border-radius: 5px;");

        return box;
    }

    private void ajouterHebergementEnBase(String nom, String type, String description, double prix, String adresse,
                                          int nbChambres, int nbAdultes, int nbEnfants, String cheminImage) {
        String url = "jdbc:mysql://localhost:3306/booking_db";
        String user = "root";
        String password = "";

        String sql = """
            INSERT INTO hebergement 
            (nom, type, description, prix_par_nuit, adresse, nb_chambres, nb_adultes, nb_enfants, image) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            stmt.setString(2, type);
            stmt.setString(3, description);
            stmt.setDouble(4, prix);
            stmt.setString(5, adresse);
            stmt.setInt(6, nbChambres);
            stmt.setInt(7, nbAdultes);
            stmt.setInt(8, nbEnfants);
            stmt.setString(9, cheminImage);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void afficherPopUpOffre(Stage stage) {
        PauseTransition delay = new PauseTransition(Duration.seconds(20));
        delay.setOnFinished(event -> {
            Stage popup = new Stage();
            popup.initOwner(stage);
            popup.initModality(Modality.WINDOW_MODAL);
            popup.setTitle("Offre Spéciale");

            VBox contenu = new VBox();
            contenu.setPadding(new Insets(20));
            contenu.setSpacing(15);
            contenu.setAlignment(Pos.TOP_CENTER);

            Button fermer = new Button("X");
            fermer.setOnAction(e -> popup.close());
            fermer.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-text-fill: red;");

            HBox topBar = new HBox();
            topBar.setAlignment(Pos.TOP_RIGHT);
            topBar.getChildren().add(fermer);

            Label titre = new Label("Offre spéciale !");
            titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            Label texte = new Label("Profitez de 30% de réduction sur votre prochaine réservation !");
            ImageView image = new ImageView(new Image(getClass().getResource("/images/promo.jpg").toExternalForm()));
            image.setFitWidth(300);
            image.setFitHeight(150);

            contenu.getChildren().addAll(topBar, titre, texte, image);

            Scene scene = new Scene(contenu, 400, 300);
            popup.setScene(scene);
            popup.show();
        });
        delay.play();
    }
}

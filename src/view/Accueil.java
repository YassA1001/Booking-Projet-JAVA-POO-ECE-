package view;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Accueil extends Application {
    private boolean utilisateurConnecte = false;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // NAVIGATION BAR
        HBox navBar = new HBox(30);
        navBar.setPadding(new Insets(15, 30, 15, 30));
        navBar.setAlignment(Pos.CENTER_RIGHT);
        navBar.setStyle("-fx-background-color: white;");

        ImageView logo = new ImageView(new Image("file:resources/images/logo.png"));
        logo.setFitHeight(40);
        logo.setPreserveRatio(true);
        HBox logoContainer = new HBox(logo);
        logoContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(logoContainer, Priority.ALWAYS);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        for (String name : new String[]{"Accueil", "Hébergements", "Panier", "Profil", "Contact"}) {
            Button btn = new Button(name);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #003580; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: transparent; -fx-border-radius: 15; -fx-padding: 6 12;");
            navBar.getChildren().add(btn);
        }
        navBar.getChildren().add(0, logoContainer);

        // Ajoute la navigation bar au root (par exemple)
        root.getChildren().add(navBar);

        Scene scene = new Scene(root, 1280, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Accueil");
        primaryStage.show();

        // Afficher le pop-up une fois la fenêtre prête
        afficherPopupConnexion(primaryStage);
    }

    private void afficherPopupConnexion(Stage owner) {
        Dialog<String> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Connexion requise");

        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField();
        Label mdpLabel = new Label("Mot de passe :");
        PasswordField mdpField = new PasswordField();

        VBox content = new VBox(10, emailLabel, emailField, mdpLabel, mdpField);
        content.setPadding(new Insets(20));

        dialog.getDialogPane().setContent(content);
        ButtonType loginButtonType = new ButtonType("Se connecter", ButtonBar.ButtonData.OK_DONE);
        ButtonType signupButtonType = new ButtonType("S'inscrire", ButtonBar.ButtonData.OTHER);

        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, signupButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType || dialogButton == signupButtonType) {
                utilisateurConnecte = true;
                // TODO: Ajouter logique réelle de connexion/inscription
                return "ok";
            }
            return null;
        });

        dialog.showAndWait();
    }

    private VBox createCard(String ville, String pays, String imgPath, int span) {
        VBox card = new VBox();
        card.setStyle("-fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 6, 0.5, 0, 2);");

        StackPane pane = new StackPane();
        ImageView image = new ImageView(new Image(imgPath));
        image.setFitWidth(span == 2 ? 480 : 240);
        image.setFitHeight(span == 2 ? 180 : 120);
        image.setPreserveRatio(false);
        image.setStyle("-fx-background-radius: 12;");

        VBox overlay = new VBox();
        overlay.setAlignment(Pos.BOTTOM_LEFT);
        overlay.setPadding(new Insets(10));

        Text villeText = new Text(ville);
        villeText.setFill(Color.WHITE);
        villeText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Text paysText = new Text(pays);
        paysText.setFill(Color.WHITE);
        paysText.setFont(Font.font("Arial", 14));

        overlay.getChildren().addAll(villeText, paysText);
        pane.getChildren().addAll(image, overlay);

        card.getChildren().add(pane);
        return card;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

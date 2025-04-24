package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Utilisateur;
import dao.UtilisateurDAO;

public class SignupView {
    public static void afficher(Stage owner) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Inscription");

        Label nomLabel = new Label("Nom :");
        TextField nomField = new TextField();

        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField();

        Label mdpLabel = new Label("Mot de passe :");
        PasswordField mdpField = new PasswordField();

        Label confirmLabel = new Label("Confirmer le mot de passe :");
        PasswordField confirmField = new PasswordField();

        Label typeLabel = new Label("Type :");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("client", "admin");
        typeBox.setValue("client");

        Button validerBtn = new Button("Valider");
        validerBtn.setOnAction(e -> {
            String nom = nomField.getText();
            String email = emailField.getText();
            String mdp = mdpField.getText();
            String confirm = confirmField.getText();
            String type = typeBox.getValue();

            if (!mdp.equals(confirm)) {
                showAlert("Erreur", "Les mots de passe ne correspondent pas.");
                return;
            }

            Utilisateur utilisateur = new Utilisateur(nom, email, mdp, type);
            boolean success = UtilisateurDAO.ajouterUtilisateur(utilisateur);
            if (success) {
                showAlert("Succès", "Utilisateur inscrit avec succès.");
                dialog.close();
            } else {
                showAlert("Erreur", "Échec de l'inscription.");
            }
        });

        VBox layout = new VBox(10, nomLabel, nomField, emailLabel, emailField, mdpLabel, mdpField, confirmLabel, confirmField, typeLabel, typeBox, validerBtn);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350, 450);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

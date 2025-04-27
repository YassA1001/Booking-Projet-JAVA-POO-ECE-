package view;

import database.ConnexionBDD;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Client;
import model.Utilisateur;
import utils.Session;
import utils.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginView {

    public static boolean afficher() {
        final boolean[] authenticated = {false};

        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setTitle("Connexion");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField();
        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);

        Label mdpLabel = new Label("Mot de passe :");
        PasswordField mdpField = new PasswordField();
        grid.add(mdpLabel, 0, 1);
        grid.add(mdpField, 1, 1);

        Button loginBtn = new Button("Connexion");
        Button signupBtn = new Button("Inscription");
        grid.add(loginBtn, 1, 2);
        grid.add(signupBtn, 1, 3);

        Label messageLabel = new Label();
        grid.add(messageLabel, 1, 4);

        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String mdp = mdpField.getText();

            try (Connection conn = ConnexionBDD.getConnection()) {
                String sql = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, mdp);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Utilisateur u = new Utilisateur(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"),
                            rs.getString("type")
                    );
                    Session.setUtilisateur(u);

                    // AJOUT IMPORTANT POUR MES RESERVATIONS
                    SessionManager.setClient(new Client(
                            u.getId(),
                            u.getNom(),
                            u.getEmail(),
                            u.getMotDePasse(),
                            u.getType(),
                            false // pas ancien par défaut
                    ));

                    authenticated[0] = true;
                    loginStage.close();
                } else {
                    messageLabel.setText("Email ou mot de passe incorrect");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                messageLabel.setText("Erreur de connexion");
            }
        });

        signupBtn.setOnAction(e -> SignupView.afficher());

        Scene scene = new Scene(grid, 400, 300);
        loginStage.setScene(scene);
        loginStage.showAndWait();

        return authenticated[0];
    }
}

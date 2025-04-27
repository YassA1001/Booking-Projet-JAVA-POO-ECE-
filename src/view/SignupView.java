package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignupView {

    public static void afficher() {
        Stage signupStage = new Stage();
        signupStage.initModality(Modality.APPLICATION_MODAL);
        signupStage.setTitle("Inscription");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        TextField nomField = new TextField();
        TextField emailField = new TextField();
        PasswordField motDePasseField = new PasswordField();
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("client", "admin");
        typeComboBox.setValue("client");

        Button inscrireBtn = new Button("S'inscrire");

        grid.add(new Label("Nom :"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Email :"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Mot de passe :"), 0, 2);
        grid.add(motDePasseField, 1, 2);
        grid.add(new Label("Type :"), 0, 3);
        grid.add(typeComboBox, 1, 3);
        grid.add(inscrireBtn, 1, 4);

        inscrireBtn.setOnAction(e -> {
            String nom = nomField.getText();
            String email = emailField.getText();
            String motDePasse = motDePasseField.getText();
            String type = typeComboBox.getValue();

            if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || type.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs !");
                alert.showAndWait();
                return;
            }

            try (Connection conn = ConnexionDB.getConnection()) {
                String sql = "INSERT INTO utilisateur (nom, email, mot_de_passe, type) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, nom);
                stmt.setString(2, email);
                stmt.setString(3, motDePasse);
                stmt.setString(4, type);

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int idUtilisateur = rs.getInt(1);

                        if (type.equals("client")) {
                            String sqlClient = "INSERT INTO client (id_client, est_ancien) VALUES (?, ?)";
                            PreparedStatement stmtClient = conn.prepareStatement(sqlClient);
                            stmtClient.setInt(1, idUtilisateur);
                            stmtClient.setBoolean(2, false);
                            stmtClient.executeUpdate();
                        }
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Inscription réussie !");
                    alert.showAndWait();
                    signupStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur d'inscription.");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur base de données : " + ex.getMessage());
                alert.showAndWait();
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        signupStage.setScene(scene);
        signupStage.showAndWait();
    }
}

package view;

import dao.HebergementDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Hebergement;

public class AjoutHebergementView {

    public static void afficher(Stage ownerStage) {
        Stage stage = new Stage();
        stage.setTitle("Ajouter un hébergement");

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);

        TextField nomField = new TextField();
        TextField typeField = new TextField();
        TextField descriptionField = new TextField();
        TextField prixField = new TextField();
        TextField adresseField = new TextField();
        TextField chambresField = new TextField();
        TextField adultesField = new TextField();
        TextField enfantsField = new TextField();
        TextField imageField = new TextField();

        form.addRow(0, new Label("Nom :"), nomField);
        form.addRow(1, new Label("Type :"), typeField);
        form.addRow(2, new Label("Description :"), descriptionField);
        form.addRow(3, new Label("Prix par nuit :"), prixField);
        form.addRow(4, new Label("Adresse :"), adresseField);
        form.addRow(5, new Label("Nombre de chambres :"), chambresField);
        form.addRow(6, new Label("Nombre d'adultes :"), adultesField);
        form.addRow(7, new Label("Nombre d'enfants :"), enfantsField);
        form.addRow(8, new Label("Chemin image :"), imageField);

        Button ajouterBtn = new Button("Ajouter");
        ajouterBtn.setOnAction(e -> {
            try {
                String nom = nomField.getText();
                String type = typeField.getText();
                String description = descriptionField.getText();
                double prix = Double.parseDouble(prixField.getText());
                String adresse = adresseField.getText();
                int nbChambres = Integer.parseInt(chambresField.getText());
                int nbAdultes = Integer.parseInt(adultesField.getText());
                int nbEnfants = Integer.parseInt(enfantsField.getText());
                String image = imageField.getText();

                Hebergement hebergement = new Hebergement(0, nom, type, description, prix, adresse, nbChambres, nbAdultes, nbEnfants, image);
                boolean success = new HebergementDAO().ajouter(hebergement);

                Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
                alert.setHeaderText(success ? "Succès" : "Erreur");
                alert.setContentText(success ? "Hébergement ajouté avec succès." : "Échec de l'ajout de l'hébergement.");
                alert.showAndWait();

                if (success) stage.close();

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Vérifiez que tous les champs numériques sont bien remplis.");
                alert.showAndWait();
            }
        });

        root.getChildren().addAll(form, ajouterBtn);

        Scene scene = new Scene(root, 450, 500);
        stage.setScene(scene);
        stage.initOwner(ownerStage);
        stage.show();
    }
}

package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HebergementsView extends Application {
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20;");

        // Exemple de logements à afficher
        Label logement1 = new Label("🏠 Appartement cosy à Paris - 70€/nuit");
        Label logement2 = new Label("🏡 Villa à Marrakech - 120€/nuit");
        Label logement3 = new Label("🏢 Studio à Londres - 90€/nuit");

        root.getChildren().addAll(logement1, logement2, logement3);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Liste des hébergements");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

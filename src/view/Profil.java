package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Profil extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Bienvenue dans l'espace client !");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Profil");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

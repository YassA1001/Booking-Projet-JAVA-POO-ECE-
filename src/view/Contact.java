package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Contact extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label titre = new Label("Nous contacter");
        TextField nomField = new TextField();
        nomField.setPromptText("Votre nom");
        TextField emailField = new TextField();
        emailField.setPromptText("Votre e-mail");
        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Votre message");
        Button envoyerBtn = new Button("Envoyer");

        VBox root = new VBox(10, titre, nomField, emailField, messageArea, envoyerBtn);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Contact");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
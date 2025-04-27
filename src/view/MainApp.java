package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Utilisateur;
import utils.Session;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        boolean isConnected = LoginView.afficher();

        if (isConnected) {
            Utilisateur utilisateur = Session.getUtilisateur();
            Accueil accueil = new Accueil(primaryStage, utilisateur); // suffit
        } else {
            primaryStage.close();
        }
    }

    public static void main(String[] args) {
        launch(args); // ✅ OBLIGATOIRE pour lancer JavaFX
    }
}

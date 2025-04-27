// SessionManager.java
package utils;

import model.Client;
import model.Utilisateur;

public class SessionManager {

    private static Utilisateur utilisateurConnecte;
    private static Client clientConnecte;

    public static void setUtilisateur(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateurConnecte;
    }

    public static void setClient(Client client) {
        clientConnecte = client;
    }

    public static Client getClient() {
        return clientConnecte;
    }

    public static void clearSession() {
        utilisateurConnecte = null;
        clientConnecte = null;
    }
}

// SessionManager.java
package utils;

import model.Client;

public class SessionManager {
    private static Client clientConnecte;

    public static void setClient(Client client) {
        clientConnecte = client;
    }

    public static Client getClient() {
        return clientConnecte;
    }

    public static boolean estConnecte() {
        return clientConnecte != null;
    }

    public static void deconnexion() {
        clientConnecte = null;
    }
}

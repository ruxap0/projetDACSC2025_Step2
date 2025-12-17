package hepl.DACSC.client.views;

import hepl.DACSC.client.ClientController;

import javax.swing.*;

public class MainClient {
    public static void main(String[] args) {
        // Lancer l'interface graphique sur le thread EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                // Créer la fenêtre de login
                LoginInterface loginView = new LoginInterface();

                // Créer le controller avec la vue login
                // Le controller gérera la création de ClientInterface après login réussi
                ClientController controller = new ClientController(loginView);

                // Afficher la fenêtre de login
                loginView.setVisible(true);

            } catch (Exception e) {
                System.err.println("Erreur lors du lancement de l'application : " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}

package hepl.DACSC.client.views;

import hepl.DACSC.client.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UpdateInterface extends JFrame {

    // Composants pour l'onglet connexion
    private JTextField dateField;
    private JTextField heureField;
    private JTextField dureeField;
    private JTextField patientField;
    private JTextField reasonField;
    private JButton updateButton;

    // Bouton commun
    private JButton cancelButton;

    private WindowCloseListener closeListener;
    public interface WindowCloseListener {
        void onWindowClosed();
    }

    public UpdateInterface() {
        setTitle("Update Consultation - Application");
        setSize(420, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (closeListener != null) {
                    closeListener.onWindowClosed();
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Onglet connexion
        JPanel updatePanel = createUpdatePanel();
        tabbedPane.addTab("Update", updatePanel);

        // Footer avec bouton intégré
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Ajout du conteneur principal à la fenêtre
        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(30, 144, 255)); // DodgerBlue
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Update Consultation - Application");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panneau pour les champs de saisie avec GridBagLayout
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);

        // Date
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(new JLabel("Date : "), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        dateField = new JTextField(15);
        dateField.setPreferredSize(new Dimension(200, 25));
        fieldsPanel.add(dateField, gbc);

        // Heure
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(new JLabel("Heure : "), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        heureField = new JTextField(15);
        heureField.setPreferredSize(new Dimension(200, 25));
        fieldsPanel.add(heureField, gbc);

        // Durée
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(new JLabel("Durée : "), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        dureeField = new JTextField(15);
        dureeField.setPreferredSize(new Dimension(200, 25));
        fieldsPanel.add(dureeField, gbc);

        // Patient
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(new JLabel("Patient : "), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        patientField = new JTextField(15);
        patientField.setPreferredSize(new Dimension(200, 25));
        fieldsPanel.add(patientField, gbc);

        // Reason
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(new JLabel("Reason : "), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        reasonField = new JTextField(15);
        reasonField.setPreferredSize(new Dimension(200, 25));
        fieldsPanel.add(reasonField, gbc);

        // Bouton Update
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        updateButton = new JButton("Update");
        buttonPanel.add(updateButton);

        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(211, 211, 211)); // LightGray
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panneau pour le bouton à droite
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(211, 211, 211));

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);

        buttonPanel.add(cancelButton);
        footerPanel.add(buttonPanel, BorderLayout.CENTER);

        return footerPanel;
    }

    public void addActionListeners(ActionListener listener) {
        cancelButton.addActionListener(listener);
        updateButton.addActionListener(listener);
    }


    // Méthodes utilitaires pour vider les champs
    public void clearUpdateFields() {
        dateField.setText("");
        heureField.setText("");
        dureeField.setText("");
        patientField.setText("");
        reasonField.setText("");
    }

    public String getDate()
    {
        return dateField.getText();
    }

    public String getHeure()
    {
        return heureField.getText();
    }

    public Integer getDuree()
    {
        return Integer.parseInt(dureeField.getText());
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            UpdateInterface ui = new UpdateInterface();
            ui.setVisible(true);
        });
    }
}
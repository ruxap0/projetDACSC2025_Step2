package hepl.DACSC.client.views;

import hepl.DACSC.client.ClientController;
import hepl.DACSC.model.entity.Consultation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ClientInterface extends JFrame {
    private JTable consultationTable;
    private JTextField filterPatientField;
    private JTextField filterDateField;
    private JButton btnNew;
    private JButton btnRefresh;
    private JButton btnUpdateConsultation;
    private JButton btnLogout;
    private String currentDoctorName;
    private String currentDoctorId;
    //private JButton btnDeleteConsultation;

    private ArrayList<Consultation> consultations;

    public ClientInterface(String doctorName) {
        this.currentDoctorName = doctorName;

        setTitle("Gestion des Consultations - " + doctorName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center - Table
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Titre et info médecin
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel("Gestion des Consultations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel doctorLabel = new JLabel("Médecin: " + currentDoctorName);
        doctorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titlePanel.add(titleLabel);
        titlePanel.add(doctorLabel);

        // Bouton déconnexion
        btnLogout = new JButton("Login");

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Panel actions
        JPanel actionPanel = createActionPanel();
        centerPanel.add(actionPanel, BorderLayout.NORTH);

        // Panel filtres
        JPanel filterPanel = createFilterPanel();
        centerPanel.add(filterPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(consultationTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        return centerPanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        btnNew= new JButton("Add new");

        btnRefresh = new JButton("Rafraîchir");
        btnUpdateConsultation = new JButton("Mettre à jour la consultation");

        actionPanel.add(btnNew);
        actionPanel.add(btnRefresh);
        actionPanel.add(btnUpdateConsultation);

        return actionPanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtres"));

        filterPanel.add(new JLabel("Patient:"));
        filterPatientField = new JTextField();
        filterPanel.add(filterPatientField);

        filterPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        filterDateField = new JTextField();
        filterPanel.add(filterDateField);

        return filterPanel;
    }

    // Action listeners and other methods would go here
    public void addActionListener(ActionListener listener) {
        btnNew.addActionListener(listener);
        btnRefresh.addActionListener(listener);
        btnUpdateConsultation.addActionListener(listener);
        btnLogout.addActionListener(listener);
    }

    public ArrayList<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(ArrayList<Consultation> consultations) {
        this.consultations = consultations;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientInterface clientInterface = new ClientInterface("Dr. Dupont");
            clientInterface.setVisible(true);
            ClientController controller = new ClientController(clientInterface);
        });
    }

    public void showMessage(String erreur, String message) {
        JOptionPane.showMessageDialog(this, message, erreur, JOptionPane.INFORMATION_MESSAGE);
    }
}
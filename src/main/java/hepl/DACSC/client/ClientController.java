package hepl.DACSC.client;

import hepl.DACSC.client.views.AddInterface;
import hepl.DACSC.client.views.ClientInterface;
import hepl.DACSC.client.views.LoginInterface;
import hepl.DACSC.model.entity.Consultation;
import hepl.DACSC.model.entity.Patient;
import hepl.DACSC.protocol.Reponse;
import hepl.DACSC.protocol.Requete;
import hepl.DACSC.protocol.reponses.AddConsultationReponse;
import hepl.DACSC.protocol.reponses.AddPatientReponse;
import hepl.DACSC.protocol.reponses.LoginReponse;
import hepl.DACSC.protocol.reponses.SearchConsultationReponse;
import hepl.DACSC.protocol.requetes.AddConsultationRequete;
import hepl.DACSC.protocol.requetes.AddPatientRequete;
import hepl.DACSC.protocol.requetes.LoginRequete;
import hepl.DACSC.protocol.requetes.SearchConsultationRequete;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class ClientController implements ActionListener {
    private Socket socket;
    private ClientInterface clientView;
    private LoginInterface loginView;
    private AddInterface addView;
    private boolean isLoggedIn = false;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientController(LoginInterface loginView) {
        this.loginView = loginView;
        this.loginView.addActionListeners(this);
    }
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        switch (e.getActionCommand())
        {
            case "Se connecter":
                gestionLogin();
                break;
            case "Add new":
                if (addView == null)
                {
                    addView = new AddInterface();
                    addView.addActionListeners(this);
                }
                addView.setVisible(true);

                break;
            case "Ajouter Consultation":
                gestionNewConsultation();
            case "Rafraichir":
                reloadScreen();
                break;
            case "Ajouter Patient":
                gestionNewPatient();
                break;
            case "Mettre à jour la consultation":
                // Récupérer la consultation sélectionnée et ouvrir une vue de mise à jour
                Consultation selectedConsultation = clientView.getSelectedConsultation();
                if(selectedConsultation != null)
                {

                }
                break;
            case "Valider":
                // Pour valider la modification
                break;
            case "Logout":
                if(isLoggedIn)
                {
                    isLoggedIn = false;
                    clientView.dispose();
                    clientView = null;

                    if (loginView == null)
                    {
                        loginView = new LoginInterface();
                        loginView.setVisible(true);
                        loginView.addActionListeners(this);
                    }
                }
                break;
            case "Cancel":
                if (loginView != null)
                {
                    loginView.dispose();
                    loginView = null;
                }
                if (addView != null)
                {
                    addView.setVisible(false);
                }
                break;
        }
    }

    private void reloadScreen() {
        String patientName = clientView.getFilterPatient();
        LocalDate date = null;

        boolean hasPatientFilter = patientName != null && !patientName.isEmpty();
        boolean hasDateFilter = false;
        String dateString = clientView.getFilterDate();

        if (dateString != null && !dateString.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formatter = formatter.withLocale(Locale.FRANCE);
                date = LocalDate.parse(dateString, formatter);
                hasDateFilter = true;
            } catch (DateTimeParseException e) {
                // Date invalide
                clientView.showMessage("Erreur", "Format de date invalide. Utilisez le format YYYY-MM-DD");
                return; // ou gérer autrement selon ton besoin
            }
        }

        SearchConsultationRequete reqCons= new SearchConsultationRequete();
        reqCons.setIdDoctor(clientView.getIdDoctor());

        clientView.showMessage("", "Recherche des consultations");

        if(hasPatientFilter)
            reqCons.setPatientName(patientName);
        if(hasDateFilter)
            reqCons.setDateConsultation(date);

        try {
            oos.writeObject(reqCons);
            oos.flush();

            SearchConsultationReponse repCons = (SearchConsultationReponse) ois.readObject();
            clientView.showMessage("", "Ajout des consultations dans le tableau");
            clientView.setConsultations(repCons.getConsultations());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void gestionLogin()
    {
        try
        {
            LoginRequete requete = new LoginRequete(loginView.getUsername(), loginView.getPassword());
            socket = new Socket("192.168.253.128", 50001);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            oos.writeObject(requete);
            oos.flush();

            LoginReponse reponse = (LoginReponse) ois.readObject();
            System.out.println("Id du Docteur trouvé : " + reponse.getDoctorID());

            if (reponse.isSuccess())
            {
                isLoggedIn = true;
                loginView.dispose();
                loginView = null;

                if (clientView == null)
                {
                    clientView = new ClientInterface(requete.getLogin());
                    clientView.setVisible(true);
                    clientView.addActionListener(this);
                }
                clientView.setIdDoctor(reponse.getDoctorID());
            }
        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void gestionNewConsultation()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.FRANCE );
        LocalDate date = LocalDate.parse(addView.getDateConsultation(), formatter);

        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hour = LocalTime.parse(addView.getHeureConsultation(), hourFormatter);

        AddConsultationRequete acr = new AddConsultationRequete(
                clientView.getIdDoctor(),
                date,
                hour,
                addView.getDureeConsultation(),
                addView.getNbConsultation());

        try {
            oos.writeObject(acr);
            oos.flush();

            AddConsultationReponse reponse = (AddConsultationReponse)ois.readObject();

            if(reponse.isSuccess())
            {
                clientView.showMessage("Succès", "Consultation ajoutée !");
            }
            else
            {
                clientView.showMessage("Erreur", "Erreur lors de l'ajout");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void gestionNewPatient()
    {
        AddPatientRequete apr = new AddPatientRequete(addView.getPrenomPatient(), addView.getNomPatient());

        try {
            oos.writeObject(apr);
            oos.flush();

            AddPatientReponse reponse = (AddPatientReponse) ois.readObject();

            if(reponse.IsSuccess())
            {
                addView.showMessage("Id du client créé : " + reponse.getIdPatient());
                addView.setVisible(false);
            }
            else
            {
                addView.showMessage("Erreur : Patient non créé");
            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}

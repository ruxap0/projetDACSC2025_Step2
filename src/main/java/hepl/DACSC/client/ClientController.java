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
import hepl.DACSC.protocol.requetes.AddConsultationRequete;
import hepl.DACSC.protocol.requetes.AddPatientRequete;
import hepl.DACSC.protocol.requetes.LoginRequete;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClientController implements ActionListener {
    private Socket socket;
    private ClientInterface clientView;
    private LoginInterface loginView;
    private AddInterface addView;
    private boolean isLoggedIn = false;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientController(ClientInterface clientView) {
        this.clientView = clientView;
        this.clientView.addActionListener(this);
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
                    addView.setVisible(true);
                    addView.addActionListeners(this);
                }
                break;
            case "Ajouter Consultation":
                gestionNewConsultation();
                break;
            case "Ajouter Patient":
                gestionNewPatient();
                break;
            case "Mettre à jour la consultation":
                // Récupérer la consultation sélectionnée et ouvrir une vue de mise à jour
                Consultation consultation = new Consultation();


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
                    addView.dispose();
                    addView = null;
                }
                break;
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

                requete = null;
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
        AddConsultationRequete acr = new AddConsultationRequete(
                LocalDate.parse(addView.getDateConsultation()),
                LocalTime.parse(addView.getHeureConsultation()),
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
                addView.dispose();
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

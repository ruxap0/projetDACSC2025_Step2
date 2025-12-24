package hepl.DACSC.protocol;

import hepl.DACSC.model.dao.ConsultationDAO;
import hepl.DACSC.model.dao.DBConnexion;
import hepl.DACSC.model.dao.DoctorDAO;
import hepl.DACSC.model.dao.PatientDAO;
import hepl.DACSC.model.entity.Patient;
import hepl.DACSC.model.viewmodel.ConsultationSearchVM;
import hepl.DACSC.model.viewmodel.DoctorSearchVM;
import hepl.DACSC.model.viewmodel.PatientSearchVM;
import hepl.DACSC.protocol.reponses.AddConsultationReponse;
import hepl.DACSC.protocol.reponses.AddPatientReponse;
import hepl.DACSC.protocol.reponses.LoginReponse;
import hepl.DACSC.protocol.reponses.SearchConsultationReponse;
import hepl.DACSC.protocol.requetes.AddConsultationRequete;
import hepl.DACSC.protocol.requetes.AddPatientRequete;
import hepl.DACSC.protocol.requetes.LoginRequete;
import hepl.DACSC.protocol.requetes.SearchConsultationRequete;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class CAP implements Protocole{
    private DBConnexion conn;
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    private ConsultationDAO consultationDAO;

    public CAP(DBConnexion dbConnexion) {
        this.conn = dbConnexion;
        this.patientDAO = new PatientDAO(conn);
        this.consultationDAO = new ConsultationDAO(conn);
        this.doctorDAO = new DoctorDAO(conn);
    }

    @Override
    public String getNom() {
        return "CAP";
    }

    @Override
    public Reponse processRequest(Requete requete, Socket socket) throws IOException, SQLException {
        if (requete instanceof LoginRequete)
        {
            DoctorSearchVM dsvm = new DoctorSearchVM();
            dsvm.setLogin(((LoginRequete) requete).getLogin());
            dsvm.setPassword(((LoginRequete) requete).getPassword());

            int idDoc = doctorDAO.isDoctorPresent(dsvm);

            if(idDoc == -1)
                return new LoginReponse(false, -1);
            else
                return new LoginReponse(true, idDoc);
        }
        else if (requete instanceof AddPatientRequete)
        {
            PatientSearchVM psvm = new PatientSearchVM();
            psvm.setFirstName(((AddPatientRequete) requete).getFirstName());
            psvm.setLastName(((AddPatientRequete) requete).getLastName());

            int id = patientDAO.addPatient(psvm);

            return new AddPatientReponse(true, id);

        } else if (requete instanceof AddConsultationRequete) {
            ConsultationSearchVM csvm = new ConsultationSearchVM();
            csvm.setIdDoctor(((AddConsultationRequete) requete).getDoctorID());
            csvm.setDate(((AddConsultationRequete) requete).getDateConsultation());
            csvm.setTime(((AddConsultationRequete) requete).getHeureConsultation());
            csvm.setDuree(((AddConsultationRequete) requete).getDureeConsultation());
            csvm.setNbCons(((AddConsultationRequete) requete).getNbConsultation());

            return new AddConsultationReponse(consultationDAO.addConsultation(csvm));
        }
        else if (requete instanceof SearchConsultationRequete) {
            ConsultationSearchVM csvm = new ConsultationSearchVM();
            csvm.setIdDoctor(((SearchConsultationRequete) requete).getIdDoctor());
            csvm.setPatient(new Patient(
                    0,
                    ((SearchConsultationRequete) requete).getPatient(),
                    ""));
            csvm.setDate(((SearchConsultationRequete) requete).getDateConsultation());

            return new SearchConsultationReponse(consultationDAO.getConsultations(csvm));
        }

        return null;
    }


}

package hepl.DACSC.protocol;

import hepl.DACSC.model.dao.ConsultationDAO;
import hepl.DACSC.model.dao.DBConnexion;
import hepl.DACSC.model.dao.DoctorDAO;
import hepl.DACSC.model.dao.PatientDAO;
import hepl.DACSC.model.entity.Patient;
import hepl.DACSC.model.viewmodel.ConsultationSearchVM;
import hepl.DACSC.model.viewmodel.PatientSearchVM;
import hepl.DACSC.protocol.reponses.LoginReponse;
import hepl.DACSC.protocol.requetes.AddConsultationRequete;
import hepl.DACSC.protocol.requetes.AddPatientRequete;
import hepl.DACSC.protocol.requetes.LoginRequete;

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
            return new LoginReponse(true);
        }
        else if (requete instanceof AddPatientRequete)
        {
            PatientSearchVM psvm = new PatientSearchVM();
            psvm.setFirstName(((AddPatientRequete) requete).getFirstName());
            psvm.setLastName(((AddPatientRequete) requete).getLastName());

            patientDAO.addPatient(psvm);

        } else if (requete instanceof AddConsultationRequete) {
            //ConsultationSearchVM csvm = new ConsultationSearchVM();

        }

        return null;
    }


}

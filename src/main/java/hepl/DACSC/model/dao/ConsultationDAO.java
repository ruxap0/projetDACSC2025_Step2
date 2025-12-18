package hepl.DACSC.model.dao;

import hepl.DACSC.model.entity.Consultation;
import hepl.DACSC.model.viewmodel.ConsultationSearchVM;

import java.sql.*;
import java.time.LocalTime;

public class ConsultationDAO {
    private DBConnexion connection;

    public ConsultationDAO(DBConnexion connection) {
        this.connection = connection;
    }

    public boolean addConsultation(ConsultationSearchVM consultation) throws SQLException {
        if (consultation.getNbCons() <= 0) {
            System.out.println("Aucune consultation à ajouter");
            return false;
        }

        LocalTime endTime = consultation.getTime().plusMinutes((long) consultation.getDuree() * consultation.getNbCons());
        if (endTime.isAfter(LocalTime.of(17, 0))) {
            throw new SQLException("Erreur : Les consultations dépasseraient 17h00. Heure de fin prévue : " + endTime);
        }

        String sql = "INSERT INTO consultations (id, doctor_id, date, hour) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.getInstance().prepareStatement(sql)) {
            int nbInserted = 0;
            int nbToInsert = consultation.getNbCons();

            for (int i = 0; i < nbToInsert; i++) {
                int idConsul = getNextId();
                ps.setInt(1, idConsul);
                ps.setDate(2, Date.valueOf(consultation.getDate()));
                ps.setTime(3, Time.valueOf(consultation.getTime()));
                ps.setInt(4, 1); // Changer la logique plus tard (pour pouvoir faire les autres requetes)

                nbInserted += ps.executeUpdate();
            }

            System.out.println("Total consultations ajoutées: " + nbInserted);
            return nbInserted > 0;

        } catch(SQLException e) {
            System.err.println("Erreur SQL addConsultation:");
            e.printStackTrace();
            throw new SQLException("Erreur lors de l'ajout de la consultation : " + e.getMessage());
        }
    }

    public int getNextId() throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps1 = connection.getInstance().prepareStatement("SELECT COALESCE(MAX(id),0) FROM doctors");
        rs = ps1.executeQuery();
        rs.next();

        int idDoc = rs.getInt(1);
        idDoc++;
        return idDoc;
    }
}

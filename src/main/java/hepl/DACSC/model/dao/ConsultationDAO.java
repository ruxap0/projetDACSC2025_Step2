package hepl.DACSC.model.dao;

import hepl.DACSC.model.entity.Consultation;
import hepl.DACSC.model.viewmodel.ConsultationSearchVM;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
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

        String sql = "INSERT INTO consultations (date, time, duration) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.getInstance().prepareStatement(sql)) {
            int nbInserted = 0;
            int nbToInsert = consultation.getNbCons();

            for (int i = 0; i < nbToInsert; i++) {
                ps.setDate(1, Date.valueOf(consultation.getDate()));
                ps.setTime(2, Time.valueOf(consultation.getTime()));
                ps.setInt(3, consultation.getDuree());

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
}

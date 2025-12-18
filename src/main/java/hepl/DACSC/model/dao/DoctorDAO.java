package hepl.DACSC.model.dao;

import hepl.DACSC.model.viewmodel.DoctorSearchVM;
import java.sql.*;

public class DoctorDAO {
    private DBConnexion connection;

    public DoctorDAO(DBConnexion connection) {
        this.connection = connection;
    }

    public int isDoctorPresent(DoctorSearchVM doctor) throws SQLException {
        String sql = "SELECT id FROM doctors WHERE login = ? AND password = ?";

        try (PreparedStatement ps = connection.getInstance().prepareStatement(sql)) {
            ps.setString(1, doctor.getLogin());
            ps.setString(2, doctor.getPassword());

            System.out.println("Exécution: " + sql);
            System.out.println("Login: " + doctor.getLogin());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL:");
            e.printStackTrace();
            throw e;
        }

        return -1;
    }
}
package hepl.DACSC.model.dao;

import hepl.DACSC.model.entity.Doctor;
import hepl.DACSC.model.viewmodel.DoctorSearchVM;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

public class DoctorDAO {
    private DBConnexion connection;

    public DoctorDAO(DBConnexion connection) {
        this.connection = connection;
    }

    public boolean isDoctorPresent(DoctorSearchVM doctor) throws SQLException {
        ResultSet rs;

        StringBuilder sql = new StringBuilder("SELECT * FROM doctors WHERE 1=1");
        sql.append(" AND login = '").append(doctor.getLogin()).append("'");
        sql.append(" AND password = '").append(doctor.getPassword()).append("'");

        PreparedStatement ps = connection.getInstance().prepareStatement(sql.toString());
        var res = ps.executeQuery();

        return res.getBoolean(0);
    }
}

package hepl.DACSC.model.dao;

import java.sql.*;
import java.util.Hashtable;
import java.util.logging.*;

public class DBConnexion {
    private static Connection instance = null;

    public Connection getInstance() {
        return instance;
    }

    public DBConnexion(String dbUrl, String dbUser, String dbPassword, String driver) {
        try {
            if (instance == null || instance.isClosed()) {
                Class.forName(driver);
                instance = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                System.out.println("Connexion à la BD établie");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnexion()
    {
        try {
            if (instance != null && !instance.isClosed()) {
                instance.close();
                System.out.println("Connexion à la BD fermée");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

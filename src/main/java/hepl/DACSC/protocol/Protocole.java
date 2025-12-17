package hepl.DACSC.protocol;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public interface Protocole {
    String getNom();
    Reponse processRequest(Requete requete, Socket socket) throws IOException, SQLException;
}

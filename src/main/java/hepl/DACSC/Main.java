package hepl.DACSC;

import hepl.DACSC.model.dao.DBConnexion;
import hepl.DACSC.protocol.CAP;
import hepl.DACSC.server.ConfigServer;
import hepl.DACSC.server.ThreadServerPool;

import hepl.DACSC.server.Logger;

public class Main {
    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello, World!");
        DBConnexion con;
        ConfigServer configServer = new ConfigServer("src/main/resources/serversetup.properties");
        con = new DBConnexion(
                configServer.getDbLink(),
                configServer.getDbUser(),
                configServer.getDbPassword(),
                configServer.getDbDriver()
        );
        CAP protocol = new CAP(con);
        Logger logger = new Logger() {
            @Override
            public void Trace(String message)
            {
                System.out.println(message);
            }
        };

        ThreadServerPool server = new ThreadServerPool(
                configServer.getPortConsultation(),
                protocol,
                configServer.getPoolSize(),
                logger
        );

        server.run();
        DBConnexion.closeConnexion();

    }
}
package hepl.DACSC.server;

import hepl.DACSC.protocol.Protocole;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ThreadServerPool extends ThreadServer{
    private WaitingList waitingList;
    private ThreadGroup threadGroup;
    private int poolSize;

    public ThreadServerPool(int port, Protocole protocole, int poolSize, Logger logger) throws IOException {
        super(port, protocole, logger);
        this.poolSize = poolSize;
        this.waitingList = new WaitingList();
        this.threadGroup = new ThreadGroup("ClientHandlers");
    }

    @Override
    public void run(){
        try
        {
            for (int i = 0; i < poolSize; i++)
            {
                new ThreadClientPool(protocole, waitingList, threadGroup, logger).start();
            }
        }
        catch (IOException e)
        {
            logger.Trace("Erreur lors de la création des threads du pool: " + e.getMessage());
        }

        while(!this.isInterrupted())
        {
            Socket csocket;

            try
            {
                serverSocket.setSoTimeout(2000);
                csocket = serverSocket.accept();
                logger.Trace("Nouvelle connexion acceptée: " + csocket.getInetAddress().toString());
                waitingList.addConnexion(csocket);
            }
            catch (SocketTimeoutException e)
            {
                // Timeout pour permettre de vérifier l'interruption du thread
            }
            catch (IOException e)
            {
                logger.Trace("Erreur lors de l'acceptation d'une connexion: " + e.getMessage());
            }
        }
        logger.Trace("Fermeture du serveur de consultation.");
        threadGroup.interrupt();
    }
}

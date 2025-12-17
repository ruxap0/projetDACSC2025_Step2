package hepl.DACSC.server;

import hepl.DACSC.protocol.Protocole;

import java.io.IOException;

public class ThreadClientPool extends ThreadClient {
    private WaitingList waitingList;

    public ThreadClientPool(
            Protocole protocole,
            WaitingList waitingList,
            ThreadGroup threadGroup,
            Logger logger)
            throws IOException
    {
        super(protocole, threadGroup, logger);
        this.waitingList = waitingList;
    }

    @Override
    public void run() {
        logger.Trace("Client " + this.getName() + " start");
        boolean interrupted = false;
        while (!interrupted) {
            try
            {
                socket = waitingList.getNextConnexion();
                logger.Trace("Client " + this.getName() + " connected to " + socket.getRemoteSocketAddress());
                super.run();
            }
            catch (InterruptedException e) {
                interrupted = true;
                logger.Trace("Client " + this.getName() + " interrupted");
            }
        }
        logger.Trace("Client " + this.getName() + " end");
    }
}

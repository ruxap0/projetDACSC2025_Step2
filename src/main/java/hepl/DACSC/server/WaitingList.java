package hepl.DACSC.server;

import java.net.Socket;
import java.util.LinkedList;

public class WaitingList {
    private LinkedList<Socket> waitingList;

    public WaitingList() {
        waitingList = new LinkedList<>();
    }

    public synchronized void addConnexion(Socket clientSocket) {
        waitingList.addLast(clientSocket);
        notifyAll(); // Notify any waiting threads that a new client has been added
    }

    public synchronized Socket getNextConnexion() throws InterruptedException {
        while (waitingList.isEmpty()) {
            wait(); // Wait until a client is available
        }
        return waitingList.removeFirst();
    }
}

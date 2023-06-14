package classes._06_13_23;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable {
    Server server;

    protected ClientImpl(Server server) throws RemoteException {
        super();
        this.server = server;
        server.registerClient(this);
    }

    @Override
    public void run() {
        while (true) {

        }
    }
}

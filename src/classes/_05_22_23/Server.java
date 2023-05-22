package classes._05_22_23;

import java.rmi.Remote;
import java.rmi.RemoteException;

interface Server extends Remote {
    void sendMessage(String message) throws RemoteException;

    void registerClient(Client client) throws RemoteException;
}

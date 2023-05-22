package classes._05_22_23;

import java.rmi.Remote;
import java.rmi.RemoteException;

interface Client extends Remote {
    void receiveMessage(String message) throws RemoteException;
}

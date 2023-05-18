package classes._05_12_23;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
    void receiveMessage(String message) throws RemoteException;
}

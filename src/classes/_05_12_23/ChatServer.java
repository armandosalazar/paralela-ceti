package classes._05_12_23;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
    void sendMessage(String message) throws RemoteException;
    void registerClient(ChatClient client) throws RemoteException;
}

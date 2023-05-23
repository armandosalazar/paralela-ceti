package classes._05_22_23;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

interface Client extends Remote {
    void receiveMessage(String name, String message) throws RemoteException;

    String getName() throws RemoteException;
}

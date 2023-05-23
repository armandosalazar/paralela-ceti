package classes._05_22_23;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

interface Server extends Remote {
    void sendMessage(String name, String message) throws RemoteException;

    void registerClient(Client client) throws RemoteException;

    void addNumber(int number) throws RemoteException;

    ArrayList<Integer> getNumbers() throws RemoteException;
}

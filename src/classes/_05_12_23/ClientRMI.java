package classes._05_12_23;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {
    public static void main(String[] args) {

        try {
            String name = JOptionPane.showInputDialog("Enter your name:");

            Registry registry = LocateRegistry.getRegistry("192.168.1.12", 3000);
            ChatServer server = (ChatServer) registry.lookup("Chat");
            new Thread(new ChatClientImpl(name, server)).start();
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}

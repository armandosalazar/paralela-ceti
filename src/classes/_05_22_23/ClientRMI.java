package classes._05_22_23;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {
    private static final int PORT = 8000;
    private static final String HOST = "192.168.1.130";

    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("Enter your name:");

//        try {
//            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
//            Server server = (Server) registry.lookup("Chat");
//            new Thread(new ClientImpl(name, server)).start();
//        } catch (RemoteException | NotBoundException e) {
//            throw new RuntimeException(e);
//        }
    }
}

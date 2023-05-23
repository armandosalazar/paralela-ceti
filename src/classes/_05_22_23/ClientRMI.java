package classes._05_22_23;

import javax.swing.*;

public class ClientRMI {
    private static final int PORT = 8000;
    private static final String HOST = "192.168.1.12";

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

package classes._05_12_23;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRMI {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "192.168.1.12");
            Registry registry = LocateRegistry.createRegistry(3000);

            registry.rebind("Chat", (Remote) new ChatImpl());
            System.out.println("Server is running...");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}

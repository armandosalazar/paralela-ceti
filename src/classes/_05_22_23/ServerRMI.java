package classes._05_22_23;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRMI {
    private static final int PORT = 8000;
    private static final String HOST = "192.168.1.130";

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", HOST);
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);

            registry.rebind("Chat", new ServerImpl());
            System.out.printf("Server is running on %s:%d\n", HOST, PORT);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}


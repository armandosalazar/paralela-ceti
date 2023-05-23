package classes._05_22_23;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRMI {
    private static final int PORT = 8000;

    public static void main(String[] args) {

        try {
            String host = Inet4Address.getLocalHost().getHostAddress();
            System.setProperty("java.rmi.server.hostname", host);
            Registry registry = LocateRegistry.createRegistry(PORT);

            registry.rebind("Chat", new ServerImpl());
            System.out.printf("Server is running on %s:%d\n", host, PORT);

        } catch (RemoteException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}


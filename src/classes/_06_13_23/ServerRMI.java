package classes._06_13_23;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRMI {

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(8080);
        registry.rebind("server", new ServerImpl());
        System.out.println("Server is running...");
    }
}

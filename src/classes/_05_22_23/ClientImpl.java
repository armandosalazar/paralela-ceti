package classes._05_22_23;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

class ClientImpl extends UnicastRemoteObject implements Client, Runnable {
    Server server;
    String name;

    protected ClientImpl(String name, Server server) throws RemoteException {
        super();
        this.name = name;
        this.server = server;
        server.registerClient(this);

    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.err.println("Message received: " + message);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String message;
        while (true) {
            message = scanner.nextLine();
            try {
                server.sendMessage(name + ": " + message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

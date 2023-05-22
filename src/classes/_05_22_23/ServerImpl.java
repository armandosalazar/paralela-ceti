package classes._05_22_23;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

class ServerImpl extends UnicastRemoteObject implements Server {
    ArrayList<Client> clients;

    protected ServerImpl() throws RemoteException {
        super();
        clients = new ArrayList<>();

    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        for (Client client : clients) {
            client.receiveMessage(message);
        }
    }

    @Override
    public void registerClient(Client client) throws RemoteException {
        clients.add(client);
    }
}

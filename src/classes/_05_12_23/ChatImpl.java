package classes._05_12_23;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatImpl extends UnicastRemoteObject implements ChatServer {

    ArrayList<ChatClient> clients;
    protected ChatImpl() throws RemoteException {
        super();
        clients = new ArrayList<ChatClient>();
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        for (ChatClient client : clients) {
            client.receiveMessage(message);
        }
    }

    @Override
    public void registerClient(ChatClient client) throws RemoteException {
        clients.add(client);
    }
}
package classes._05_22_23;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

class ServerImpl extends UnicastRemoteObject implements Server {
    ArrayList<Client> clients;
    ArrayList<Integer> numbers;

    protected ServerImpl() throws RemoteException {
        super();
        clients = new ArrayList<>();
        numbers = new ArrayList<>();
    }

    @Override
    public void sendMessage(String name, String message) throws RemoteException {
        for (Client client : clients) {
            client.receiveMessage(name, message);
        }
    }

    @Override
    public void registerClient(Client client) throws RemoteException {
        System.out.printf("Client %s registered\n", client.getName());
        clients.add(client);
    }

    @Override
    public void addNumber(int number) throws RemoteException {
        numbers.add(number);
    }

    @Override
    public ArrayList<Integer> getNumbers() throws RemoteException {
        return numbers;
    }

}

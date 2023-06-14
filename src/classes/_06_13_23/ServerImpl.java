package classes._06_13_23;

import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements Server {
    ArrayList<Client> clients;
    String fileName;
    byte[] fileBytes;
    String password;


    public ServerImpl() throws RemoteException {
        super();
        this.clients = new ArrayList<>();
    }

    @Override
    public void uploadFile(String fileName, byte[] fileBytes, String password) throws Exception {
        this.fileName = fileName;
        this.fileBytes = fileBytes;
        this.password = password;
        System.out.println("Received file: " + fileName);
        System.out.println("File size: " + fileBytes.length + " bytes");
        System.out.println("File content: " + new String(fileBytes));
        System.out.println("File password: " + new String(password));
        // create a new file with the same name and content
        FileOutputStream fileOutputStream = new FileOutputStream("crypto-rmi/" + fileName);
        fileOutputStream.write(fileBytes);
        fileOutputStream.close();
    }

    @Override
    public void registerClient(Client client) throws RemoteException {
        System.out.printf("Client %s registered\n", client.toString());
        clients.add(client);
    }

    @Override
    public boolean applyAlgorithm() throws RemoteException {
        return false;
    }

    @Override
    public boolean applyAlgorithmForkJoin() throws RemoteException {
        return false;
    }

    @Override
    public boolean applyAlgorithmExecutorService() throws RemoteException {
        return false;
    }

}

package classes._06_13_23;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void uploadFile(String fileName, byte[] fileBytes, String password) throws Exception;

    void registerClient(Client client) throws RemoteException;

    boolean applyAlgorithm() throws RemoteException;

    boolean applyAlgorithmForkJoin() throws RemoteException;

    boolean applyAlgorithmExecutorService() throws RemoteException;
}

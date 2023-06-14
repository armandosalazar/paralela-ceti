package classes._06_13_23;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface Server extends Remote {
    void uploadFile(String fileName, byte[] fileBytes, String password) throws Exception;

    void registerClient(Client client) throws RemoteException;

    byte[] getSalt() throws RemoteException;

    long applyAlgorithm(String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

    long applyAlgorithmForkJoin(String password) throws RemoteException, NoSuchAlgorithmException, InvalidKeySpecException;

    long applyAlgorithmExecutorService(String password) throws RemoteException, NoSuchAlgorithmException, InvalidKeySpecException;
}

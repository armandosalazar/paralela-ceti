package classes._06_13_23;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ScheduledExecutorService;

public class ServerImpl extends UnicastRemoteObject implements Server {
    ArrayList<Client> clients;
    static String fileName;
    // byte[] fileBytes;
    ArrayList<byte[]> fileBytes;

    String password;


    public ServerImpl() throws RemoteException {
        super();
        this.clients = new ArrayList<>();
        this.fileBytes = new ArrayList<>();
    }

    @Override
    public void uploadFile(String fileName, byte[] fileBytes, String password) throws Exception {
        if (this.fileBytes.size() == 0) {
            this.fileName = fileName;
            // this.fileBytes = fileBytes;
            this.fileBytes.add(fileBytes);
            // this.password = password;
            System.out.println("Received file: " + fileName);
            System.out.println("File size: " + fileBytes.length + " bytes");
            System.out.println("File content: " + new String(fileBytes));
            System.out.println("File password: " + password);
            // create a new file with the same name and content
            FileOutputStream fileOutputStream = new FileOutputStream("files/" + fileName);
            fileOutputStream.write(fileBytes);
            fileOutputStream.close();
        } else {
            this.fileName += fileName;
            this.fileBytes.add(fileBytes);
            // this.password += password;
            System.out.println("Received file: " + fileName);
            System.out.println("File size: " + fileBytes.length + " bytes");
            System.out.println("File content: " + new String(fileBytes));
            System.out.println("File password: " + password);
            // create a new file with the same name and content
            FileOutputStream fileOutputStream = new FileOutputStream("files/" + fileName);
            fileOutputStream.write(fileBytes);
            fileOutputStream.close();
        }
    }

    @Override
    public void registerClient(Client client) throws RemoteException {
        System.out.printf("Client %s registered\n", client.toString());
        clients.add(client);
    }

    @Override
    public byte[] getSalt() throws RemoteException {
        return new byte[16];
    }

    @Override
    public long applyAlgorithm(String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        long startTime = System.currentTimeMillis();
        Algorithm.decryptFile(convertArrayListToByteArray(fileBytes), generateKeyFromPassword(password, getSalt()), "normal");
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    @Override
    public long applyAlgorithmForkJoin(String password) throws RemoteException, NoSuchAlgorithmException, InvalidKeySpecException {
        long startTime = System.currentTimeMillis();
        ForkJoinPool.commonPool().invoke(new AlgorithmForkJoin(convertArrayListToByteArray(fileBytes), generateKeyFromPassword(password, getSalt())));
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    @Override
    public long applyAlgorithmExecutorService(String password) throws RemoteException, NoSuchAlgorithmException, InvalidKeySpecException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        long startTime = System.currentTimeMillis();
        scheduledExecutorService.submit(new AlgorithmExecutorService(convertArrayListToByteArray(fileBytes), generateKeyFromPassword(password, getSalt())));
        long endTime = System.currentTimeMillis();
        return endTime - startTime;

    }

    byte[] convertArrayListToByteArray(ArrayList<byte[]> arrayList) {
        byte[] result = new byte[0];
        for (byte[] bytes : arrayList) {
            byte[] temp = new byte[result.length + bytes.length];
            System.arraycopy(result, 0, temp, 0, result.length);
            System.arraycopy(bytes, 0, temp, result.length, bytes.length);
            result = temp;
        }
        return result;
    }


    static SecretKey generateKeyFromPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        int keyLength = 128;

        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();

        return new SecretKeySpec(keyBytes, "AES");
    }

    static class Algorithm {
        static void decryptFile(byte[] fileBytes, SecretKey secretKey, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(fileBytes);

            System.out.println("Decrypted file content: " + new String(decryptedBytes));

            FileOutputStream fileOutputStream = new FileOutputStream("files/decrypted-" + algorithm + "*" + fileName);
            fileOutputStream.write(decryptedBytes);
            fileOutputStream.close();
        }
    }

    static class AlgorithmForkJoin extends RecursiveAction {
        byte[] fileBytes;
        SecretKey secretKey;

        AlgorithmForkJoin(byte[] fileBytes, SecretKey secretKey) {
            this.fileBytes = fileBytes;
            this.secretKey = secretKey;
        }

        @Override
        protected void compute() {
            try {
                Algorithm.decryptFile(fileBytes, secretKey, "fork-join");
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     IllegalBlockSizeException | BadPaddingException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class AlgorithmExecutorService implements Runnable {
        byte[] fileBytes;
        SecretKey secretKey;

        public AlgorithmExecutorService(byte[] fileBytes, SecretKey secretKey) {
            this.fileBytes = fileBytes;
            this.secretKey = secretKey;
        }

        @Override
        public void run() {
            try {
                Algorithm.decryptFile(fileBytes, secretKey, "executor-service");
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     IllegalBlockSizeException | BadPaddingException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

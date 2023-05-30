package classes._05_29_23;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket socket = serverSocket.accept();
        System.out.println("Connected to client: " + socket);
        try {
            BufferedReader input = new BufferedReader(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

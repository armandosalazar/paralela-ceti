package classes._05_29_23;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerMultithread {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started [OK] on port " + PORT + "\n");

            int idSession = 0;

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected to client: " + socket);
                new ServerThread(socket, idSession++).start();
            }
        } finally {
            System.out.println("Server closed");
            serverSocket.close();
        }
    }

    static class ServerThread extends Thread {
        private Socket socket;
        private DataOutputStream output;
        private DataInputStream input;
        private int idSession;

        public ServerThread(Socket socket, int idSession) {
            this.socket = socket;
            this.idSession = idSession;
            try {
                output = new DataOutputStream(socket.getOutputStream());
                input = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Connection with client #" + idSession + " established");
            }
        }

        public void disconnect() {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Connection with client #" + idSession + " closed");
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String echoString = input.readUTF();
                    if (echoString.equals("exit")) {
                        output.writeUTF("Bye!");
                        break;
                    }
                    System.out.println("Client #" + idSession + " sent: " + echoString);
                    output.writeUTF("Echo from server: " + echoString);
                }
            } catch (IOException e) {
            } finally {
                disconnect();
            }
        }
    }
}
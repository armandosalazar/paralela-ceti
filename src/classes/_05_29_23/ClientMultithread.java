package classes._05_29_23;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ClientMultithread {
    public static void main(String[] args) {
        ArrayList<Thread> clients = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            clients.add(new ClientThread(i));
        }
        for (Thread client : clients) {
            client.start();
        }
    }

    static class ClientThread extends Thread {
        private Socket socket;
        private DataOutputStream output;
        private DataInputStream input;
        private int idSession;

        public ClientThread(int idSession) {
            this.idSession = idSession;
        }

        @Override
        public void run() {
            super.run();
            try {
                socket = new Socket("127.0.0.1", 8080);
                output = new DataOutputStream(socket.getOutputStream());
                input = new DataInputStream(socket.getInputStream());
                Logger.getLogger(ClientThread.class.getName()).info("Connected to server");
                output.writeUTF("Hello from client #" + idSession);
                String echoString = input.readUTF();
                Logger.getLogger(ClientThread.class.getName()).info(echoString);
                output.close();
                input.close();
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}

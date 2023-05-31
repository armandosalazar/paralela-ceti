package classes._05_29_23;

import java.io.*;
import java.net.Socket;

public class Client {
    public static final String HOST = "localhost";
    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try {
            System.out.println("Client started");
            socket = new Socket(HOST, PORT);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            output.println("User: " + System.getProperty("user.name"));
            String echoString = input.readLine();
            System.out.println(echoString);
            output.println("exit");
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        } finally {
            System.out.println("Client closed");
            socket.close();
        }
    }
}

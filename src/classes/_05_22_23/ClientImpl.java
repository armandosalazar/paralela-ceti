package classes._05_22_23;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

class ClientImpl extends UnicastRemoteObject implements Client, Runnable {
    Server server;
    String name;
    JTextArea display;

    protected ClientImpl(String name, Server server, JTextArea display) throws RemoteException {
        super();
        this.name = name;
        this.server = server;
        this.display = display;
        server.registerClient(this);

    }

    @Override
    public void receiveMessage(String name, String message) throws RemoteException {
        // System.err.println("[ Message received ] " + message);
        // display.append(name + ": " + message + "\n");
        // System.out.println("Message length: " + message.length());
        // System.out.println("Message: " + message);
        if (message.length() == 1) {
            if (message.equals("0")) {
                if (messageMe(name, message)) return;
                server.addNumber(0);
                display.append("0");
            } else if (message.equals("1")) {
                if (messageMe(name, message)) return;
                server.addNumber(1);
                display.append("1");
            } else if (message.equals("2")) {
                if (messageMe(name, message)) return;
                server.addNumber(2);
                display.append("2");
            } else if (message.equals("3")) {
                if (messageMe(name, message)) return;
                server.addNumber(3);
                display.append(name + ": 3\n");
            } else if (message.equals("4")) {
                if (messageMe(name, message)) return;
                server.addNumber(4);
                display.append(name + ": 4\n");
            } else if (message.equals("5")) {
                if (messageMe(name, message)) return;
                server.addNumber(5);
                display.append(name + ": 5\n");
            } else if (message.equals("6")) {
                if (messageMe(name, message)) return;
                server.addNumber(6);
                display.append(name + ": 6\n");
            } else if (message.equals("7")) {
                if (messageMe(name, message)) return;
                server.addNumber(7);
                display.append(name + ": 7\n");
            } else if (message.equals("8")) {
                if (messageMe(name, message)) return;
                server.addNumber(8);
                display.append(name + ": 8\n");
            } else if (message.equals("9")) {
                if (messageMe(name, message)) return;
                server.addNumber(9);
                display.append(name + ": 9\n");
            } else if (message.equals("/")) {
                display.append(name + ": /\n");
                int previous = 0;
                int result = 0;
                for (int number : server.getNumbers()) {
                    result = previous / number;
                    previous = number;
                }
                // display.append(name + ": Your result = " + result + "\n");
                display.append(" = " + result + "\n");
            } else if (message.equals("*")) {
                display.append(name + ": *\n");
                int previous = 0;
                int result = 0;
                for (int number : server.getNumbers()) {
                    result = previous * number;
                    previous = number;
                }
                // display.append(name + ": Your result = " + result + "\n");
                display.append("= " + result + "\n");
            } else if (message.equals("+")) {
                display.append("+");
                int result = 0;
                for (int number : server.getNumbers()) {
                    result += number;
                }
                // display.append(name + ": Your result = " + result + "\n");
                display.append("= " + result + "\n");
            } else if (message.equals("-")) {
                display.append("-");
                int previous = 0;
                int result = 0;
                for (int number : server.getNumbers()) {
                    result = previous - number;
                    previous = number;
                }
                display.append("= " + result + "\n");
            }
        }
    }

    private boolean messageMe(String name, String message) {
        if (name.equals(this.name)) {
            // System.out.println("Message from me");
            display.append(message + " ");
            return true;
        }
        return false;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String message;
        while (true) {
            message = scanner.nextLine();
            try {
                // display.append(name + ": " + message + "\n");
                // server.sendMessage(name + ": " + message);
                server.sendMessage(name, message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessage(String name, String message) {
        try {
            // display.append(name + ": " + number + "\n");
            server.sendMessage(name, message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}

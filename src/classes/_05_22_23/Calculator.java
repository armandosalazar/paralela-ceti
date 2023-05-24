package classes._05_22_23;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Calculator extends JFrame {
    private static final JTextArea display = new JTextArea(10, 14);
    private static ClientImpl client = null;
    private static String name;

    public Calculator() {
        super("Calculator");
        add(new GUI());
        setSize(250, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws NotBoundException, RemoteException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("192.168.1.12", 8000);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Server server = (Server) registry.lookup("Chat");
        name = JOptionPane.showInputDialog("Enter your name:");
        client = new ClientImpl(name, server, display);
        new Thread(client).start();

        calculator.setTitle("Calculator of " + name);
        // System.out.printf("Client %s connected%n", name);

    }

    static class GUI extends JPanel {
        GUI() {
            setLayout(new BorderLayout());
            add(new Display(), BorderLayout.NORTH);
            add(new Buttons(), BorderLayout.CENTER);
        }

        static class Display extends JPanel {
            Display() {
                setBackground(Color.getHSBColor(0.5f, 0.5f, 0.6f));
                display.setLineWrap(true);
                display.setWrapStyleWord(true);
                display.setEditable(false);
                display.setFont(new Font("Ubuntu", Font.ITALIC, 20));
                add(new JScrollPane(display));
            }
        }

        static class Buttons extends JPanel {
            private final JPanel panel = new JPanel();
            private final JButton[] buttons = new JButton[16];

            Buttons() {
                setBackground(Color.BLUE);
                setLayout(new BorderLayout());
                setPanels();
            }

            private void setPanels() {
                panel.setBackground(Color.getHSBColor(0.5f, 0.5f, 0.5f));
                panel.setLayout(new GridLayout(4, 4));
                setButtons();
                add(panel, BorderLayout.CENTER);
            }

            private void setButtons() {
                for (int i = 0; i < buttons.length; i++) {
                    buttons[i] = new JButton();
                }
                buttons[0].setText("7");
                buttons[0].addActionListener(e -> {
                    client.sendMessage(name, "7");
                });

                buttons[1].setText("8");
                buttons[1].addActionListener(e -> {
                    client.sendMessage(name, "8");
                });
                buttons[2].setText("9");
                buttons[2].addActionListener(e -> {
                    client.sendMessage(name, "9");
                });
                buttons[3].setText("/");
                buttons[3].addActionListener(e -> {
                    client.sendMessage(name, "/");
                });
                buttons[4].setText("4");
                buttons[4].addActionListener(e -> {
                    client.sendMessage(name, "4");
                });
                buttons[5].setText("5");
                buttons[5].addActionListener(e -> {
                    client.sendMessage(name, "5");
                });
                buttons[6].setText("6");
                buttons[6].addActionListener(e -> {
                    client.sendMessage(name, "6");
                });
                buttons[7].setText("*");
                buttons[7].addActionListener(e -> {
                    client.sendMessage(name, "*");
                });
                buttons[8].setText("1");
                buttons[8].addActionListener(e -> {
                    client.sendMessage(name, "1");
                });
                buttons[9].setText("2");
                buttons[9].addActionListener(e -> {
                    client.sendMessage(name, "2");
                });
                buttons[10].setText("3");
                buttons[10].addActionListener(e -> {
                    client.sendMessage(name, "3");
                });
                buttons[11].setText("-");
                buttons[11].addActionListener(e -> {
                    client.sendMessage(name, "-");
                });
                buttons[12].setText("0");
                buttons[12].addActionListener(e -> {
                    client.sendMessage(name, "0");
                });
                buttons[13].setText(" ");
                buttons[14].setText(" ");
                buttons[15].setText("+");
                buttons[15].addActionListener(e -> {
                    client.sendMessage(name, "+");
                });

                for (JButton button : buttons) {
                    button.setFont(new Font("Ubuntu", Font.BOLD, 20));
                    panel.add(button);
                }
            }
        }
    }
}

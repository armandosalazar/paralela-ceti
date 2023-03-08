package classes._03_08_23;

import javax.swing.*;
import java.awt.*;

public class Swing {
    public static void main(String[] args) {
        new Window();
    }
}

class Window extends JFrame {
    public Window() {
        super("Swing");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* Configuración de layout */
        add(new Panel());
        /* Centrar la ventana */
        setLocationRelativeTo(null);
        /* Añadir menú */
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        JMenuItem menuItem = new JMenuItem("Salir");
        menuItem.addActionListener(e -> System.exit(0));
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        /* Mostrar ventana */
        setVisible(true);
    }
}

class Panel extends JPanel {

    public Panel() {
        super();
        setLayout(new BorderLayout());
        JPanel panelText = new JPanel();
        panelText.setBackground(Color.WHITE);
        panelText.setLayout(new FlowLayout());
        panelText.add(new JLabel("¡Hola mundo!"));
        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(Color.ORANGE);
        panelButtons.setLayout(new FlowLayout());
        panelButtons.add(new JTextField(10));
        panelButtons.add(new JButton("Aceptar"));
        panelButtons.add(new JCheckBox("Opción 1"));
        DefaultListModel<String> list = new DefaultListModel<>();
        list.addElement("Rust");
        list.addElement("JavaScript");
        list.addElement("Java");
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(Color.GREEN);
        panelCenter.setLayout(new GridLayout(2, 2));
        String names[] = {"Rust", "JavaScript", "Java", "Python"};
        JComboBox comboBox = new JComboBox(names);
        panelCenter.add(comboBox);
        JPanel panelRadio = new JPanel();
        panelRadio.setLayout(new GridLayout(3, 1));
        panelRadio.setBackground(Color.CYAN);
        panelRadio.add(new JRadioButton("HTML"));
        panelRadio.add(new JRadioButton("CSS"));
        panelRadio.add(new JRadioButton("JS"));
        panelCenter.add(panelRadio);
        JPanel panelButtonsDialogs = new JPanel();
        panelButtonsDialogs.setLayout(new GridLayout(4, 1));
        panelButtonsDialogs.setBackground(Color.darkGray);
        JButton messageButton = new JButton("Mensaje");
        messageButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Hola"));
        panelButtonsDialogs.add(messageButton);
        JButton confirmationButton = new JButton("Confirmación");
        confirmationButton.addActionListener(e -> JOptionPane.showConfirmDialog(this, "¿Estás seguro?"));
        panelButtonsDialogs.add(confirmationButton);
        JButton inputButton = new JButton("Entrada");
        inputButton.addActionListener(e -> JOptionPane.showInputDialog(this, "¿Cuál es tu nombre?"));
        panelButtonsDialogs.add(inputButton);
        String data[][] = {{"Rust", "JavaScript", "Java", "Python"}, {"HTML", "CSS", "JS"}};
        String columns[] = {"Lenguajes", "Herramientas"};
        JTable table = new JTable(data, columns);
        panelCenter.add(new JScrollPane(table));
        panelCenter.add(panelButtonsDialogs);
        add(panelText, BorderLayout.NORTH);
        add(new JScrollPane(new JTextArea(10, 10)), BorderLayout.WEST);
        add(panelCenter, BorderLayout.CENTER);
        add(new JScrollPane(new JList<>(list)), BorderLayout.EAST);
        add(panelButtons, BorderLayout.SOUTH);
    }
}
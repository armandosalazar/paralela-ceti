package classes._02_07_23;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsProgram {
    public static void main(String[] args) {
        new Window().setVisible(true);
    }
}

class Window extends JFrame {
    private JButton button1 = new JButton("Button 1");
    private JButton button2 = new JButton("Button 2");

    Window() {
        setTitle("Ejemplo 1");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Panel());
        addEvents(this);
    }

    private void addEvents(Window parent) {
        button1.addActionListener(e -> JOptionPane.showMessageDialog(parent, "Hola"));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parent, "Adiós");
            }
        });
    }

    class Panel extends JPanel {
        Panel() {
            add(button1);
            add(button2);
        }
    }
}
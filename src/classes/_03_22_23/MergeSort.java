package classes._03_22_23;

import javax.swing.*;
import java.awt.*;

public class MergeSort {
    public static void main(String[] args) {
        new GUI().setVisible(true);
    }
}

class GUI extends JFrame {
    public GUI() {
        setTitle("MergeSort");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new Panel());
    }

    class Panel extends JPanel {

        private final JPanel panelButtons = new JPanel();
        private final JButton buttonGenerate = new JButton("Generate");
        private final JButton buttonMergeSort = new JButton("MergeSort");
        private final JButton buttonForkJoin = new JButton("Fork/Join");
        private final JButton buttonExecuteServices = new JButton("Execute Services");

        private final JPanel panelInput = new JPanel();
        private final JLabel labelInput = new JLabel("Number items: ");
        private final JTextField textFieldInput = new JTextField(10);

        private final JPanel panelOutput = new JPanel();
        private final JLabel labelOutput = new JLabel("Generate: ");
        private final TextArea textAreaOutputGenerate = new TextArea();
        private final JLabel labelOutputMergeSort = new JLabel("MergeSort: ");
        private final TextArea textAreaOutputMergeSort = new TextArea();

        Panel() {
            setLayout(new BorderLayout());
            setPanelInput();
            setPanelOutput();
            setPanelButtons();
        }

        private void setPanelInput() {
            panelInput.add(labelInput);
            panelInput.add(textFieldInput);
            this.add(panelInput, BorderLayout.NORTH);
        }

        private void setPanelOutput() {
            panelOutput.setLayout(new GridLayout(1, 2));
            panelOutput.add(labelOutput);
            panelOutput.add(textAreaOutputGenerate);
            panelOutput.add(labelOutputMergeSort);
            panelOutput.add(textAreaOutputMergeSort);
            this.add(panelOutput, BorderLayout.CENTER);
        }

        private void setPanelButtons() {
            panelButtons.add(buttonGenerate);
            panelButtons.add(buttonMergeSort);
            panelButtons.add(buttonForkJoin);
            panelButtons.add(buttonExecuteServices);
            this.add(panelButtons, BorderLayout.SOUTH);
        }

    }
}

class Algorithm {
}
package classes._03_25_23;

import javax.crypto.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) {
        new GUI().setVisible(true);
    }
}

class GUI extends JFrame {

    public GUI() {
        super("Encrypt Files");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new Panel());
    }

    static class Panel extends JPanel {
        private final JButton buttonSelectFiles = new JButton("Select Files");
        private final JPanel panelButtons = new JPanel();

        Panel() {
            setLayout(new BorderLayout());
            add(panelButtons, BorderLayout.SOUTH);
            selectEvents();
        }

        void selectEvents() {
            panelButtons.add(buttonSelectFiles);
            buttonSelectFiles.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int option = fileChooser.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    // System.out.println(fileChooser.getSelectedFile());
                    // System.out.println(Arrays.toString(fileChooser.getSelectedFiles()));
                    File[] files = fileChooser.getSelectedFiles();
                    try {
                        Algorithm.encrypt(files);
                    } catch (FileNotFoundException | NoSuchPaddingException | IllegalBlockSizeException |
                             NoSuchAlgorithmException | BadPaddingException | InvalidKeyException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }
}

class Algorithm {

    static void encrypt(File[] files) throws FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey secretKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("DES");

        for (File file : files) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                byte[] bytes = scanner.nextLine().getBytes();
                System.out.println(Arrays.toString(bytes));
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encryptedBytes = cipher.doFinal(bytes);
                System.out.println(Arrays.toString(encryptedBytes));
            }
        }
    }

}
package classes._03_25_23;

import javax.crypto.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) {
        new GUI().setVisible(true);
    }
}

class GUI extends JFrame {

    public GUI() {
        super("Encrypt Files");
        setSize(460, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new Panel());
    }

    static class Panel extends JPanel {
        private final JButton buttonSelectFiles = new JButton("Select Files");
        private final JPanel panelButtons = new JPanel();

        private final JTable tableTimes = new JTable();
        private final String[] columnNamesTimes = {"Encrypt normal", "Encrypt Fork/Join", "Encrypt Executor Service"};
        private final JScrollPane scrollPaneTimes = new JScrollPane(tableTimes);

        private final JPanel panelTableTimes = new JPanel();

        private final JTable tableFiles = new JTable();
        private final String[] columnNames = {"File Name", "File Size", "File Path"};

        private final JScrollPane scrollPaneFiles = new JScrollPane(tableFiles);

        private final JPanel panelTableFiles = new JPanel();

        Panel() {
            setLayout(new BorderLayout());
            selectEvents();
            // set columns name to table
            tableFiles.setModel(new DefaultTableModel(columnNames, 0));
            panelTableFiles.setLayout(new BorderLayout());
            panelTableFiles.add(scrollPaneFiles, BorderLayout.CENTER);
            add(panelTableFiles, BorderLayout.NORTH);
            // set columns name to table
            tableTimes.setModel(new DefaultTableModel(new String[1][3], columnNamesTimes));
            panelTableTimes.setLayout(new BorderLayout());
            panelTableTimes.add(scrollPaneTimes, BorderLayout.CENTER);
            add(panelTableTimes, BorderLayout.CENTER);
            // add buttons to panel
            add(panelButtons, BorderLayout.SOUTH);
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
                        Algorithm.encrypt(files, tableFiles);
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

    static void encrypt(File[] files, JTable table) throws FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey secretKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("DES");
        table.setModel(new DefaultTableModel(new String[files.length][3], new String[]{"File Name", "File Size", "File Path"}));
        for (int i = 0; i < files.length; i++) {
            table.getModel().setValueAt(files[i].getName(), i, 0);
            table.getModel().setValueAt(files[i].length() + " bytes", i, 1);
            table.getModel().setValueAt(files[i].getPath(), i, 2);
            Scanner scanner = new Scanner(files[i]);
            while (scanner.hasNextLine()) {
                byte[] bytes = scanner.nextLine().getBytes();
                // System.out.println(Arrays.toString(bytes));
                // System.out.println(new String(bytes));
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encryptedBytes = cipher.doFinal(bytes);
                // System.out.println(Arrays.toString(encryptedBytes));
                System.out.println(new String(encryptedBytes));
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
                // System.out.println(Arrays.toString(decryptedBytes));
                System.out.println(new String(decryptedBytes));
            }
        }

//        for (File file : files) {
//            table.getModel().setValueAt(file.getName(), 0, 0);
//            table.getModel().setValueAt(file.length(), 0, 1);
//            table.getModel().setValueAt(file.getPath(), 0, 2);
//            Scanner scanner = new Scanner(file);
//            while (scanner.hasNextLine()) {
//                byte[] bytes = scanner.nextLine().getBytes();
//                // System.out.println(Arrays.toString(bytes));
//                // System.out.println(new String(bytes));
//                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//                byte[] encryptedBytes = cipher.doFinal(bytes);
//                // System.out.println(Arrays.toString(encryptedBytes));
//                // System.out.println(new String(encryptedBytes));
//                cipher.init(Cipher.DECRYPT_MODE, secretKey);
//                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//                // System.out.println(Arrays.toString(decryptedBytes));
//                // System.out.println(new String(decryptedBytes));
//            }
//        }
    }

}
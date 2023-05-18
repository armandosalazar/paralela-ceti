package classes._03_25_23;

import javax.crypto.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

public class Project {
    public static void main(String[] args) {
        new GUI().setVisible(true);
    }
}

class GUI extends JFrame {

    public GUI() {
        super("Encrypt Files");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new Panel(), BorderLayout.CENTER);
    }

    static class Panel extends JPanel {
        private final JButton buttonSelectFiles = new JButton("Select Files");
        private final JPanel panelSouth = new JPanel();

        private final JTable tableTimes = new JTable();
        private final String[] columnNamesTimes = {"Encrypt normal", "Encrypt Fork/Join", "Encrypt Executor Service"};
        private final Object[][] ct = {{"0.0 ms", "0.0 ms", "0.0 ms"}};
        // private final JScrollPane scrollPaneTimes = new JScrollPane(tableTimes);

        private final JPanel panelNorth = new JPanel();

        private final JTable tableFiles = new JTable();
        private final String[] columnNames = {"File Name", "File Size", "File Path"};

        // private final JScrollPane scrollPaneFiles = new JScrollPane(tableFiles);

        private final JPanel panelCenter = new JPanel();

        private final JButton buttonEncryptNormal = new JButton("Encrypt Normal");
        private final JButton buttonEncryptForkJoin = new JButton("Encrypt Fork/Join");
        private final JButton buttonEncryptExecutorService = new JButton("Encrypt Executor Service");
        private final JPanel panelEncryptButtons = new JPanel();

        Panel() {
            setLayout(new BorderLayout());
            selectEvents();
            // set columns name to table
            tableTimes.setModel(new DefaultTableModel(ct, columnNamesTimes));
            // panelNorth.setLayout(new BorderLayout());
            panelNorth.setBackground(Color.RED);
            panelNorth.add(new JScrollPane(tableTimes));
            // panelNorth.add(new JScrollPane(tableTimes));
            // add buttons to panel
//            panelEncryptButtons.add(buttonEncryptNormal);
//            panelEncryptButtons.add(buttonEncryptForkJoin);
//            panelEncryptButtons.add(buttonEncryptExecutorService);
            // panelNorth.add(panelEncryptButtons);
            // panelTableTimes.add(scrollPaneTimes);
            // set columns name to table
            tableFiles.setModel(new DefaultTableModel(columnNames, 0));
            // panelCenter.setLayout(new BorderLayout());
            panelCenter.setBackground(Color.BLUE);
            panelCenter.add(new JScrollPane(tableFiles));
            // panelCenter.add(scrollPaneFiles, BorderLayout.CENTER);

            panelSouth.setBackground(Color.GREEN);
            panelSouth.add(buttonSelectFiles);

            // add buttons to panel
            add(panelNorth, BorderLayout.NORTH);
            add(panelCenter, BorderLayout.CENTER);
            add(panelSouth, BorderLayout.SOUTH);
        }

        void selectEvents() {
            buttonSelectFiles.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int option = fileChooser.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    // System.out.println(fileChooser.getSelectedFile());
                    // System.out.println(Arrays.toString(fileChooser.getSelectedFiles()));
                    File[] files = fileChooser.getSelectedFiles();
                    try {
                        // Take time for normal
                        long startTime = System.currentTimeMillis();
                        Algorithm.encrypt(files, tableFiles);
                        long endTime = System.currentTimeMillis();
                        tableTimes.getModel().setValueAt(endTime - startTime + "ms", 0, 0);

                        // Take time for Fork/Join
                        startTime = System.currentTimeMillis();
                        // Algorithm.encrypt(files, tableFiles);
                        ForkJoinPool.commonPool().invoke(new AlgorithmForkJoin(files, tableFiles));
                        endTime = System.currentTimeMillis();
                        tableTimes.getModel().setValueAt(endTime - startTime + "ms", 0, 1);

                        // Take time for Executor Service
                        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
                        startTime = System.currentTimeMillis();
                        // Algorithm.encrypt(files, tableFiles);
                        executorService.execute(new AlgorithmExecutorService(files, tableFiles));
                        endTime = System.currentTimeMillis();
                        tableTimes.getModel().setValueAt(endTime - startTime + "ms", 0, 2);

                        // Algorithm.decrypt(files, tableFiles);
                    } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                             BadPaddingException | InvalidKeyException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
    }
}

class Algorithm {

    static void encrypt(File[] files, JTable table) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey secretKey = keyGenerator.generateKey();
        // System.out.printf("Secret Key: %s%n", secretKey.getEncoded());
        Cipher cipher = Cipher.getInstance("DES");
        table.setModel(new DefaultTableModel(new String[files.length][3], new String[]{"File Name", "File Size", "File Path"}));
        for (int i = 0; i < files.length; i++) {
            table.getModel().setValueAt(files[i].getName(), i, 0);
            table.getModel().setValueAt(files[i].length() + " bytes", i, 1);
            table.getModel().setValueAt(files[i].getPath(), i, 2);
            Scanner scanner = new Scanner(files[i]);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                byte[] bytes = scanner.nextLine().getBytes();
                // System.out.println(Arrays.toString(bytes));
                // System.out.println(new String(bytes));
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encryptedBytes = cipher.doFinal(bytes);
                // System.out.println(Arrays.toString(encryptedBytes));
//                System.out.println(new String(encryptedBytes));
//                cipher.init(Cipher.DECRYPT_MODE, secretKey);
//                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
                // System.out.println(Arrays.toString(decryptedBytes));
//                System.out.println(new String(decryptedBytes));
                stringBuilder.append(new String(encryptedBytes));
            }
            // System.out.println(stringBuilder);
            FileWriter fileWriter = new FileWriter(files[i], false);
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();
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

    static void decrypt(File[] files, JTable table) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey secretKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("DES");
        table.setModel(new DefaultTableModel(new String[files.length][3], new String[]{"File Name", "File Size", "File Path"}));
        for (int i = 0; i < files.length; i++) {
            table.getModel().setValueAt(files[i].getName(), i, 0);
            table.getModel().setValueAt(files[i].length() + " bytes", i, 1);
            table.getModel().setValueAt(files[i].getPath(), i, 2);
            Scanner scanner = new Scanner(files[i]);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                byte[] bytes = scanner.nextLine().getBytes();
                System.out.println(Arrays.toString(bytes));
                // System.out.println(new String(bytes));
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] decryptedBytes = cipher.doFinal(bytes);
//                // System.out.println(Arrays.toString(decryptedBytes));
////                System.out.println(new String(decryptedBytes));
////                cipher.init(Cipher.DECRYPT_MODE, secretKey);
////                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//                // System.out.println(Arrays.toString(decryptedBytes));
//
////                System.out.println(new String(decryptedBytes));
//
//                stringBuilder.append(new String(decryptedBytes));
            }
            // System.out.println(stringBuilder);
//            FileWriter fileWriter = new FileWriter(files[i], false);
//            fileWriter.write(stringBuilder.toString());
//            fileWriter.close();
        }
    }


}

class AlgorithmForkJoin extends RecursiveAction {
    private File[] files;
    private JTable table;

    public AlgorithmForkJoin(File[] files, JTable table) {
        this.files = files;
        this.table = table;
    }

    @Override
    protected void compute() {
        if (files.length == 1) {
            try {
                Algorithm.encrypt(files, table);
            } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                     BadPaddingException | InvalidKeyException | IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            File[] files1 = Arrays.copyOfRange(files, 0, files.length / 2);
            File[] files2 = Arrays.copyOfRange(files, files.length / 2, files.length);
            invokeAll(new AlgorithmForkJoin(files1, table), new AlgorithmForkJoin(files2, table));
        }
    }

}

// Programing using ExecutorService

class AlgorithmExecutorService implements Runnable {
    private File[] files;
    private JTable table;

    public AlgorithmExecutorService(File[] files, JTable table) {
        this.files = files;
        this.table = table;
    }


    @Override
    public void run() {
        try {
            Algorithm.encrypt(files, table);
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}

package classes._06_13_23;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class CryptoRMI {
    // RMI variables
    static Server server;

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("192.168.1.138", 8080);
        server = (Server) registry.lookup("server");
        new Thread(new ClientImpl(server)).start();
        new UI().setVisible(true);
    }

    static class UI extends JFrame {
        private final JPanel panel = new JPanel(), panelTimes = new JPanel(), panelFiles = new JPanel(), panelButtons = new JPanel();
        private final JTable tableTimes = new JTable();
        private final JTable tableFiles = new JTable();
        private final JButton buttonSelectFile = new JButton("Select File");
        private final JButton buttonUploadFile = new JButton("Upload File");
        private final JButton buttonApplyAlgorithms = new JButton("Apply Algorithms");
        private final JTextField passwordField = new JTextField();

        private String fileName, filePath;

        public UI() throws HeadlessException {
            super("CryptoRMI");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(800, 600);
            setLocationRelativeTo(null);

            panel.setLayout(new BorderLayout());
            panel.setBackground(Color.WHITE);

            tableTimes.setFillsViewportHeight(true);
            tableTimes.setModel(new DefaultTableModel(new String[]{"Encrypt normal", "Encrypt Fork/Join", "Encrypt Executor Service"}, 0));
            tableFiles.setFillsViewportHeight(true);
            tableFiles.setModel(new DefaultTableModel(new String[]{"File name", "File size", "File path"}, 0));


            panelFiles.setLayout(new BorderLayout());
            panelFiles.setBackground(Color.WHITE);
            panelFiles.add(new JLabel("File", SwingConstants.CENTER), BorderLayout.NORTH);
            panelFiles.add(new JScrollPane(tableFiles), BorderLayout.CENTER);

            panelTimes.setLayout(new BorderLayout());
            panelTimes.setBackground(Color.WHITE);
            panelTimes.add(new JLabel("Times", SwingConstants.CENTER), BorderLayout.NORTH);
            panelTimes.add(new JScrollPane(tableTimes), BorderLayout.CENTER);

            panelButtons.setLayout(new GridLayout(1, 4));
            panelButtons.setBackground(Color.WHITE);

            panelButtons.add(buttonApplyAlgorithms);
            panelButtons.add(buttonSelectFile);
            panelButtons.add(buttonUploadFile);
            panelButtons.add(passwordField);


            panel.add(panelFiles, BorderLayout.NORTH);
            panel.add(panelTimes, BorderLayout.CENTER);
            panel.add(panelButtons, BorderLayout.SOUTH);


            addEvents();

            add(panel);
        }

        void addEvents() {
            buttonSelectFile.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    fileName = fileChooser.getSelectedFile().getName();
                    long fileSize = fileChooser.getSelectedFile().length();
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    ((DefaultTableModel) tableFiles.getModel()).addRow(new Object[]{fileName, fileSize + " bytes", filePath});
                }
            });

            buttonUploadFile.addActionListener(e -> {
                if (passwordField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Password field is empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    FileInputStream fileInputStream = null;
                    try {
                        fileInputStream = new FileInputStream(filePath);
                        byte[] fileBytes = new byte[fileInputStream.available()];
                        fileInputStream.read(fileBytes);
                        fileInputStream.close();

                        // TODO: 6/13/2021
                        String password = passwordField.getText().trim();
                        SecretKey secretKey = generateKeyFromPassword(password, server.getSalt());
                        System.out.println(secretKey.getEncoded());

                        Cipher cipher = Cipher.getInstance("AES");
                        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                        byte[] encryptedBytes = cipher.doFinal(fileBytes);


                        server.uploadFile(fileName, encryptedBytes, password);

                        JOptionPane.showMessageDialog(this, "File uploaded successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            buttonApplyAlgorithms.addActionListener(e -> {
                try {
                    ((DefaultTableModel) tableTimes.getModel()).addRow(new Object[]{server.applyAlgorithm(passwordField.getText().trim()), server.applyAlgorithmForkJoin(passwordField.getText().trim()), server.applyAlgorithmExecutorService(passwordField.getText().trim())});
                    File directory = new File("files");
                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                    File[] files = directory.listFiles();
                    for (File file : files) {
                        if (file.isFile()) {
                            // TODO: add row with file name, file size, file path
                            ((DefaultTableModel) tableFiles.getModel()).addRow(new Object[]{file.getName(), file.length() + " bytes", file.getAbsolutePath()});
                        }
                    }

                } catch (InvalidKeySpecException | NoSuchPaddingException | IllegalBlockSizeException |
                         BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        SecretKey generateKeyFromPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
            int iterations = 1000;
            int keyLength = 128;

            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();

            return new SecretKeySpec(keyBytes, "AES");
        }
    }


}

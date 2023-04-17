package classes._03_22_23;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ScheduledExecutorService;

public class MergeSort {
    public static void main(String[] args) {
        new GUI().setVisible(true);
    }
}

class GUI extends JFrame {
    public GUI() {
        setTitle("MergeSort");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        add(new Panel());
    }

    static class Panel extends JPanel {
        // private final Font font = new Font("Euclid Circular A", Font.PLAIN, 14);
        // light green
        // Color primaryColor = new Color(0xCBFAD6);
        // light blue
        Color primaryColor = new Color(0x9EE0F5);

        private final JPanel panelButtons = new JPanel();
        private final JButton buttonGenerate = new JButton("Generate");
        private final JButton buttonClear = new JButton("Clear");
        private final JButton buttonMergeSort = new JButton("MergeSort");
        private final JButton buttonForkJoin = new JButton("Fork/Join");
        private final JButton buttonExecutorService = new JButton("Executor Service");

        private final JPanel panelInput = new JPanel();
        private final JLabel labelInput = new JLabel("Number items: ");
        private final JTextField textFieldInput = new JTextField(10);

        private final JPanel panelOutput = new JPanel();
        private final JLabel labelOutput = new JLabel("Generate: ");
        private final JTextArea textAreaOutputGenerate = new JTextArea();
        private final JLabel labelOutputMergeSort = new JLabel("MergeSort: ");
        private final JTextArea textAreaOutputMergeSort = new JTextArea();
        private final JScrollPane scrollPaneGenerate = new JScrollPane(textAreaOutputGenerate);
        private final JScrollPane scrollPaneMergeSort = new JScrollPane(textAreaOutputMergeSort);

        private final JLabel labelTime = new JLabel("Times: ");
        private final JTable timeTable = new JTable();
        private final JScrollPane scrollPaneTime = new JScrollPane(timeTable);
        private final String[] columnNames = {"MergeSort", "Fork/Join", "Executor Service"};
        private final Object[][] ct = {{"0.0 ms", "0.0 ms", "0.0 ms"}};
        private final DefaultTableModel model = new DefaultTableModel(ct, columnNames);
        private int[] data;


        Panel() {
            setLayout(new BorderLayout());
            setStyles();
            setPanelInput();
            setPanelOutput();
            setPanelButtons();
        }

        private void setStyles() {
//            labelInput.setFont(font);
//            textFieldInput.setFont(font);
//            labelOutput.setFont(font);
//            textAreaOutputGenerate.setFont(font);
//            labelOutputMergeSort.setFont(font);
//            textAreaOutputMergeSort.setFont(font);
//            labelTime.setFont(font);
//            timeTable.setFont(font);
//            buttonGenerate.setFont(font);
//            buttonClear.setFont(font);
//            buttonMergeSort.setFont(font);
//            buttonForkJoin.setFont(font);
//            buttonExecutorService.setFont(font);
            panelButtons.setBackground(primaryColor);
            panelInput.setBackground(primaryColor);
        }

        private void setPanelInput() {
            panelInput.setBackground(primaryColor);
            panelInput.add(labelInput);
            panelInput.add(textFieldInput);
            this.add(panelInput, BorderLayout.NORTH);
            JPanel paddingLeft = new JPanel();
            paddingLeft.setBackground(primaryColor);
            this.add(paddingLeft, BorderLayout.WEST);
            JPanel paddingRight = new JPanel();
            paddingRight.setBackground(primaryColor);
            this.add(paddingRight, BorderLayout.EAST);
        }

        private void setPanelOutput() {
            JPanel panelTitles = new JPanel();
            panelTitles.setBackground(primaryColor);
            panelTitles.setLayout(new GridLayout(1, 2));
            panelTitles.add(labelOutput);
            panelTitles.add(labelOutputMergeSort);

            JPanel panelTextAreas = new JPanel();
            panelTextAreas.setBackground(primaryColor);
            panelTextAreas.setLayout(new GridLayout(2, 2));
            textAreaOutputGenerate.setLineWrap(true);
            textAreaOutputMergeSort.setLineWrap(true);
            panelTextAreas.add(scrollPaneGenerate);
            panelTextAreas.add(scrollPaneMergeSort);

            JPanel panelTime = new JPanel();
            panelTime.setBackground(primaryColor);
            panelTime.setLayout(new BorderLayout());
            timeTable.setDragEnabled(false);
            timeTable.setRowSelectionAllowed(false);
            timeTable.setModel(model);
            panelTime.add(labelTime, BorderLayout.NORTH);
            panelTime.add(scrollPaneTime, BorderLayout.CENTER);

            panelTextAreas.add(panelTime);

            panelOutput.setLayout(new BorderLayout());
            panelOutput.add(panelTitles, BorderLayout.NORTH);
            panelOutput.add(panelTextAreas, BorderLayout.CENTER);

            this.add(panelOutput, BorderLayout.CENTER);
        }

        private void setPanelButtons() {
            setButtonsEvents();
            panelButtons.add(buttonGenerate);
            panelButtons.add(buttonClear);
            panelButtons.add(buttonMergeSort);
            panelButtons.add(buttonForkJoin);
            panelButtons.add(buttonExecutorService);
            this.add(panelButtons, BorderLayout.SOUTH);
        }

        private void setButtonsEvents() {
            buttonGenerate.addActionListener(e -> generate());
            buttonClear.addActionListener(e -> clear());
            buttonMergeSort.addActionListener(e -> mergeSortSimple());
            buttonForkJoin.addActionListener(e -> mergeSortForkJoin());
            buttonExecutorService.addActionListener(e -> mergeSortExecuteService());
        }

        private void generate() {
            try {
                int length = Integer.parseInt(textFieldInput.getText());
                data = new int[length];
                for (int i = 0; i < length; i++) {
                    data[i] = new Random().nextInt(1, 101);
                }
                textAreaOutputGenerate.setText("");
                for (int i = 0; i < length; i++) {
                    if (i == 0) textAreaOutputGenerate.append("[ " + data[i] + ", ");
                    else if (i == length - 1) textAreaOutputGenerate.append(data[i] + " ]");
                    else textAreaOutputGenerate.append(data[i] + ", ");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        }

        private void clear() {
            textAreaOutputGenerate.setText("");
            textAreaOutputMergeSort.setText("");
//            model.setValueAt("0.0 ms", 0, 0);
//            model.setValueAt("0.0 ms", 0, 1);
//            model.setValueAt("0.0 ms", 0, 2);
        }

        private void mergeSortSimple() {
            long startTime = System.currentTimeMillis();
            AlgorithmSimple.mergeSort(data);
            long endTime = System.currentTimeMillis();
            model.setValueAt(Double.parseDouble(String.valueOf(endTime - startTime)) + " ms", 0, 0);
            setOutputMergeSort();
        }

        private void mergeSortForkJoin() {
            ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
            long startTime = System.currentTimeMillis();
            forkJoinPool.invoke(new AlgorithmForkJoin(data));
            long endTime = System.currentTimeMillis();
            model.setValueAt(Double.parseDouble(String.valueOf(endTime - startTime)) + " ms", 0, 1);
            setOutputMergeSort();
        }

        private void mergeSortExecuteService() {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
            long startTime = System.currentTimeMillis();
            executorService.execute(new AlgorithmExecutorService(data));
            long endTime = System.currentTimeMillis();
            model.setValueAt(Double.parseDouble(String.valueOf(endTime - startTime)) + " ms", 0, 2);
            setOutputMergeSort();
        }

        private void setOutputMergeSort() {
            textAreaOutputMergeSort.setText("");
            for (int i = 0; i < data.length; i++) {
                if (i == 0) textAreaOutputMergeSort.append("[ " + data[i] + ", ");
                else if (i == data.length - 1) textAreaOutputMergeSort.append(data[i] + " ]");
                else textAreaOutputMergeSort.append(data[i] + ", ");
            }
        }

    }
}

class AlgorithmSimple {

    public static void mergeSort(int[] data) {
        if (data.length > 1) {
            int[] left = leftHalf(data);
            int[] right = rightHalf(data);

            mergeSort(left);
            mergeSort(right);

            merge(data, left, right);
        }
    }

    private static int[] leftHalf(int[] data) {
        int size1 = data.length / 2;
        int[] left = new int[size1];
        System.arraycopy(data, 0, left, 0, size1);
        return left;
    }

    private static int[] rightHalf(int[] data) {
        int size1 = data.length / 2;
        int size2 = data.length - size1;
        int[] right = new int[size2];
        System.arraycopy(data, size1, right, 0, size2);
        return right;
    }

    private static void merge(int[] result, int[] left, int[] right) {
        int i1 = 0;
        int i2 = 0;

        for (int i = 0; i < result.length; i++) {
            if (i2 >= right.length || (i1 < left.length && left[i1] <= right[i2])) {
                result[i] = left[i1];
                i1++;
            } else {
                result[i] = right[i2];
                i2++;
            }
        }
    }
}

// AlgorithmForkJoin more efficient than AlgorithmSimple
class AlgorithmForkJoin extends RecursiveAction {
    private final int[] data;

    public AlgorithmForkJoin(int[] data) {
        this.data = data;
    }

    @Override
    protected void compute() {
        if (data.length < 2) return; // if data.length < 2, then it is already sorted (base case
        int middle = data.length / 2;
        int[] left = leftHalf(data);
        int[] right = rightHalf(data);

        invokeAll(new AlgorithmForkJoin(left), new AlgorithmForkJoin(right));

        merge(data, left, right);
    }

    private int[] leftHalf(int[] data) {
        int size1 = data.length / 2;
        int[] left = new int[size1];
        System.arraycopy(data, 0, left, 0, size1);
        return left;
    }

    private int[] rightHalf(int[] data) {
        int size1 = data.length / 2;
        int size2 = data.length - size1;
        int[] right = new int[size2];
        System.arraycopy(data, size1, right, 0, size2);
        return right;
    }

    private void merge(int[] result, int[] left, int[] right) {
        int i1 = 0;
        int i2 = 0;

        for (int i = 0; i < result.length; i++) {
            if (i2 >= right.length || (i1 < left.length && left[i1] <= right[i2])) {
                result[i] = left[i1];
                i1++;
            } else {
                result[i] = right[i2];
                i2++;
            }
        }
    }
}


class AlgorithmExecutorService implements Runnable {
    private final int[] data;

    public AlgorithmExecutorService(int[] data) {
        this.data = data;
    }

    @Override
    public void run() {
        mergeSort(data);
    }

    private void mergeSort(int[] data) {
        if (data.length > 1) {
            int[] left = leftHalf(data);
            int[] right = rightHalf(data);

            mergeSort(left);
            mergeSort(right);

            merge(data, left, right);
        }
    }

    private int[] leftHalf(int[] data) {
        int size1 = data.length / 2;
        int[] left = new int[size1];
        System.arraycopy(data, 0, left, 0, size1);
        return left;
    }

    private int[] rightHalf(int[] data) {
        int size1 = data.length / 2;
        int size2 = data.length - size1;
        int[] right = new int[size2];
        System.arraycopy(data, size1, right, 0, size2);
        return right;
    }

    private void merge(int[] result, int[] left, int[] right) {
        int i1 = 0;
        int i2 = 0;

        for (int i = 0; i < result.length; i++) {
            if (i2 >= right.length || (i1 < left.length && left[i1] <= right[i2])) {
                result[i] = left[i1];
                i1++;
            } else {
                result[i] = right[i2];
                i2++;
            }
        }
    }
}
package classes._02_07_23.matrix_with_threads;

import java.util.Random;

public class DemoMatrix extends Thread {
    int[][] originalMatrix;
    int[][] multipliedMatrix;

    private DemoMatrix(int[][] originalMatrix, int[][] multipliedMatrix) {
        this.originalMatrix = originalMatrix;
        this.multipliedMatrix = multipliedMatrix;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix.length; j++) {
                if (multipliedMatrix[i][j] != originalMatrix[i][j] * 2) {
                    multipliedMatrix[i][j] = (originalMatrix[i][j] * 2);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] originalMatrix = new int[3][3];
        int[][] multipliedMatrix = new int[3][3];
        fillMatrix(originalMatrix);
        fillMatrix(multipliedMatrix);
        printMatrix(originalMatrix);
        Thread firstThread = new DemoMatrix(originalMatrix, multipliedMatrix);
        Thread secondThread = new DemoMatrix(originalMatrix, multipliedMatrix);
        firstThread.start();
        secondThread.start();
        printMatrix(multipliedMatrix);
    }

    private static void fillMatrix(int[][] matrix) {
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = random.nextInt(1, 11);
            }
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(String.valueOf(matrix[i][j]).concat("\t"));
                if (j == 2) {
                    System.out.println();
                }
            }
        }
    }
}

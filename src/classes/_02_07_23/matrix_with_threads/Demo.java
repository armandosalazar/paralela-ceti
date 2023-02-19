package classes._02_07_23.matrix_with_threads;

import java.util.Random;

public class Demo extends Thread {
    int matriz[][];
    int sleep;

    Demo(int[][] matriz) {
        this.matriz = matriz;
        this.sleep = sleep;
    }

    Demo(int[][] matriz, int sleep) {
        this.matriz = matriz;
        this.sleep = sleep;
    }

    @Override
    public void run() {
        super.run();
        try {
            sleep(sleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                matriz[i][j] = matriz[i][j] * 2;
            }
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.print(String.valueOf(matriz[i][j]).concat(" "));
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        int matriz[][] = new int[3][3];
        Random random = new Random();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                matriz[i][j] = random.nextInt(1, 5);
            }
        }
        Thread thread1 = new Demo(matriz, 0);
        Thread thread2 = new Demo(matriz, 1000);
        thread1.start();
        thread2.start();
    }
}

package classes._02_25_23;

public class ProducerConsumer {

    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Thread producer = new Producer(10, buffer);
        producer.start();

        Thread[] listConsumers = new Thread[10];
        for (int i = 0; i < listConsumers.length; i++) {
            listConsumers[i] = new Consumer(1, buffer);
            listConsumers[i].start();
        }
    }

}

class Buffer {
    private int contents;
    private boolean empty = true;
    private static int id = 0;

    public synchronized void put(int i) throws InterruptedException {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw e;
            }
        }
        contents = i;
        empty = false;
        System.out.println("Producer [ put > " + i + " ]");
        notifyAll();
    }

    public synchronized int get() throws InterruptedException {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw e;
            }
        }
        empty = true;
        notifyAll();
        int value = contents;
        id++;
        System.out.println("\s\sConsumer " + id + " [ get < " + value + " ]");
        return value;
    }
}


class Producer extends Thread {
    private final int number;
    private final Buffer buffer;

    public Producer(int number, Buffer buffer) {
        this.number = number;
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < number; i++) {
            try {
                sleep((int) Math.random() * 100);
            } catch (InterruptedException e) {
                return;
            }

            try {
                buffer.put(i + 1);
            } catch (InterruptedException e) {
                return;
            }

        }
    }
}

class Consumer extends Thread {
    private final int number;
    private final Buffer buffer;

    public Consumer(int number, Buffer buffer) {
        this.number = number;
        this.buffer = buffer;
    }

    public void run() {
        int value;
        for (int i = 0; i < number; i++) {
            try {
                value = buffer.get();
            } catch (InterruptedException e) {
                return;
            }
            try {
                sleep((int) Math.random() * 100);
            } catch (InterruptedException e) {
                return;
            }

        }
    }
}


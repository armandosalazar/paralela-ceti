package classes._02_25_23;

/**
 * Este es un problema de sincronización
 * Debería utilizar un hilo para un productor y varios para los consumidores
 */

public class ProducerConsumer {

    public static void main(String[] args) {
        new Monitor(8);
    }
}

class Monitor {
    private char[] buffer = null;
    private int maxSize = 0;
    private boolean full = false;
    private boolean empty = true;

    public Monitor(int size) {
        this.buffer = new char[size];
    }

    public synchronized void put(char c) throws InterruptedException {
        while (full) {
            wait();
        }
        buffer[++maxSize] = c;
        empty = false;
        full = maxSize >= buffer.length;
        notifyAll();
    }

    public synchronized char get() throws InterruptedException {
        while (empty) {
            wait();
        }
        char c = buffer[--maxSize];
        full = false;
        empty = maxSize == 0;
        notifyAll();
        return c;
    }

}

class Producer extends Thread {

    private Monitor buffer;

    @Override
    public void run() {
        super.run();
    }
}

class Consumer extends Thread {
}

class Product {
    private String name;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
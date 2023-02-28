package classes._02_25_23;

import java.util.ArrayList;

/**
 * Este es un problema de sincronización
 * Debería utilizar un hilo para un productor y varios para los consumidores
 */

public class ProducerConsumer {
    public static void main(String[] args) {
    }
}

class Producer extends Thread {
    private Product[] buffer = new Product[5];

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = new Product("Product ".concat(String.valueOf(i + 1)));
        }
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
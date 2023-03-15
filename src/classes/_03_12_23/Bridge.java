package classes._03_12_23;

import java.util.Random;

// El puente sería el recurso compartido
public class Bridge {
    private static final Mutex mutex = new Mutex();

    public static void main(String[] args) {
        Generator north = new Generator("North", mutex);
        Generator south = new Generator("South", mutex);
        north.start();
        south.start();
    }

}

class Mutex {
    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        // notify();
        notifyAll();
    }
}

class Generator extends Thread {
    private final Mutex mutex;

    Generator(String name, Mutex mutex) {
        super(name);
        this.mutex = mutex;
    }

    @Override
    public void run() {
        // super.run();
        while (true) {
            // System.out.println(new Random().nextInt(1, 6) + this.getName());
            for (int i = 0; i < new Random().nextInt(1, 6); i++) {
                System.out.println("Car from " + this.getName());
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

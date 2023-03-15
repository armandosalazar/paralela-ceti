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

    synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    synchronized void unlock() {
        isLocked = false;
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
        super.run();
        while (true) {
            try {
                mutex.lock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int random = new Random().nextInt(1, 6);
            System.out.println(random + " cars from " + this.getName());
            for (int i = 0; i < random; i++) {
                System.out.println("Car from " + this.getName());
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            mutex.unlock();
            try {
                sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

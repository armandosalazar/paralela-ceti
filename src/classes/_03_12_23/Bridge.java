package classes._03_12_23;

import java.util.Random;

// El puente sería el recurso compartido
public class Bridge {
    private static final Mutex mutex = new Mutex();

    public static void main(String[] args) {
        Generator generator = new Generator(mutex);
        generator.start();
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
    private Mutex mutex;

    Generator(Mutex mutex) {
        this.mutex = mutex;
    }

    @Override
    public void run() {
        super.run();
        // System.out.println(new Random().nextInt(1, 6) + this.getName());
        String[] directions = {"North", "South"};
        while (true) {
            try {
                mutex.lock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int random = new Random().nextInt(1, 6);
            int direction = new Random().nextInt(0, 2);
            System.out.println(random + " cars from " + directions[direction]);
            for (int i = 0; i < random; i++) {
                System.out.println("Car from " + directions[direction]);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            mutex.unlock();
        }
    }
}

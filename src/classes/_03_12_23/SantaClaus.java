package classes._03_12_23;

import java.util.Random;

public class SantaClaus {

    public static void main(String[] args) {
        Mutex mutex = new Mutex();
        Reindeer reindeer = new Reindeer(mutex);
        Santa santa = new Santa(mutex);
        Elf elf = new Elf(mutex);
        reindeer.start();
        santa.start();
        elf.start();
    }

    static class Mutex {
        private boolean isLocked = false;
        int key = 0;

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
}

class Santa extends Thread {

    private final SantaClaus.Mutex mutex;

    Santa(SantaClaus.Mutex mutex) {
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
            if (mutex.key == 9) {
                System.out.println("Santa is working with reindeers");
                mutex.key = 0;
            }
            mutex.unlock();
            try {
                sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Elf extends Thread {
    private final SantaClaus.Mutex mutex;

    Elf(SantaClaus.Mutex mutex) {
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
            if (random == 3) {
                System.out.println("Santa is coming");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 1; i <= random; i++) {
                System.out.println("Elf " + i + " is working");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            mutex.unlock();
            try {
                sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Reindeer extends Thread {
    private final SantaClaus.Mutex mutex;
    private final int NUM_REINDEER = 9;

    public Reindeer(SantaClaus.Mutex mutex) {
        this.mutex = mutex;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 1; i <= NUM_REINDEER; i++) {
                try {
                    mutex.lock();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Reindeer " + i + " is back from vacation");
                mutex.key = i;
                try {
                    mutex.unlock();
                    sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
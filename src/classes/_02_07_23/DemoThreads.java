package classes._02_07_23;

public class DemoThreads extends Thread {
    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                System.out.println("Hola");
                sleep(1000);
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        new DemoThreads().start();
    }
}

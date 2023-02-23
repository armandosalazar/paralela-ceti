package classes._02_15_23;


public class Timer extends Thread {
    private int seconds;
    private int temp;

    @Override
    public void run() {
        super.run();
        while (seconds != 120000) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            seconds += 1000;
            if (temp >= 59000) {
                temp = 0;
            } else {
                temp += 1000;
            }
            System.out.println("00:".concat(String.format("%02d", seconds / 60000)).concat(":").concat(String.format("%02d", temp / 1000)));
        }
    }

    public static void main(String[] args) {
        new Timer().start();
    }
}

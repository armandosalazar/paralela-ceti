package classes._03_12_23;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Graphic {
    public static void main(String[] args) {
        new Window().setVisible(true);
    }
}

class Window extends JFrame {

    Window() {
        setTitle("Bridge");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Panel panel = new Panel();
        add(panel);
    }
}

class Panel extends JPanel {
    JLabel carNorth = new JLabel(new ImageIcon(new ImageIcon("src/classes/_03_12_23/amg.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
    JLabel carSouth = new JLabel(new ImageIcon(new ImageIcon("src/classes/_03_12_23/urus.jpeg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));

    Panel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(carNorth);
        add(carSouth);
        M mutex = new M();
        G north = new G("North", mutex, this);
        G south = new G("South", mutex, this);
        north.start();
        south.start();
    }

    class M {
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

    class G extends Thread {
        private final M mutex;
        private final Panel panel;

        G(String name, M mutex, Panel panel) {
            super(name);
            this.mutex = mutex;
            this.panel = panel;
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
                    //cardNorth = new JLabel(new ImageIcon(new ImageIcon("src/classes/_03_12_23/amg.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
                    //revalidate();
                    //repaint();

                    // carNorth.setLocation(0, carNorth.getX());
                    for (int j = 0; j < 300; j += 10) {
                        carNorth.setLocation(carNorth.getX() + j, carNorth.getY());
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
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
}

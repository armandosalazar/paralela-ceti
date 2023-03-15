package classes._03_12_23;

import javax.swing.*;
import java.util.ArrayList;
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
    ArrayList<JLabel> labels = new ArrayList<>();

    Panel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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
                    JLabel label = new JLabel("Car from " + this.getName());
                    labels.add(label);
                    panel.add(label);
                    panel.revalidate();
                    panel.repaint();

                    for (JLabel lab : labels) {
                        lab.setLocation(lab.getX() - 500, lab.getY());
                        panel.revalidate();
                        panel.repaint();
                    }

                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


                for (JLabel label : labels) {
                    panel.remove(label);
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

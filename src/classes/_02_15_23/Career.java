package classes._02_15_23;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class Career {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Animal> animals = new ArrayList<>();
        Animal animal1 = new Animal("Leopardo", 16.11);
        Animal animal2 = new Animal("Gacela", 26.94);
        Animal animal3 = new Animal("Hiena", 17.77);
        animal1.start();
        animal2.start();
        animal3.start();
        animal1.join();
        animal2.join();
        animal3.join();
        animals.add(animal1);
        animals.add(animal2);
        animals.add(animal3);
//        while (animal1.isAlive() || animal2.isAlive() || animal3.isAlive())
//            continue;
        System.out.println("==== POSITIONS ====");
        animals.sort(Comparator.comparing(Animal::getMtsPerSecond));
        int pos = 1;
        for (Animal animal : animals) {
            System.out.println(String.valueOf(pos).concat("º ").concat(animal.getAnimalName()));
            pos++;
        }

    }
}

class Animal extends Thread {
    private final String name;
    private final double velocity;
    private double mtsPerSecond;


    public Animal(String name, double velocity) {
        this.name = name;
        this.velocity = velocity;
        this.mtsPerSecond = velocity;
    }

    @Override
    public void run() {
        String career = ".";
        super.run();
        while (true) {
            try {
                if (mtsPerSecond >= 700) {
                    break;
                }
                career += ".";
                System.out.println(career.concat(name.concat(" ").concat(String.valueOf(new DecimalFormat("000.0").format(mtsPerSecond)))));
                mtsPerSecond += velocity;
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getAnimalName() {
        return name;
    }

    public double getMtsPerSecond() {
        return mtsPerSecond;
    }

}
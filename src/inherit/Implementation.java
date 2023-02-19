package inherit;

public class Implementation implements Calculator {
    @Override
    public double addition(double a, double b) {
        return a + b;
    }

    @Override
    public double subtraction(double a, double b) {
        return a - b;
    }

    @Override
    public double multiplication(double a, double b) {
        return a * b;
    }

    @Override
    public double pow(double a, double b) {
        return Math.pow(a, b);
    }

    @Override
    public double module(double a, double b) {
        return a % b;
    }
}

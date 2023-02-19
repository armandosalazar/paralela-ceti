package classes._02_07_23.calculator;

import java.util.Scanner;

public class Interface {
    public static void main(String[] args) {
        Calculator calculator = new Implementation();
        Scanner input = new Scanner(System.in);
        int option;
        while (true) {
            System.out.print("=== CALCULATOR ===\n\n".concat(
                    "1] Additions\n2] Subtraction\n3] Multiplication\n4] Pow\n5] Module\n6] Exit\n\nSelect a option: "
            ));
            option = input.nextInt();
            double a, b;
            switch (option) {
                case 1:
                    System.out.print("Enter a number A: ");
                    a = input.nextFloat();
                    System.out.print("Enter a number B: ");
                    b = input.nextFloat();
                    System.out.println("the result is: ".concat(String.valueOf(calculator.addition(a, b))));
                    break;
                case 2: {

                }
                    System.out.print("Enter a number A: ");
                    a = input.nextFloat();
                    System.out.print("Enter a number B: ");
                    b = input.nextFloat();
                    System.out.println("the result is: ".concat(String.valueOf(calculator.subtraction(a, b))));
                    break;
                case 3:
                    System.out.print("Enter a number A: ");
                    a = input.nextFloat();
                    System.out.print("Enter a number B: ");
                    b = input.nextFloat();
                    System.out.println("the result is: ".concat(String.valueOf(calculator.multiplication(a, b))));
                    break;
                case 4:
                    System.out.print("Enter a number A: ");
                    a = input.nextFloat();
                    System.out.print("Enter a number B: ");
                    b = input.nextFloat();
                    System.out.println("the result is: ".concat(String.valueOf(calculator.pow(a, b))));
                    break;
                case 5:
                    System.out.print("Enter a number A: ");
                    a = input.nextFloat();
                    System.out.print("Enter a number B: ");
                    b = input.nextFloat();
                    System.out.println("the result is: ".concat(String.valueOf(calculator.module(a, b))));
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Option not found");
            }
        }
    }
}

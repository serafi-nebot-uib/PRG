package com.sng.exception.factorial;

public class FactorialCalculator {
    public static void main(String[] args) {
        FactorialCalculator factorialCalculator = new FactorialCalculator();
        factorialCalculator.metodoPrincipal();
    }

    private void metodoPrincipal() {
        try {
            System.out.print("INTRODUCE UN NÚMERO: ");
            int num = LT.readInt();
            int factorial = FactorialCalculator.factorial(num);
            if (factorial <= 0) throw new NumberOutOfBounds();
            System.out.println("EL RESULTADO ES: " + factorial);
        } catch (NumberFormatException ignored) {
            System.out.println("DEBES INTRODUCIR UN NUMERO");
        } catch (NumberOutOfBounds ignored) {
            System.out.println("EL FACTORIAL CALCULADO SUPERA EL NUMERO MÁXIMO PERMITIDO");
        } catch (Error e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static int factorial(int n) {
        int result = 1;

        for (int i = n; i > 0; i--)
            result *= i;

        return result;
    }
}
/*Ejercicio 4 – Máximo común divisor (MCD)
Implemente de manera recursiva el algoritmo de Euclides para calcular el MCD de dos
números enteros positivos. Ejemplo: MCD(48, 18) = 6. */

package Practico2;

public class Ej4 {

    // Función recursiva para calcular el MCD usando el algoritmo de Euclides
    public static int calcularMCD(int a, int b) {
        // Caso base: Si b es 0, el MCD es a
        if (b == 0) {
            return a;
        } else {
            // Recursión: Llamar a la función con b y el resto de a dividido entre b
            return calcularMCD(b, a % b);
        }
    }
    public static void main(String[] args) {

        int a = 48;
        int b = 18;

        int mcd = calcularMCD(a, b);

        System.out.println("El MCD de " + a + " y " + b + " es: " + mcd);
    }
}
/*Ejercicio 7 – Números de Fibonacci optimizados
Escriba una función recursiva para calcular el n-ésimo número de Fibonacci.
Discuta con la IA cómo mejorar la eficiencia (por ejemplo, con memoización).*/

package Practico2;

public class Ej7 {

    // Función recursiva para calcular el n-ésimo número de Fibonacci
    public static int fibonacci(int n) {
        // Caso base: Fibonacci(0) = 0 y Fibonacci(1) = 1
        if (n <= 1) {
            return n;
        } else {
            // Llamada recursiva para F(n) = F(n-1) + F(n-2)
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
    public static void main(String[] args) {
        int n = 10;
        System.out.println("El número de Fibonacci en la posición " + n + " es: " + fibonacci(n));
    }
}
    // Mejora de eficiencia con memoización
    /*
    private static int[] memo = new int[100]; // Array para almacenar resultados ya calculados

    public static int fibonacciMemoizado(int n) {
        if (n <= 1) {
            return n;
        }
        if (memo[n] != 0) { // Si ya se calculó, devolver el valor almacenado
            return memo[n];
        }
        memo[n] = fibonacciMemoizado(n - 1) + fibonacciMemoizado(n - 2); // Almacenar el resultado
        return memo[n];
    }
    */
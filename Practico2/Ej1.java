/*Ejercicio 1 – Conteo de dígitos
Escriba una función recursiva que calcule cuántos dígitos
tiene un número entero positivo. Ejemplo: 12345 → 5.*/

package Practico2;

import java.util.Scanner;

public class Ej1 {

    // Función recursiva para contar los dígitos de un número
    public static int contarDigitos(int numero) {
        // Caso base: Si el número es menor que 10, tiene 1 dígito
        if (numero < 10) {
            return 1;
        } else {
            // Recursión: se reduce el número dividiéndolo entre 10
            return 1 + contarDigitos(numero / 10);
        }
    }

   public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ingresa un número: ");
            int numero = scanner.nextInt();

            System.out.println("El número " + numero + " tiene " + contarDigitos(numero) + " dígitos.");
        }
    }
}
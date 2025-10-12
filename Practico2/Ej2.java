/*Ejercicio 2 – Invertir una cadena
Escriba un método recursivo que reciba una cadena y devuelva la misma cadena invertida.
Ejemplo: "recursivo" → "ovisrucer".*/

package Practico2;

import java.util.Scanner;

public class Ej2 {

    // Método recursivo para invertir la cadena
    public static String invertirCadena(String cadena) {
        // Caso base: Si la cadena tiene 0 o 1 caracteres, no se puede invertir
        if (cadena.length() <= 1) {
            return cadena;
        } else {
            // Caso recursivo: Invertir el resto de la cadena y agregar el primer carácter
            // al final
            return invertirCadena(cadena.substring(1)) + cadena.charAt(0);
        }
    }

     public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ingresa una palabra: ");
            String palabra = scanner.nextLine();

            System.out.println("La palabra invertida es: " + invertirCadena(palabra));
        }
    }
}
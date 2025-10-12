/*Ejercicio 6 – Palíndromo
Cree una función recursiva que determine si una cadena es un palíndromo.
Ejemplo: "neuquen" → true, "informatica" → false. */

package Practico2;

public class Ej6 {

    // Método recursivo para verificar si una cadena es un palíndromo
    public static boolean esPalindromo(String cadena) {
        // Caso base: si la longitud es 0 o 1, es un palíndromo
        if (cadena.length() <= 1) {
            return true;
        } else {
            // Compara el primer y último carácter
            if (cadena.charAt(0) == cadena.charAt(cadena.length() - 1)) {
                // Recursión: verificar la subcadena sin el primer y último carácter
                return esPalindromo(cadena.substring(1, cadena.length() - 1));
            } else {
                return false; // Si los caracteres no coinciden, no es un palíndromo
            }
        }
    }
    public static void main(String[] args) {
        String palabra1 = "neuquen";
        String palabra2 = "informatica";

        System.out.println(palabra1 + " es palíndromo: " + esPalindromo(palabra1));
        System.out.println(palabra2 + " es palíndromo: " + esPalindromo(palabra2));
    }

}
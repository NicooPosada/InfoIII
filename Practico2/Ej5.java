/*Ejercicio 5 – Conversión binaria
Escriba un método recursivo que reciba un número entero positivo y devuelva su
representación en binario (como string). Ejemplo: 13 → "1101". */

package Practico2;

public class Ej5 {

    // Método recursivo para convertir un número a su representación binaria
    public static String convertirABinario(int numero) {
        // Caso base: si el número es 0 o 1, la representación binaria es "0" o "1"
        switch (numero) {
            case 0:
                return "0";
            case 1:
                return "1";
            default:
                // Recursión: dividir el número por 2 y concatenar el resto (bit)
                return convertirABinario(numero / 2) + (numero % 2);
        }
    }
    public static void main(String[] args) {

        int numero = 13;

        String binario = convertirABinario(numero);

        System.out.println("El número " + numero + " en binario es: " + binario);
    }

}
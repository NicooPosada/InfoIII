/*Ejercicio 8 – Buscar en un arreglo
Implemente un método recursivo que determine si un número se encuentra dentro de un
arreglo de enteros. Ejemplo: [3, 5, 7, 9], buscar 7 → true.*/

package Practico2;

public class Ej8 {

    // Función recursiva para buscar un número en un arreglo
    public static boolean buscarEnArreglo(int[] arreglo, int numero, int indice) {
        // Caso base: Si hemos recorrido todo el arreglo
        if (indice == arreglo.length) {
            return false; // No se encontró el número
        }

        // Caso recursivo: Si encontramos el número en el índice actual
        if (arreglo[indice] == numero) {
            return true; // El número se encuentra en el arreglo
        }

        // Llamada recursiva con el siguiente índice
        return buscarEnArreglo(arreglo, numero, indice + 1);
    }
    public static void main(String[] args) {
        int[] arreglo = { 3, 5, 7, 9 };
        int numeroBuscado = 7;

        boolean encontrado = buscarEnArreglo(arreglo, numeroBuscado, 0);

        if (encontrado) {
            System.out.println("El número " + numeroBuscado + " se encuentra en el arreglo.");
        } else {
            System.out.println("El número " + numeroBuscado + " no se encuentra en el arreglo.");
        }
    }
}
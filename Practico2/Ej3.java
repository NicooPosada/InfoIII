/*Ejercicio 3 – Suma de elementos de un arreglo Implemente una función recursiva que 
calcule la suma de todos los elementos de un arreglo de enteros. Ejemplo: [2, 4, 6, 8] → 20. 
Extienda la solución para que además devuelva el promedio usando únicamente recursión.*/

package Practico2;

public class Ej3 {

    // Función recursiva para calcular la suma de los elementos de un arreglo
    public static int calcularSuma(int[] arreglo, int indice) {
        // Caso base: Si llegamos al final del arreglo, retornamos 0
        if (indice == arreglo.length) {
            return 0;
        } else {
            // Recursión: suma el elemento actual y llama recursivamente a la siguiente
            // posición
            return arreglo[indice] + calcularSuma(arreglo, indice + 1);
        }
    }

    // Función recursiva para calcular el promedio de los elementos del arreglo
    public static double calcularPromedio(int[] arreglo, int indice, int suma) {
        // Caso base: Si hemos recorrido todo el arreglo, calculamos el promedio
        if (indice == arreglo.length) {
            return (double) suma / arreglo.length;
        } else {
            // Recursión: sumar el elemento actual y continuar con el siguiente
            return calcularPromedio(arreglo, indice + 1, suma + arreglo[indice]);
        }
    }
    public static void main(String[] args) {

        int[] arreglo = { 2, 4, 6, 8 };

        int suma = calcularSuma(arreglo, 0);

        double promedio = calcularPromedio(arreglo, 0, 0);

        System.out.println("La suma de los elementos es: " + suma);
        System.out.println("El promedio de los elementos es: " + promedio);
    }
}
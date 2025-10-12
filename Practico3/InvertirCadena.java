/*  
Ejercicio 3 – Invertir una Cadena con Pila
Usando la clase PilaArreglo, escriba un programa que reciba una cadena y la invierta.
Ejemplo: "Hola" → "aloH". 
 */

package Practico3;

import java.util.Scanner;

// --- Clase PilaArreglo ---
class PilaArreglo {
    private final char[] pila;
    private int tope;

    public PilaArreglo(int capacidad) {
        pila = new char[capacidad];
        tope = -1;
    }

    public void push(char dato) {
        if (!isFull()) {
            pila[++tope] = dato;
        }
    }

    public char pop() {
        if (!isEmpty()) {
            return pila[tope--];
        }
        return '\0';
    }

    public boolean isEmpty() {
        return tope == -1;
    }

    public boolean isFull() {
        return tope == pila.length - 1;
    }
}

// --- Clase principal ---
public class InvertirCadena {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Ingrese una cadena: ");
            String texto = sc.nextLine();

            PilaArreglo pila = new PilaArreglo(texto.length());

            for (int i = 0; i < texto.length(); i++) {
                pila.push(texto.charAt(i));
            }

            String invertida = "";
            while (!pila.isEmpty()) {
                invertida += pila.pop();
            }

            System.out.println("Cadena invertida: " + invertida);
        }
    }
}

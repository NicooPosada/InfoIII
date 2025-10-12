/*
Ejercicio 5 – Palíndromo con Pila y Cola
Un palíndromo es una palabra o frase que se lee igual en ambos sentidos (ej: "radar").
Implemente un programa que determine si una palabra es palíndromo usando:
● Una pila para recorrer de derecha a izquierda.
● Una cola para recorrer de izquierda a derecha.

javac Practico3/Palindromo.java
java Practico3.Palindromo
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
        if (!isFull()) pila[++tope] = dato;
    }

    public char pop() {
        if (!isEmpty()) return pila[tope--];
        return '\0';
    }

    public boolean isEmpty() {
        return tope == -1;
    }

    public boolean isFull() {
        return tope == pila.length - 1;
    }
}

// --- Clase ColaArreglo ---
class ColaArreglo {
    private final char[] cola;
    private int frente;
    private int fin;
    private int cantidad;

    public ColaArreglo(int capacidad) {
        cola = new char[capacidad];
        frente = 0;
        fin = -1;
        cantidad = 0;
    }

    public void enqueue(char dato) {
        if (!isFull()) {
            fin = (fin + 1) % cola.length;
            cola[fin] = dato;
            cantidad++;
        }
    }

    public char dequeue() {
        if (!isEmpty()) {
            char dato = cola[frente];
            frente = (frente + 1) % cola.length;
            cantidad--;
            return dato;
        }
        return '\0';
    }

    public boolean isEmpty() {
        return cantidad == 0;
    }

    public boolean isFull() {
        return cantidad == cola.length;
    }
}

// --- Clase principal ---
public class Palindromo {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Ingrese una palabra: ");
            String palabra = sc.nextLine();

            // Normalizamos: quitamos espacios y pasamos a minúsculas
            palabra = palabra.replaceAll("\\s+", "").toLowerCase();

            PilaArreglo pila = new PilaArreglo(palabra.length());
            ColaArreglo cola = new ColaArreglo(palabra.length());

            // Cargar caracteres en pila y cola
            for (int i = 0; i < palabra.length(); i++) {
                char c = palabra.charAt(i);
                pila.push(c);
                cola.enqueue(c);
            }

            // Comparar ambos recorridos
            boolean esPalindromo = true;
            while (!pila.isEmpty() && !cola.isEmpty()) {
                if (pila.pop() != cola.dequeue()) {
                    esPalindromo = false;
                    break;
                }
            }

            // Mostrar resultado
            if (esPalindromo) {
                System.out.println("\"" + palabra + "\" es un palíndromo.");
            } else {
                System.out.println("\"" + palabra + "\" no es un palíndromo.");
            }
        }
    }
}
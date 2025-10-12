/*
Ejercicio 6 – Deshacer/Rehacer con Pila
Implemente un programa que simule un editor de texto.
● Cada acción del usuario (escribir, borrar, copiar) se guarda en una pila de deshacer.
● Cuando el usuario presiona Deshacer, se pasa la acción a una pila de rehacer.
📌 Simule al menos 5 acciones y muestre cómo cambian las pilas al deshacer y
rehacer.

javac Practico3/EditorSimulado.java
java Practico3.EditorSimulado
 */



package Practico3;

import java.util.Stack;

public class EditorSimulado {
     public static void main(String[] args) {
        // Usamos la clase Stack de Java para simplificar
        Stack<String> deshacer = new Stack<>();
        Stack<String> rehacer = new Stack<>();

        // Simulación de 5 acciones del usuario
        deshacer.push("Escribir: Hola");
        deshacer.push("Escribir: Mundo");
        deshacer.push("Borrar: o");
        deshacer.push("Copiar: Hola");
        deshacer.push("Escribir: Java");

        System.out.println("Pila de deshacer inicial:");
        mostrarPila(deshacer);
        System.out.println("Pila de rehacer inicial:");
        mostrarPila(rehacer);

        // Deshacer 2 acciones
        System.out.println("\nDeshaciendo 2 acciones...");
        for (int i = 0; i < 2; i++) {
            if (!deshacer.isEmpty()) {
                String accion = deshacer.pop();
                rehacer.push(accion);
            }
        }

        System.out.println("Pila de deshacer después de deshacer:");
        mostrarPila(deshacer);
        System.out.println("Pila de rehacer después de deshacer:");
        mostrarPila(rehacer);

        // Rehacer 1 acción
        System.out.println("\nRehaciendo 1 acción...");
        if (!rehacer.isEmpty()) {
            String accion = rehacer.pop();
            deshacer.push(accion);
        }

        System.out.println("Pila de deshacer después de rehacer:");
        mostrarPila(deshacer);
        System.out.println("Pila de rehacer después de rehacer:");
        mostrarPila(rehacer);
    }

    // Método auxiliar para mostrar el contenido de una pila
    public static void mostrarPila(Stack<String> pila) {
        if (pila.isEmpty()) {
            System.out.println("[vacía]");
        } else {
            for (int i = pila.size() - 1; i >= 0; i--) {
                System.out.println(pila.get(i));
            }
        }
    }
}

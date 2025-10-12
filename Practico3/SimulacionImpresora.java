/*
Ejercicio 7 – Simulación de Impresora con Cola
Una impresora recibe documentos en orden de llegada.
● Cada documento tiene un número (ej: Doc1, Doc2, Doc3).
● Se procesan en orden usando una cola.
📌 Simule la llegada de 5 documentos y la impresión de 3 de ellos.
 */


package Practico3;

import java.util.LinkedList;
import java.util.Queue;

public class SimulacionImpresora {
     public static void main(String[] args) {
        // Usamos la interfaz Queue con LinkedList para simular la cola
        Queue<String> colaImpresora = new LinkedList<>();

        // Simular llegada de 5 documentos
        colaImpresora.add("Doc1");
        colaImpresora.add("Doc2");
        colaImpresora.add("Doc3");
        colaImpresora.add("Doc4");
        colaImpresora.add("Doc5");

        System.out.println("Cola inicial de documentos:");
        mostrarCola(colaImpresora);

        // Procesar (imprimir) 3 documentos
        System.out.println("\nImprimiendo 3 documentos...");
        for (int i = 0; i < 3; i++) {
            if (!colaImpresora.isEmpty()) {
                String doc = colaImpresora.poll(); // saca el primero
                System.out.println("Impreso: " + doc);
            }
        }

        System.out.println("\nCola restante después de imprimir:");
        mostrarCola(colaImpresora);
    }

    // Método auxiliar para mostrar la cola
    public static void mostrarCola(Queue<String> cola) {
        if (cola.isEmpty()) {
            System.out.println("[vacía]");
        } else {
            for (String doc : cola) {
                System.out.print(doc + " ");
            }
            System.out.println();
        }
    }
}

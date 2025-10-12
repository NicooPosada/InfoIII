/*
Ejercicio 8 – Cola Circular para Gestión de Llamadas
Implemente una cola circular para gestionar llamadas en un call center.
● La cola tiene capacidad máxima de 5 llamadas.
● Cuando llega una nueva llamada y la cola está llena, sobrescribe la más antigua.
📌 Simule la llegada de 8 llamadas y muestre el estado final de la cola.
 */

package Practico3;

public class ColaCircularLlamadas {
     public static void main(String[] args) {
        ColaCircular cola = new ColaCircular(5);

        // Simular la llegada de 8 llamadas
        for (int i = 1; i <= 8; i++) {
            cola.enqueue("Llamada" + i);
        }

        System.out.println("Estado final de la cola de llamadas:");
        cola.mostrarCola();
    }
}

// Clase ColaCircular con sobrescritura
class ColaCircular {
    private final String[] cola;
    private int frente;
    private int fin;
    private int cantidad;
    private final int capacidad;

    public ColaCircular(int capacidad) {
        this.capacidad = capacidad;
        cola = new String[capacidad];
        frente = 0;
        fin = -1;
        cantidad = 0;
    }

    public void enqueue(String llamada) {
        // Si la cola está llena, sobrescribimos la más antigua
        if (cantidad == capacidad) {
            // Sobrescribimos el frente
            cola[frente] = llamada;
            frente = (frente + 1) % capacidad; // avanzamos frente
            fin = (fin + 1) % capacidad;       // avanzamos fin
            System.out.println("Cola llena, sobrescribiendo la llamada más antigua");
        } else {
            fin = (fin + 1) % capacidad;
            cola[fin] = llamada;
            cantidad++;
        }
    }

    public void mostrarCola() {
        if (cantidad == 0) {
            System.out.println("[vacía]");
            return;
        }

        for (int i = 0; i < cantidad; i++) {
            int indice = (frente + i) % capacidad;
            System.out.print(cola[indice] + " ");
        }
        System.out.println();
    }
}

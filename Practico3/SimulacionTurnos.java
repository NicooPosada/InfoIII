package Practico3;

public class SimulacionTurnos {
    
    // --- Clase ColaArreglo ---
static class ColaArreglo {
    private final String[] cola;
    private int frente;
    private int fin;
    private int cantidad;

    public ColaArreglo(int capacidad) {
        cola = new String[capacidad];
        frente = 0;
        fin = -1;
        cantidad = 0;
    }

    public void enqueue(String dato) {
        if (!isFull()) {
            fin = (fin + 1) % cola.length;
            cola[fin] = dato;
            cantidad++;
        } else {
            System.out.println("La cola está llena.");
        }
    }

    public String dequeue() {
        if (!isEmpty()) {
            String dato = cola[frente];
            frente = (frente + 1) % cola.length;
            cantidad--;
            return dato;
        } else {
            System.out.println("La cola está vacía.");
            return null;
        }
    }

    public boolean isEmpty() {
        return cantidad == 0;
    }

    public boolean isFull() {
        return cantidad == cola.length;
    }

    public void mostrarCola() {
        if (isEmpty()) {
            System.out.println("La cola está vacía.");
        } else {
            System.out.print("Fila de espera: ");
            for (int i = 0; i < cantidad; i++) {
                int indice = (frente + i) % cola.length;
                System.out.print(cola[indice] + " ");
            }
            System.out.println();
        }
    }
}

// --- Clase principal ---
public static void main(String[] args) {
    ColaArreglo cola = new ColaArreglo(5);

    // Llegan los clientes
    cola.enqueue("Ana");
    cola.enqueue("Luis");
    cola.enqueue("Marta");
    cola.enqueue("Pedro");

    System.out.println("Fila inicial:");
    cola.mostrarCola();

    // Atendemos a los dos primeros
    System.out.println("\nAtendiendo a los clientes...");
    System.out.println("Atendido: " + cola.dequeue());
    System.out.println("Atendido: " + cola.dequeue());

    // Mostrar la fila actual
    System.out.println("\nFila después de atender:");
    cola.mostrarCola();
}

}

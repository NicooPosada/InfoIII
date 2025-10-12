/* 
Ejercicio 2 – Implementación de Cola
Implemente una clase ColaArreglo en Java utilizando un arreglo.
Métodos: enqueue(int dato), dequeue(), top(), isEmpty(), isFull().
Pruebe encolando los enteros 1, 2, 3, 4 y desencolando uno.
 */

package Practico3;

public class ColaArreglo {
    
    private final int[] cola;     // Arreglo para guardar los elementos
    private int frente;     // Índice del primer elemento
    private int fin;        // Índice del último elemento
    private int cantidad;   // Cantidad actual de elementos

    // Constructor
    public ColaArreglo(int capacidad) {
        cola = new int[capacidad];
        frente = 0;
        fin = -1;
        cantidad = 0;
    }

    // Encolar (agregar al final)
    public void enqueue(int dato) {
        if (isFull()) {
            System.out.println("La cola está llena. No se puede encolar " + dato);
        } else {
            fin = (fin + 1) % cola.length; // Avanza circularmente
            cola[fin] = dato;
            cantidad++;
            System.out.println("Se encoló: " + dato);
        }
    }

    // Desencolar (quitar del frente)
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("La cola está vacía. No se puede desencolar.");
            return -1;
        } else {
            int dato = cola[frente];
            frente = (frente + 1) % cola.length; // Avanza circularmente
            cantidad--;
            System.out.println("Se desencoló: " + dato);
            return dato;
        }
    }

    // Ver el primer elemento (sin quitarlo)
    public int top() {
        if (isEmpty()) {
            System.out.println("La cola está vacía.");
            return -1;
        } else {
            return cola[frente];
        }
    }

    // Verificar si está vacía
    public boolean isEmpty() {
        return cantidad == 0;
    }

    // Verificar si está llena
    public boolean isFull() {
        return cantidad == cola.length;
    }

    // Mostrar contenido de la cola (opcional)
    public void mostrarCola() {
        if (isEmpty()) {
            System.out.println("La cola está vacía.");
        } else {
            System.out.print("Contenido de la cola: ");
            for (int i = 0; i < cantidad; i++) {
                int indice = (frente + i) % cola.length;
                System.out.print(cola[indice] + " ");
            }
            System.out.println();
        }
    }

    // Método main para probar
    public static void main(String[] args) {
        ColaArreglo cola = new ColaArreglo(5);

        cola.enqueue(1);
        cola.enqueue(2);
        cola.enqueue(3);
        cola.enqueue(4);
        cola.mostrarCola();

        cola.dequeue();
        cola.mostrarCola();

        System.out.println("Frente actual: " + cola.top());
    }
}

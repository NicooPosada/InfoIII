package Practico3;

public class Ej1 {
    
    private final int[] pila;   // Arreglo donde guardamos los datos
    private int tope;     // Índice del elemento superior de la pila

    // Constructor: inicializa el arreglo con una capacidad fija
    public Ej1(int capacidad) {
        pila = new int[capacidad];
        tope = -1; // -1 significa que la pila está vacía
    }

    // Agregar un elemento a la pila
    public void push(int dato) {
        if (isFull()) {
            System.out.println("La pila está llena. No se puede apilar " + dato);
        } else {
            pila[++tope] = dato;
            System.out.println("Se apiló: " + dato);
        }
    }

    // Quitar el elemento superior de la pila
    public int pop() {
        if (isEmpty()) {
            System.out.println("La pila está vacía. No se puede desapilar.");
            return -1;
        } else {
            int dato = pila[tope--];
            System.out.println("Se desapiló: " + dato);
            return dato;
        }
    }

    // Ver el elemento superior sin quitarlo
    public int top() {
        if (isEmpty()) {
            System.out.println("La pila está vacía.");
            return -1;
        } else {
            return pila[tope];
        }
    }

    // Verifica si la pila está vacía
    public boolean isEmpty() {
        return tope == -1;
    }

    // Verifica si la pila está llena
    public boolean isFull() {
        return tope == pila.length - 1;
    }

    // Mostrar la pila (opcional para probar)
    public void mostrarPila() {
        if (isEmpty()) {
            System.out.println("La pila está vacía.");
        } else {
            System.out.print("Contenido de la pila: ");
            for (int i = 0; i <= tope; i++) {
                System.out.print(pila[i] + " ");
            }
            System.out.println();
        }
    }

    // Método main para probar
    public static void main(String[] args) {
        Ej1 pila = new Ej1(5);

        pila.push(10);
        pila.push(20);
        pila.push(30);
        pila.push(40);
        pila.mostrarPila();

        pila.pop();
        pila.pop();
        pila.mostrarPila();

        System.out.println("Tope actual: " + pila.top());
    }
}


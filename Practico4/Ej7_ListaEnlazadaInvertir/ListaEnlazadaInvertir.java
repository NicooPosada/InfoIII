/*
Ejercicio 7 – Invertir la lista
Escribe un método invertir() que invierta el orden de los nodos en la lista.
● Ejemplo: [10 -> 20 -> 30 -> 40] debe transformarse en [40 -> 30 -> 20
-> 10].
 */

package Practico4.Ej7_ListaEnlazadaInvertir;

// Clase Nodo
class Nodo {
    int dato;
    Nodo siguiente;

    public Nodo(int dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}

// Clase ListaEnlazada
class ListaEnlazada {
    Nodo cabeza;

    public ListaEnlazada() {
        cabeza = null;
    }

    // Insertar al final
    public void insertarFinal(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    // Invertir la lista
    public void invertir() {
        Nodo prev = null;
        Nodo actual = cabeza;
        Nodo siguiente;

        while (actual != null) {
            siguiente = actual.siguiente; // guardar siguiente nodo
            actual.siguiente = prev;      // invertir el enlace
            prev = actual;                // mover prev
            actual = siguiente;           // avanzar al siguiente nodo
        }

        cabeza = prev; // actualizar cabeza
    }

    // Imprimir lista
    public void imprimirLista() {
        Nodo actual = cabeza;
        System.out.print("Lista: ");
        while (actual != null) {
            System.out.print(actual.dato + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }
}

// Clase principal
public class ListaEnlazadaInvertir {
    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();

        // Insertar valores [10 -> 20 -> 30 -> 40]
        lista.insertarFinal(10);
        lista.insertarFinal(20);
        lista.insertarFinal(30);
        lista.insertarFinal(40);

        System.out.println("Lista original:");
        lista.imprimirLista();

        // Invertir la lista
        lista.invertir();

        System.out.println("Lista invertida:");
        lista.imprimirLista();
    }
}


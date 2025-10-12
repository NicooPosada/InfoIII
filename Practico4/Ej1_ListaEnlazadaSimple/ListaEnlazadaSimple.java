/*
Ejercicio 1 – Crear un nodo
Escribe una clase Nodo que almacene un número entero y un puntero al siguiente nodo.
● Implementa un programa que cree tres nodos y los enlace manualmente.
● Imprime la lista completa.
 */

package Practico4.Ej1_ListaEnlazadaSimple;

// Clase Nodo
class Nodo {
    int dato;
    Nodo siguiente;

    public Nodo(int dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}

// Clase principal
public class ListaEnlazadaSimple {
    public static void main(String[] args) {
        // Crear tres nodos
        Nodo nodo1 = new Nodo(10);
        Nodo nodo2 = new Nodo(20);
        Nodo nodo3 = new Nodo(30);

        // Enlazar manualmente
        nodo1.siguiente = nodo2;
        nodo2.siguiente = nodo3;
        nodo3.siguiente = null; // último nodo apunta a null

        // Imprimir la lista completa
        Nodo actual = nodo1; // empezamos desde el primer nodo
        System.out.println("Lista enlazada completa:");
        while (actual != null) {
            System.out.print(actual.dato + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }
}

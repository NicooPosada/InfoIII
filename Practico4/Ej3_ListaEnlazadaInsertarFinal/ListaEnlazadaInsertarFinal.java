/*
Ejercicio 3 – Insertar al final
Agrega a la clase ListaEnlazada el método insertarFinal(int dato).
● Inserta los valores 1, 2, 3.
● Imprime la lista y verifica que se agregan en orden.
 */

package Practico4.Ej3_ListaEnlazadaInsertarFinal;

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
    Nodo cabeza; // referencia al primer nodo

    public ListaEnlazada() {
        cabeza = null;
    }

    // Método para insertar al inicio (ya lo teníamos)
    public void insertarInicio(int dato) {
        Nodo nuevo = new Nodo(dato);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
    }

    // Método para insertar al final
    public void insertarFinal(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevo; // lista vacía, el nuevo nodo es la cabeza
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente; // avanzar hasta el último nodo
            }
            actual.siguiente = nuevo; // enlazar el nuevo nodo al final
        }
    }

    // Método para imprimir la lista
    public void imprimirLista() {
        Nodo actual = cabeza;
        System.out.println("Lista enlazada:");
        while (actual != null) {
            System.out.print(actual.dato + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }
}

// Clase principal
public class ListaEnlazadaInsertarFinal {
    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();

        // Insertar valores al final
        lista.insertarFinal(1);
        lista.insertarFinal(2);
        lista.insertarFinal(3);

        // Imprimir lista para verificar orden
        lista.imprimirLista();
    }
}

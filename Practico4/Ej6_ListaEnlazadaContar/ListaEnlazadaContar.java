/*
Ejercicio 6 – Contar elementos
Implementa el método contar() que devuelva la cantidad de nodos en la lista.
● Para [1 -> 2 -> 3 -> 4 -> 5], el resultado debe ser 5.
 */

package Practico4.Ej6_ListaEnlazadaContar;

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

    // Contar cantidad de nodos
    public int contar() {
        int contador = 0;
        Nodo actual = cabeza;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }
        return contador;
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
public class ListaEnlazadaContar {
    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();

        // Insertar valores [1 -> 2 -> 3 -> 4 -> 5]
        lista.insertarFinal(1);
        lista.insertarFinal(2);
        lista.insertarFinal(3);
        lista.insertarFinal(4);
        lista.insertarFinal(5);

        // Imprimir lista
        lista.imprimirLista();

        // Contar elementos
        System.out.println("Cantidad de elementos en la lista: " + lista.contar());
    }
}


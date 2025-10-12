/*
Ejercicio 5 – Buscar un valor
Crea un método buscar(int valor) que recorra la lista y devuelva true si encuentra el
nodo.
● Prueba con la lista [5 -> 15 -> 25 -> 35].
● Busca el 25 (debe devolver true) y el 100 (debe devolver false).
 */


package Practico4.Ej5_ListaEnlazadaBuscar;

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

    // Buscar un valor en la lista
    public boolean buscar(int valor) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato == valor) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
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
public class ListaEnlazadaBuscar {
    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();

        // Insertar valores [5 -> 15 -> 25 -> 35]
        lista.insertarFinal(5);
        lista.insertarFinal(15);
        lista.insertarFinal(25);
        lista.insertarFinal(35);

        System.out.println("Lista completa:");
        lista.imprimirLista();

        // Buscar valores
        int valor1 = 25;
        int valor2 = 100;

        System.out.println("¿Se encuentra " + valor1 + "? " + lista.buscar(valor1)); // true
        System.out.println("¿Se encuentra " + valor2 + "? " + lista.buscar(valor2)); // false
    }
}

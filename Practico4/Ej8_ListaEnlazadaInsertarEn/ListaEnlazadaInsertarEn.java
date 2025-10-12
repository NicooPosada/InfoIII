/*
Ejercicio 8 – Insertar en posición
Crea un método insertarEn(int pos, int valor) que inserte un nodo en la posición
indicada (0 = inicio).
● Ejemplo: en [1 -> 2 -> 4], al insertar 3 en la posición 2, debe quedar [1 -> 2
-> 3 -> 4].
 */


package Practico4.Ej8_ListaEnlazadaInsertarEn;

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

    // Insertar en posición (0 = inicio)
    public void insertarEn(int pos, int valor) {
        Nodo nuevo = new Nodo(valor);

        if (pos <= 0 || cabeza == null) { // insertar al inicio
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
            return;
        }

        Nodo actual = cabeza;
        int contador = 0;

        // Avanzar hasta la posición anterior
        while (actual.siguiente != null && contador < pos - 1) {
            actual = actual.siguiente;
            contador++;
        }

        // Insertar el nodo
        nuevo.siguiente = actual.siguiente;
        actual.siguiente = nuevo;
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
public class ListaEnlazadaInsertarEn {
    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();

        // Crear lista [1 -> 2 -> 4]
        lista.insertarFinal(1);
        lista.insertarFinal(2);
        lista.insertarFinal(4);

        System.out.println("Lista original:");
        lista.imprimirLista();

        // Insertar 3 en la posición 2
        lista.insertarEn(2, 3);

        System.out.println("Lista después de insertar 3 en posición 2:");
        lista.imprimirLista();
    }
}


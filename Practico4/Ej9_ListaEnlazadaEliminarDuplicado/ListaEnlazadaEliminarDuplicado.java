/*
Ejercicio 9 – Eliminar duplicados
Implementa un método eliminarDuplicados() que recorra la lista y elimine los nodos
repetidos.
● Ejemplo: [1 -> 2 -> 2 -> 3 -> 1] debe quedar [1 -> 2 -> 3].
 */

package Practico4.Ej9_ListaEnlazadaEliminarDuplicado;

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

    // Eliminar duplicados
    public void eliminarDuplicados() {
        Nodo actual = cabeza;

        while (actual != null) {
            Nodo runner = actual;
            // Revisar los nodos siguientes
            while (runner.siguiente != null) {
                if (runner.siguiente.dato == actual.dato) {
                    // Saltar nodo duplicado
                    runner.siguiente = runner.siguiente.siguiente;
                } else {
                    runner = runner.siguiente;
                }
            }
            actual = actual.siguiente;
        }
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
public class ListaEnlazadaEliminarDuplicado {
    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();

        // Crear lista [1 -> 2 -> 2 -> 3 -> 1]
        lista.insertarFinal(1);
        lista.insertarFinal(2);
        lista.insertarFinal(2);
        lista.insertarFinal(3);
        lista.insertarFinal(1);

        System.out.println("Lista original:");
        lista.imprimirLista();

        // Eliminar duplicados
        lista.eliminarDuplicados();

        System.out.println("Lista después de eliminar duplicados:");
        lista.imprimirLista();
    }
}


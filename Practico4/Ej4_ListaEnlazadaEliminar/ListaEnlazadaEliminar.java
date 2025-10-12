/*
Ejercicio 4 – Eliminar por valor
Implementa el método eliminar(int valor) que elimine el primer nodo que contenga
ese valor.
● Prueba con la lista [10 -> 20 -> 30 -> 40] eliminando el 30.
● Verifica el resultado: [10 -> 20 -> 40].
 */

package Practico4.Ej4_ListaEnlazadaEliminar;

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

    // Insertar al final para simplificar la prueba
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

    // Método para eliminar un nodo por valor (el primero que encuentre)
    public void eliminar(int valor) {
        if (cabeza == null) return; // lista vacía

        // Si el valor está en la cabeza
        if (cabeza.dato == valor) {
            cabeza = cabeza.siguiente;
            return;
        }

        Nodo actual = cabeza;
        while (actual.siguiente != null && actual.siguiente.dato != valor) {
            actual = actual.siguiente;
        }

        // Si se encontró el valor
        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
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
public class ListaEnlazadaEliminar {
    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();

        // Insertar valores [10 -> 20 -> 30 -> 40]
        lista.insertarFinal(10);
        lista.insertarFinal(20);
        lista.insertarFinal(30);
        lista.insertarFinal(40);

        System.out.println("Lista inicial:");
        lista.imprimirLista();

        // Eliminar el valor 30
        lista.eliminar(30);

        System.out.println("Lista después de eliminar 30:");
        lista.imprimirLista();
    }
}

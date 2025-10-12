/*
Ejercicio 2 – Insertar al inicio
Implementa el método insertarInicio(int dato) en la clase ListaEnlazada.
● Prueba insertando los valores: 10, 20, 30.
● Imprime la lista para verificar que el orden sea correcto.
 */

package Practico4.Ej2_ListaEnlazadaInsertarInicio;

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

    // Método para insertar un nodo al inicio
    public void insertarInicio(int dato) {
        Nodo nuevo = new Nodo(dato);
        nuevo.siguiente = cabeza; // el nuevo nodo apunta al actual primer nodo
        cabeza = nuevo;           // la cabeza ahora es el nuevo nodo
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
public class ListaEnlazadaInsertarInicio {
    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();

        // Insertar valores al inicio
        lista.insertarInicio(10);
        lista.insertarInicio(20);
        lista.insertarInicio(30);

        // Imprimir lista para verificar orden
        lista.imprimirLista();
    }
}

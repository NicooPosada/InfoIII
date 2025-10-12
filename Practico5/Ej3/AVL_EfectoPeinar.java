/*
3. Secuencia ordenada y “efecto peinar”
Inserte 5, 10, 15, 20, 25, 30, 35 en un ABB y luego balancee hasta AVL (o
inserte directamente en AVL, mostrando reequilibrios).
a) Explique por qué un ABB puro se desbalancea con datos crecientes.
b) Detalle las rotaciones que hacen que el AVL mantenga altura O(log n).
 */

package Practico5.Ej3;

// --- Nodo del árbol ---
class NodoAVL {
    int valor;
    NodoAVL izq, der;
    int altura;

    public NodoAVL(int valor) {
        this.valor = valor;
        this.altura = 1; // cada nuevo nodo tiene altura 1
    }
}

// --- Clase Árbol AVL ---
class ArbolAVL {
    private NodoAVL raiz;

    // --- Obtener altura de un nodo ---
    private int altura(NodoAVL n) {
        return (n == null) ? 0 : n.altura;
    }

    // --- Calcular Factor de Equilibrio (FE) ---
    private int obtenerFE(NodoAVL n) {
        return (n == null) ? 0 : altura(n.izq) - altura(n.der);
    }

    // --- Rotación Simple a la Derecha (LL) ---
    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izq;
        NodoAVL T2 = x.der;

        // rotar
        x.der = y;
        y.izq = T2;

        // actualizar alturas
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;

        return x;
    }

    // --- Rotación Simple a la Izquierda (RR) ---
    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.der;
        NodoAVL T2 = y.izq;

        // rotar
        y.izq = x;
        x.der = T2;

        // actualizar alturas
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;

        return y;
    }

    // --- Insertar nodo en el árbol AVL ---
    public NodoAVL insertar(NodoAVL nodo, int valor) {
        // Inserción normal de ABB
        if (nodo == null)
            return new NodoAVL(valor);

        if (valor < nodo.valor)
            nodo.izq = insertar(nodo.izq, valor);
        else if (valor > nodo.valor)
            nodo.der = insertar(nodo.der, valor);
        else
            return nodo; // duplicados no permitidos

        // Actualizar altura
        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));

        // Calcular FE
        int fe = obtenerFE(nodo);

        // --- Casos de desbalance ---
        // Caso LL
        if (fe > 1 && valor < nodo.izq.valor)
            return rotacionDerecha(nodo);

        // Caso RR
        if (fe < -1 && valor > nodo.der.valor)
            return rotacionIzquierda(nodo);

        // Caso LR
        if (fe > 1 && valor > nodo.izq.valor) {
            nodo.izq = rotacionIzquierda(nodo.izq);
            return rotacionDerecha(nodo);
        }

        // Caso RL
        if (fe < -1 && valor < nodo.der.valor) {
            nodo.der = rotacionDerecha(nodo.der);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    // Método auxiliar público
    public void insertar(int valor) {
        raiz = insertar(raiz, valor);
    }

    // --- Recorrido In-Orden (muestra en orden ascendente) ---
    public void inOrden() {
        inOrdenRec(raiz);
        System.out.println();
    }

    private void inOrdenRec(NodoAVL nodo) {
        if (nodo != null) {
            inOrdenRec(nodo.izq);
            System.out.print(nodo.valor + " ");
            inOrdenRec(nodo.der);
        }
    }

    // --- Mostrar árbol con indentación ---
    public void mostrarArbol() {
        mostrarRec(raiz, 0);
    }

    private void mostrarRec(NodoAVL nodo, int nivel) {
        if (nodo != null) {
            mostrarRec(nodo.der, nivel + 1);
            System.out.println(" ".repeat(nivel * 4) + nodo.valor + " (h=" + nodo.altura + ")");
            mostrarRec(nodo.izq, nivel + 1);
        }
    }
}

// --- Clase principal ---
public class AVL_EfectoPeinar {
    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();

        int[] valores = {5, 10, 15, 20, 25, 30, 35};

        System.out.println("=== Inserciones AVL con reequilibrios ===\n");
        for (int v : valores) {
            arbol.insertar(v);
            System.out.println("Después de insertar " + v + ":");
            arbol.mostrarArbol();
            System.out.println("-------------------------");
        }

        System.out.println("\nRecorrido in-orden final (orden ascendente):");
        arbol.inOrden();
    }
}

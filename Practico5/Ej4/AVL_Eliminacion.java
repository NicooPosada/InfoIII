/*
4. Eliminación con rebalanceo
Dado el AVL resultante de insertar 50, 30, 70, 20, 40, 60, 80, 65, 75,
elimine: 20, luego 70.
a) Dibuje el árbol tras cada borrado.
b) Indique FE de los nodos afectados y rotaciones necesarias para restaurar el
balance.
 */

package Practico5.Ej4;

// --- Nodo del árbol AVL ---
class NodoAVL {
    int valor;
    NodoAVL izq, der;
    int altura;

    public NodoAVL(int valor) {
        this.valor = valor;
        this.altura = 1;
    }
}

// --- Clase Árbol AVL ---
class ArbolAVL {
    private NodoAVL raiz;

    // --- Obtener altura ---
    private int altura(NodoAVL n) {
        return (n == null) ? 0 : n.altura;
    }

    // --- Calcular factor de equilibrio ---
    private int obtenerFE(NodoAVL n) {
        return (n == null) ? 0 : altura(n.izq) - altura(n.der);
    }

    // --- Rotaciones ---
    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izq;
        NodoAVL T2 = x.der;
        x.der = y;
        y.izq = T2;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        return x;
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.der;
        NodoAVL T2 = y.izq;
        y.izq = x;
        x.der = T2;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        return y;
    }

    // --- Inserción con rebalanceo ---
    private NodoAVL insertar(NodoAVL nodo, int valor) {
        if (nodo == null) return new NodoAVL(valor);
        if (valor < nodo.valor)
            nodo.izq = insertar(nodo.izq, valor);
        else if (valor > nodo.valor)
            nodo.der = insertar(nodo.der, valor);
        else
            return nodo; // duplicados no permitidos

        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
        return balancear(nodo, valor);
    }

    private NodoAVL balancear(NodoAVL nodo, int valor) {
        int fe = obtenerFE(nodo);

        // Casos de desbalance
        if (fe > 1 && valor < nodo.izq.valor) // LL
            return rotacionDerecha(nodo);
        if (fe < -1 && valor > nodo.der.valor) // RR
            return rotacionIzquierda(nodo);
        if (fe > 1 && valor > nodo.izq.valor) { // LR
            nodo.izq = rotacionIzquierda(nodo.izq);
            return rotacionDerecha(nodo);
        }
        if (fe < -1 && valor < nodo.der.valor) { // RL
            nodo.der = rotacionDerecha(nodo.der);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    // --- Eliminar nodo y rebalancear ---
    private NodoAVL eliminar(NodoAVL nodo, int valor) {
        if (nodo == null)
            return nodo;

        // Eliminación normal tipo ABB
        if (valor < nodo.valor)
            nodo.izq = eliminar(nodo.izq, valor);
        else if (valor > nodo.valor)
            nodo.der = eliminar(nodo.der, valor);
        else {
            // Nodo con uno o cero hijos
            if ((nodo.izq == null) || (nodo.der == null)) {
                NodoAVL temp = (nodo.izq != null) ? nodo.izq : nodo.der;
                if (temp == null)
                    nodo = null;
                else
                    nodo = temp;
            } else {
                // Nodo con dos hijos: buscar sucesor (menor en subárbol derecho)
                NodoAVL temp = nodoMinimo(nodo.der);
                nodo.valor = temp.valor;
                nodo.der = eliminar(nodo.der, temp.valor);
            }
        }

        // Si el árbol quedó vacío
        if (nodo == null)
            return nodo;

        // Actualizar altura
        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));

        // Obtener FE y rebalancear si es necesario
        int fe = obtenerFE(nodo);

        // Casos de desbalance en eliminación
        if (fe > 1 && obtenerFE(nodo.izq) >= 0) // LL
            return rotacionDerecha(nodo);
        if (fe > 1 && obtenerFE(nodo.izq) < 0) { // LR
            nodo.izq = rotacionIzquierda(nodo.izq);
            return rotacionDerecha(nodo);
        }
        if (fe < -1 && obtenerFE(nodo.der) <= 0) // RR
            return rotacionIzquierda(nodo);
        if (fe < -1 && obtenerFE(nodo.der) > 0) { // RL
            nodo.der = rotacionDerecha(nodo.der);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL nodoMinimo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.izq != null)
            actual = actual.izq;
        return actual;
    }

    // --- Métodos públicos ---
    public void insertar(int valor) {
        raiz = insertar(raiz, valor);
    }

    public void eliminar(int valor) {
        raiz = eliminar(raiz, valor);
    }

    // --- Mostrar árbol ---
    public void mostrarArbol() {
        mostrarRec(raiz, 0);
    }

    private void mostrarRec(NodoAVL nodo, int nivel) {
        if (nodo != null) {
            mostrarRec(nodo.der, nivel + 1);
            System.out.println(" ".repeat(nivel * 4) + nodo.valor + " (FE=" + obtenerFE(nodo) + ")");
            mostrarRec(nodo.izq, nivel + 1);
        }
    }
}

// --- Clase principal ---
public class AVL_Eliminacion {
    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();
        int[] valores = {50, 30, 70, 20, 40, 60, 80, 65, 75};

        System.out.println("=== Construcción inicial del árbol AVL ===");
        for (int v : valores)
            arbol.insertar(v);

        arbol.mostrarArbol();
        System.out.println("-------------------------");

        // --- Eliminar 20 ---
        System.out.println("Después de eliminar 20:");
        arbol.eliminar(20);
        arbol.mostrarArbol();
        System.out.println("-------------------------");

        // --- Eliminar 70 ---
        System.out.println("Después de eliminar 70:");
        arbol.eliminar(70);
        arbol.mostrarArbol();
        System.out.println("-------------------------");
    }
}

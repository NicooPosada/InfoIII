/*
PRÁCTICO ÁRBOL AVL
1. Inserciones y FE paso a paso (caso LL y RR)
Inserte en un AVL la secuencia: 30, 20, 10, 40, 50, 60.
a) Dibuje el árbol tras cada inserción.
b) Calcule alturas y factor de equilibrio (FE) de cada nodo en cada paso.
c) Indique qué rotación se aplica en cada desbalance (LL o RR) y por qué.
 */

package Practico5.Ej1;

class NodoAVL {
    int valor;
    int altura;
    NodoAVL izq, der;

    public NodoAVL(int valor) {
        this.valor = valor;
        this.altura = 1;
    }
}

public class ArbolAVL {
    private NodoAVL raiz;

    // --- Altura ---
    private int altura(NodoAVL n) {
        return (n == null) ? 0 : n.altura;
    }

    // --- Factor de equilibrio ---
    private int factorEquilibrio(NodoAVL n) {
        if (n == null) return 0;
        return altura(n.izq) - altura(n.der);
    }

    // --- Rotación derecha (LL) ---
    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izq;
        NodoAVL T2 = x.der;

        // Rotación
        x.der = y;
        y.izq = T2;

        // Actualizar alturas
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;

        System.out.println("Rotación Derecha (LL) sobre " + y.valor);
        return x;
    }

    // --- Rotación izquierda (RR) ---
    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.der;
        NodoAVL T2 = y.izq;

        // Rotación
        y.izq = x;
        x.der = T2;

        // Actualizar alturas
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;

        System.out.println("Rotación Izquierda (RR) sobre " + x.valor);
        return y;
    }

    // --- Inserción con balanceo ---
    private NodoAVL insertar(NodoAVL nodo, int valor) {
        if (nodo == null)
            return new NodoAVL(valor);

        if (valor < nodo.valor)
            nodo.izq = insertar(nodo.izq, valor);
        else if (valor > nodo.valor)
            nodo.der = insertar(nodo.der, valor);
        else
            return nodo; // no duplicados

        // Actualizar altura
        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));

        int fe = factorEquilibrio(nodo);

        // --- Casos de rotación ---
        // LL
        if (fe > 1 && valor < nodo.izq.valor)
            return rotacionDerecha(nodo);

        // RR
        if (fe < -1 && valor > nodo.der.valor)
            return rotacionIzquierda(nodo);

        // LR
        if (fe > 1 && valor > nodo.izq.valor) {
            nodo.izq = rotacionIzquierda(nodo.izq);
            return rotacionDerecha(nodo);
        }

        // RL
        if (fe < -1 && valor < nodo.der.valor) {
            nodo.der = rotacionDerecha(nodo.der);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    public void insertar(int valor) {
        raiz = insertar(raiz, valor);
        System.out.println("\nÁrbol tras insertar " + valor + ":");
        mostrarArbol(raiz, "", true);
        System.out.println("---------------------------\n");
    }

    // --- Mostrar árbol en consola (formato jerárquico) ---
    private void mostrarArbol(NodoAVL nodo, String prefijo, boolean esDerecho) {
        if (nodo != null) {
            System.out.println(prefijo + (esDerecho ? "└── " : "├── ") + nodo.valor +
                    " (h=" + nodo.altura + ", FE=" + factorEquilibrio(nodo) + ")");
            mostrarArbol(nodo.izq, prefijo + (esDerecho ? "    " : "│   "), false);
            mostrarArbol(nodo.der, prefijo + (esDerecho ? "    " : "│   "), true);
        }
    }

    // --- Main para probar la secuencia ---
    public static void main(String[] args) {
        ArbolAVL avl = new ArbolAVL();

        int[] valores = {30, 20, 10, 40, 50, 60};
        for (int v : valores) {
            avl.insertar(v);
        }
    }
}

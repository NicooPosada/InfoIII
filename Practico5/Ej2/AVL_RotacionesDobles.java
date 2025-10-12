/*
2. Inserciones con rotación doble (caso LR y RL)
Inserte la secuencia: 30, 10, 20, 40, 35, 37.
a) Muestre el estado del árbol en cada paso.
b) Identifique los desbalances (FE = ±2).
c) Especifique cuándo corresponde rotación doble (LR o RL) y ejecútela.
 */

package Practico5.Ej2;

class NodoAVL {
    int valor, altura;
    NodoAVL izquierdo, derecho;

    public NodoAVL(int valor) {
        this.valor = valor;
        this.altura = 1;
    }
}

class ArbolAVL {
    NodoAVL raiz;

    int altura(NodoAVL n) {
        return (n == null) ? 0 : n.altura;
    }

    int factorEquilibrio(NodoAVL n) {
        return (n == null) ? 0 : altura(n.izquierdo) - altura(n.derecho);
    }

    // Rotación derecha
    NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;

        return x;
    }

    // Rotación izquierda
    NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;

        return y;
    }

    // Inserción con balanceo
    NodoAVL insertar(NodoAVL nodo, int valor) {
        if (nodo == null)
            return new NodoAVL(valor);

        if (valor < nodo.valor)
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        else if (valor > nodo.valor)
            nodo.derecho = insertar(nodo.derecho, valor);
        else
            return nodo; // sin duplicados

        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));

        int fe = factorEquilibrio(nodo);

        // Caso LL
        if (fe > 1 && valor < nodo.izquierdo.valor)
            return rotacionDerecha(nodo);

        // Caso RR
        if (fe < -1 && valor > nodo.derecho.valor)
            return rotacionIzquierda(nodo);

        // Caso LR
        if (fe > 1 && valor > nodo.izquierdo.valor) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        // Caso RL
        if (fe < -1 && valor < nodo.derecho.valor) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    void insertar(int valor) {
        raiz = insertar(raiz, valor);
        System.out.println("Insertado: " + valor);
        imprimirArbol();
        System.out.println("---------------------------");
    }

    // Mostrar el árbol (rotado)
    void imprimirArbol() {
        imprimirArbol(raiz, "", true);
    }

    void imprimirArbol(NodoAVL nodo, String prefijo, boolean esDerecho) {
        if (nodo != null) {
            System.out.println(prefijo + (esDerecho ? "└── " : "├── ") + nodo.valor +
                    " (h=" + nodo.altura + ", FE=" + factorEquilibrio(nodo) + ")");
            imprimirArbol(nodo.izquierdo, prefijo + (esDerecho ? "    " : "│   "), false);
            imprimirArbol(nodo.derecho, prefijo + (esDerecho ? "    " : "│   "), true);
        }
    }
}

public class AVL_RotacionesDobles {
    public static void main(String[] args) {
        ArbolAVL avl = new ArbolAVL();

        int[] valores = {30, 10, 20, 40, 35, 37};
        for (int v : valores)
            avl.insertar(v);
    }
}

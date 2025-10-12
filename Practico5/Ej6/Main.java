/*
6. Factor de equilibrio completo
Inserte 10, 100, 20, 80, 40, 70 (o una variante equivalente) y:
a) Liste para el árbol final (valor, altura, FE) de todos los nodos.
b) Marque los nodos críticos donde surgieron FE = ±2 durante el proceso.
 */

package Practico5.Ej6;

class Nodo {
    int dato, altura;
    Nodo izq, der;

    public Nodo(int dato) {
        this.dato = dato;
        this.altura = 0;
    }
}

class ArbolAVL {
    Nodo raiz;

    int altura(Nodo n) {
        return (n == null) ? -1 : n.altura;
    }

    int factorEquilibrio(Nodo n) {
        return (n == null) ? 0 : altura(n.izq) - altura(n.der);
    }

    Nodo rotacionDerecha(Nodo y) {
        Nodo x = y.izq;
        Nodo T2 = x.der;
        x.der = y;
        y.izq = T2;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        return x;
    }

    Nodo rotacionIzquierda(Nodo x) {
        Nodo y = x.der;
        Nodo T2 = y.izq;
        y.izq = x;
        x.der = T2;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        return y;
    }

    Nodo insertar(Nodo nodo, int valor) {
        if (nodo == null)
            return new Nodo(valor);

        if (valor < nodo.dato)
            nodo.izq = insertar(nodo.izq, valor);
        else if (valor > nodo.dato)
            nodo.der = insertar(nodo.der, valor);
        else
            return nodo;

        nodo.altura = Math.max(altura(nodo.izq), altura(nodo.der)) + 1;

        int fe = factorEquilibrio(nodo);

        // Casos de rotación
        if (fe > 1 && valor < nodo.izq.dato)
            return rotacionDerecha(nodo); // LL
        if (fe < -1 && valor > nodo.der.dato)
            return rotacionIzquierda(nodo); // RR
        if (fe > 1 && valor > nodo.izq.dato) {
            nodo.izq = rotacionIzquierda(nodo.izq); // LR
            return rotacionDerecha(nodo);
        }
        if (fe < -1 && valor < nodo.der.dato) {
            nodo.der = rotacionDerecha(nodo.der); // RL
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    void mostrarFactores(Nodo nodo) {
        if (nodo != null) {
            mostrarFactores(nodo.izq);
            System.out.println("Nodo " + nodo.dato + " | Altura: " + nodo.altura +
                               " | FE: " + factorEquilibrio(nodo));
            mostrarFactores(nodo.der);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ArbolAVL avl = new ArbolAVL();
        int[] valores = {10, 100, 20, 80, 40, 70};

        for (int v : valores) {
            avl.raiz = avl.insertar(avl.raiz, v);
            System.out.println("Insertado: " + v);
        }

        System.out.println("\n--- Factores de Equilibrio ---");
        avl.mostrarFactores(avl.raiz);
    }
}

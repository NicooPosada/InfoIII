/*
5. Comprobador de AVL
Implemente un método esAVL(Nodo r) que:
○ Devuelva (esAVL, altura) en una sola pasada recursiva.
○ Verifique que para todo nodo |altura(izq) - altura(der)| ≤ 1 y que
además respete la propiedad de ABB.
Pruebe con árboles válidos e inválidos pequeños.
 */

package Practico5.Ej5;

class Nodo {
    int dato;
    Nodo izq, der;

    public Nodo(int dato) {
        this.dato = dato;
        this.izq = this.der = null;
    }
}

class ResultadoAVL {
    boolean esAVL;
    int altura;

    public ResultadoAVL(boolean esAVL, int altura) {
        this.esAVL = esAVL;
        this.altura = altura;
    }
}

class ArbolAVL {

    Nodo raiz;

    public ArbolAVL() {
        raiz = null;
    }

    // ---------- Método principal ----------
    public ResultadoAVL esAVL(Nodo nodo) {
        // Árbol vacío = AVL válido con altura -1
        if (nodo == null)
            return new ResultadoAVL(true, -1);

        // Revisar subárbol izquierdo y derecho
        ResultadoAVL izq = esAVL(nodo.izq);
        ResultadoAVL der = esAVL(nodo.der);

        // Verificar propiedad de ABB
        boolean esABB = true;
        if (nodo.izq != null && nodo.izq.dato >= nodo.dato)
            esABB = false;
        if (nodo.der != null && nodo.der.dato <= nodo.dato)
            esABB = false;

        // Calcular FE (Factor de equilibrio)
        int fe = izq.altura - der.altura;

        // Verificar balance y propiedades
        boolean balanceado = Math.abs(fe) <= 1;

        boolean esAVL = izq.esAVL && der.esAVL && balanceado && esABB;
        int altura = Math.max(izq.altura, der.altura) + 1;

        return new ResultadoAVL(esAVL, altura);
    }

    // Insertar como ABB normal
    public Nodo insertar(Nodo nodo, int valor) {
        if (nodo == null) return new Nodo(valor);
        if (valor < nodo.dato)
            nodo.izq = insertar(nodo.izq, valor);
        else if (valor > nodo.dato)
            nodo.der = insertar(nodo.der, valor);
        return nodo;
    }

    // Mostrar árbol inorden
    public void inorden(Nodo nodo) {
        if (nodo != null) {
            inorden(nodo.izq);
            System.out.print(nodo.dato + " ");
            inorden(nodo.der);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();

        // Ejemplo 1: Árbol AVL válido
        int[] valores1 = {30, 20, 40, 10, 25, 35, 50};
        for (int v : valores1)
            arbol.raiz = arbol.insertar(arbol.raiz, v);

        System.out.print("Árbol inorden: ");
        arbol.inorden(arbol.raiz);
        System.out.println();

        ResultadoAVL res1 = arbol.esAVL(arbol.raiz);
        System.out.println("¿Es AVL? " + res1.esAVL + " | Altura: " + res1.altura);

        // Ejemplo 2: Árbol NO AVL (desbalanceado)
        ArbolAVL arbol2 = new ArbolAVL();
        int[] valores2 = {10, 5, 3, 2, 1}; // Insertados de forma creciente → desbalanceado
        for (int v : valores2)
            arbol2.raiz = arbol2.insertar(arbol2.raiz, v);

        System.out.print("Árbol inorden: ");
        arbol2.inorden(arbol2.raiz);
        System.out.println();

        ResultadoAVL res2 = arbol2.esAVL(arbol2.raiz);
        System.out.println("¿Es AVL? " + res2.esAVL + " | Altura: " + res2.altura);
    }
}

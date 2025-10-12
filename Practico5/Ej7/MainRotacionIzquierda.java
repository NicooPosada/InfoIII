/*
7. Implementación guiada: rotación izquierda
Complete el código de una rotación simple a izquierda y úselo en insertar.
a) Muestre antes/después sobre un subárbol donde ocurra caso RR.
b) Actualice correctamente las alturas involucradas.

(Pueden partir de un esqueleto de NodoAVL{ valor, altura, izq, der }.)
 */

package Practico5.Ej7;

// Archivo: MainRotacionIzquierda.java

// Asegúrate de que la clase ArbolAVL esté definida en este archivo o importada correctamente.
// Si está en otro archivo en el mismo paquete, no necesitas importarla.
// Si está en otro paquete, usa: import <paquete>.ArbolAVL;

public class MainRotacionIzquierda {
    public static void main(String[] args) {
        ArbolAVL avl = new ArbolAVL();

        // Insertamos datos que producen caso RR (10, 20, 30)
        avl.raiz = avl.insertarSinBalanceo(avl.raiz, 10);
        avl.raiz = avl.insertarSinBalanceo(avl.raiz, 20);
        avl.raiz = avl.insertarSinBalanceo(avl.raiz, 30);

        System.out.println("🔹 Árbol antes de rotación (caso RR):");
        avl.imprimirEstructura(avl.raiz, "", true);

        // Aplicar rotación a izquierda en la raíz (10)
        avl.raiz = avl.rotacionIzquierda(avl.raiz);

        System.out.println("\nÁrbol después de rotación izquierda:");
        avl.imprimirEstructura(avl.raiz, "", true);
    }
}

// Si no tienes la clase ArbolAVL, aquí tienes una implementación mínima para evitar el error de compilación:
class ArbolAVL {
    class Nodo {
        int valor;
        Nodo izq, der;
        Nodo(int v) { valor = v; }
    }
    Nodo raiz;

    Nodo insertarSinBalanceo(Nodo nodo, int valor) {
        if (nodo == null) return new Nodo(valor);
        if (valor < nodo.valor) nodo.izq = insertarSinBalanceo(nodo.izq, valor);
        else nodo.der = insertarSinBalanceo(nodo.der, valor);
        return nodo;
    }

    Nodo rotacionIzquierda(Nodo x) {
        if (x == null || x.der == null) return x;
        Nodo y = x.der;
        x.der = y.izq;
        y.izq = x;
        return y;
    }

    void imprimirEstructura(Nodo nodo, String prefijo, boolean esUltimo) {
        if (nodo == null) return;
        System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + nodo.valor);
        imprimirEstructura(nodo.izq, prefijo + (esUltimo ? "    " : "│   "), false);
        imprimirEstructura(nodo.der, prefijo + (esUltimo ? "    " : "│   "), true);
    }
}
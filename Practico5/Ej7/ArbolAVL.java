package Practico5.Ej7;

// Archivo: ArbolAVL.java
class ArbolAVL {
    NodoAVL raiz;

    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();
        arbol.demoRotacionIzquierda();
    }

    int altura(NodoAVL n) {
        return (n == null) ? -1 : n.altura;
    }

    // Método de prueba para demostrar el uso de rotacionIzquierda
    void demoRotacionIzquierda() {
        // Insertar nodos para crear un caso RR
        raiz = null;
        raiz = insertarSinBalanceo(raiz, 10);
        raiz = insertarSinBalanceo(raiz, 20);
        raiz = insertarSinBalanceo(raiz, 30);

        System.out.println("Antes de la rotación izquierda:");
        imprimirEstructura(raiz, "", true);

        // Aplicar rotación izquierda manualmente
        raiz = rotacionIzquierda(raiz);

        System.out.println("Después de la rotación izquierda:");
        imprimirEstructura(raiz, "", true);

        // Mostrar el árbol en orden para demostrar el uso de imprimirInorden
        System.out.print("Impresión inorden: ");
        imprimirInorden(raiz);
        System.out.println();
    }
    
    // Definición de la clase NodoAVL
    class NodoAVL {
        int valor;
        NodoAVL izq, der;
        int altura;
    
        NodoAVL(int valor) {
            this.valor = valor;
            this.izq = null;
            this.der = null;
            this.altura = 0;
        }
    }

    // 🔹 Rotación simple a la izquierda (caso RR)
    NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.der;     // y será la nueva raíz del subárbol
        NodoAVL T2 = y.izq;    // subárbol izquierdo de y

        // Rotación
        y.izq = x;
        x.der = T2;

        // Actualizar alturas
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;

        return y; // nueva raíz del subárbol
    }

    // 🔹 Inserta sin balancear, para mostrar manualmente el caso RR
    NodoAVL insertarSinBalanceo(NodoAVL nodo, int valor) {
        if (nodo == null)
            return new NodoAVL(valor);

        if (valor < nodo.valor)
            nodo.izq = insertarSinBalanceo(nodo.izq, valor);
        else if (valor > nodo.valor)
            nodo.der = insertarSinBalanceo(nodo.der, valor);

        nodo.altura = Math.max(altura(nodo.izq), altura(nodo.der)) + 1;
        return nodo;
    }

    // Mostrar árbol en orden (inorden)
    void imprimirInorden(NodoAVL nodo) {
        if (nodo != null) {
            imprimirInorden(nodo.izq);
            System.out.print(nodo.valor + " ");
            imprimirInorden(nodo.der);
        }
    }

    void imprimirEstructura(NodoAVL nodo, String prefijo, boolean esDerecha) {
        if (nodo != null) {
            System.out.println(prefijo + (esDerecha ? "└── " : "├── ") + nodo.valor);
            imprimirEstructura(nodo.izq, prefijo + (esDerecha ? "    " : "│   "), false);
            imprimirEstructura(nodo.der, prefijo + (esDerecha ? "    " : "│   "), true);
        }
    }
}
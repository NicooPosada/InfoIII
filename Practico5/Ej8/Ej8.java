/*
 * 8. Implementación guiada: rotación doble izquierda-derecha (LR)
Programe rotacionDobleIzquierdaDerecha(n) usando dos rotaciones
simples.
a) Justifique por qué LR ≡ (rotación simple izquierda en hijo) + (rotación simple
derecha en n).
b) Valide con el caso del ejercicio 2.
 */

package Practico5.Ej8;

// Clase que representa un nodo del árbol AVL
class NodoAVL8 {
    int valor;
    NodoAVL8 izquierdo;
    NodoAVL8 derecho;
    int altura;

    public NodoAVL8(int valor) {
        this.valor = valor;
        this.altura = 1;
        this.izquierdo = null;
        this.derecho = null;
    }
}

// Clase principal del árbol AVL con rotación doble LR
class ArbolAVL8 {
    NodoAVL8 raiz;

    public ArbolAVL8() {
        this.raiz = null;
    }

    // Obtener la altura de un nodo
    private int altura(NodoAVL8 nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    // Calcular el factor de balance
    private int factorBalance(NodoAVL8 nodo) {
        return (nodo == null) ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    // Actualizar la altura de un nodo
    private void actualizarAltura(NodoAVL8 nodo) {
        if (nodo != null) {
            nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
        }
    }

    /**
     * ROTACIÓN SIMPLE DERECHA (Right Rotation)
     * 
     * Antes: n Después: x
     * / \ / \
     * x C A n
     * / \ / \
     * A B B C
     */
    private NodoAVL8 rotacionDerecha(NodoAVL8 n) {
        System.out.println(" Ejecutando rotación DERECHA en nodo " + n.valor);

        NodoAVL8 x = n.izquierdo;
        NodoAVL8 B = x.derecho;

        // Realizar rotación
        x.derecho = n;
        n.izquierdo = B;

        // Actualizar alturas
        actualizarAltura(n);
        actualizarAltura(x);

        return x; // Nueva raíz del subárbol
    }

    /**
     * ROTACIÓN SIMPLE IZQUIERDA (Left Rotation)
     * 
     * Antes: n Después: y
     * / \ / \
     * A y n C
     * / \ / \
     * B C A B
     */
    private NodoAVL8 rotacionIzquierda(NodoAVL8 n) {
        System.out.println(" Ejecutando rotación IZQUIERDA en nodo " + n.valor);

        NodoAVL8 y = n.derecho;
        NodoAVL8 B = y.izquierdo;

        // Realizar rotación
        y.izquierdo = n;
        n.derecho = B;

        // Actualizar alturas
        actualizarAltura(n);
        actualizarAltura(y);

        return y; // Nueva raíz del subárbol
    }

    /**
     * ROTACIÓN DOBLE IZQUIERDA-DERECHA (LR - Left-Right Rotation)
     * 
     * JUSTIFICACIÓN:
     * Cuando tenemos una configuración LR (hijo izquierdo tiene subárbol derecho
     * pesado),
     * necesitamos dos rotaciones simples para balancear:
     * 
     * 1. Primero, rotación IZQUIERDA en el hijo izquierdo (x)
     * Esto convierte la configuración LR en LL
     * 
     * 2. Luego, rotación DERECHA en el nodo padre (n)
     * Esto balancea el árbol
     * 
     * PASO A PASO:
     * 
     * Estado inicial (LR):
     * n FB(n) = +2
     * / \ FB(x) = -1
     * x D (desequilibrio LR)
     * / \
     * A y
     * / \
     * B C
     * 
     * Después de rotación IZQUIERDA en x:
     * n FB(n) = +2
     * / \ FB(y) = +1
     * y D (desequilibrio LL)
     * / \
     * x C
     * / \
     * A B
     * 
     * Después de rotación DERECHA en n:
     * y Balanceado!
     * / \
     * x n
     * / \ / \
     * A B C D
     * 
     * @param n nodo con desequilibrio LR
     * @return nueva raíz del subárbol balanceado
     */
    public NodoAVL8 rotacionDobleIzquierdaDerecha(NodoAVL8 n) {
        System.out.println("\n ROTACIÓN DOBLE IZQUIERDA-DERECHA (LR) en nodo " + n.valor);
        System.out.println(" Configuración: hijo izquierdo tiene subárbol derecho pesado");

        // Paso 1: Rotación IZQUIERDA en el hijo izquierdo
        System.out.println("\n  Paso 1: Rotación IZQUIERDA en hijo izquierdo (" + n.izquierdo.valor + ")");
        n.izquierdo = rotacionIzquierda(n.izquierdo);
        System.out.println(" Configuración LR convertida a LL");
        mostrarSubarbol(n, " Después del paso 1");

        // Paso 2: Rotación DERECHA en el nodo padre
        System.out.println("\n  Paso 2: Rotación DERECHA en nodo padre (" + n.valor + ")");
        NodoAVL8 resultado = rotacionDerecha(n);
        System.out.println(" Árbol balanceado");
        mostrarSubarbol(resultado, " Después del paso 2");

        return resultado;
    }

    /**
     * Inserción AVL con balanceo automático
     */
    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private NodoAVL8 insertarRecursivo(NodoAVL8 nodo, int valor) {
        // Inserción BST normal
        if (nodo == null) {
            return new NodoAVL8(valor);
        }

        if (valor < nodo.valor) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = insertarRecursivo(nodo.derecho, valor);
        } else {
            return nodo; // Valor duplicado
        }

        // Actualizar altura
        actualizarAltura(nodo);

        // Obtener factor de balance
        int balance = factorBalance(nodo);

        // Caso LR: Izquierdo-Derecho
        if (balance > 1 && factorBalance(nodo.izquierdo) < 0) {
            System.out.println("\n Desequilibrio LR detectado en nodo " + nodo.valor);
            return rotacionDobleIzquierdaDerecha(nodo);
        }

        // Caso LL: Izquierdo-Izquierdo
        if (balance > 1 && factorBalance(nodo.izquierdo) >= 0) {
            System.out.println("\n Desequilibrio LL detectado en nodo " + nodo.valor);
            return rotacionDerecha(nodo);
        }

        // Caso RR: Derecho-Derecho
        if (balance < -1 && factorBalance(nodo.derecho) <= 0) {
            System.out.println("\n Desequilibrio RR detectado en nodo " + nodo.valor);
            return rotacionIzquierda(nodo);
        }

        // Caso RL: Derecho-Izquierdo
        if (balance < -1 && factorBalance(nodo.derecho) > 0) {
            System.out.println("\n Desequilibrio RL detectado en nodo " + nodo.valor);
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    // Método para mostrar el árbol
    public void mostrarArbol() {
        System.out.println("\n Estructura del árbol:");
        mostrarArbol(raiz, "", true);
    }

    private void mostrarArbol(NodoAVL8 nodo, String prefijo, boolean esUltimo) {
        if (nodo != null) {
            System.out.println(prefijo + (esUltimo ? "└── " : "├── ") +
                    nodo.valor + " (h=" + nodo.altura + ", FB=" + factorBalance(nodo) + ")");

            if (nodo.izquierdo != null || nodo.derecho != null) {
                mostrarArbol(nodo.izquierdo, prefijo + (esUltimo ? "    " : "│   "), false);
                mostrarArbol(nodo.derecho, prefijo + (esUltimo ? "    " : "│   "), true);
            }
        }
    }

    // Método auxiliar para mostrar un subárbol
    private void mostrarSubarbol(NodoAVL8 nodo, String titulo) {
        System.out.println(titulo + ":");
        mostrarArbol(nodo, "      ", true);
    }

    // Recorrido en orden
    public void inorden() {
        System.out.print("Recorrido en orden: ");
        inordenRecursivo(raiz);
        System.out.println();
    }

    private void inordenRecursivo(NodoAVL8 nodo) {
        if (nodo != null) {
            inordenRecursivo(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            inordenRecursivo(nodo.derecho);
        }
    }
}

public class Ej8 {
    public static void main(String[] args) {
        System.out.println("=== ROTACIÓN DOBLE IZQUIERDA-DERECHA (LR) ===\n");

        // DEMOSTRACIÓN 1: Caso clásico LR
        System.out.println("--- DEMOSTRACIÓN 1: Caso clásico LR ---");
        ArbolAVL8 arbol1 = new ArbolAVL8();

        System.out.println("\nInsertando: 30");
        arbol1.insertar(30);
        arbol1.mostrarArbol();

        System.out.println("\nInsertando: 10");
        arbol1.insertar(10);
        arbol1.mostrarArbol();

        System.out.println("\nInsertando: 20 (causará desequilibrio LR)");
        arbol1.insertar(20);
        arbol1.mostrarArbol();
        arbol1.inorden();

        // DEMOSTRACIÓN 2: Validación del ejercicio 2
        System.out.println("\n\n--- DEMOSTRACIÓN 2: Validación del ejercicio 2 ---");
        System.out.println("Secuencia de inserción: 1, 2, 3, 4, 5, 6, 7");
        ArbolAVL8 arbol2 = new ArbolAVL8();

        int[] valores = { 1, 2, 3, 4, 5, 6, 7 };
        for (int val : valores) {
            System.out.println("\n>>> Insertando: " + val);
            arbol2.insertar(val);
            arbol2.mostrarArbol();
        }

        System.out.println("\n=== ÁRBOL FINAL ===");
        arbol2.mostrarArbol();
        arbol2.inorden();

        // DEMOSTRACIÓN 3: Comparación de rotaciones
        System.out.println("\n\n--- DEMOSTRACIÓN 3: Comparación LR vs LL ---");

        // Caso LR
        System.out.println("\nCaso LR (30, 10, 20):");
        ArbolAVL8 arbolLR = new ArbolAVL8();
        arbolLR.insertar(30);
        arbolLR.insertar(10);
        System.out.println("\nAntes de insertar 20:");
        arbolLR.mostrarArbol();
        arbolLR.insertar(20);
        System.out.println("\nDespués de insertar 20 (con rotación LR):");
        arbolLR.mostrarArbol();

        // Caso LL
        System.out.println("\n\nCaso LL (30, 20, 10):");
        ArbolAVL8 arbolLL = new ArbolAVL8();
        arbolLL.insertar(30);
        arbolLL.insertar(20);
        System.out.println("\nAntes de insertar 10:");
        arbolLL.mostrarArbol();
        arbolLL.insertar(10);
        System.out.println("\nDespués de insertar 10 (con rotación LL):");
        arbolLL.mostrarArbol();

        // JUSTIFICACIÓN TEÓRICA
        System.out.println("\n\n=== JUSTIFICACIÓN TEÓRICA ===");
        System.out.println("\n¿Por qué LR ≡ Rotación Izquierda(hijo) + Rotación Derecha(padre)?");
        System.out.println("\n1. PROBLEMA INICIAL:");
        System.out.println("   En una configuración LR, el nodo problemático está en el subárbol");
        System.out.println("   DERECHO del hijo IZQUIERDO. Esto crea una forma de 'codo' o 'zigzag'.");
        System.out.println("\n2. PASO 1 - Rotación Izquierda en hijo:");
        System.out.println("   Esta rotación 'endereza' el codo, convirtiendo la configuración LR");
        System.out.println("   en una configuración LL (línea recta hacia la izquierda).");
        System.out.println("\n3. PASO 2 - Rotación Derecha en padre:");
        System.out.println("   Ahora que tenemos una configuración LL, una simple rotación");
        System.out.println("   derecha balancea completamente el árbol.");
        System.out.println("\n4. RESULTADO:");
        System.out.println("   El nodo que estaba en el subárbol derecho del hijo izquierdo");
        System.out.println("   ahora se convierte en la raíz del subárbol balanceado.");
        System.out.println("\nEsto mantiene las propiedades BST y AVL:");
        System.out.println("   ✓ Orden BST preservado");
        System.out.println("   ✓ Factor de balance en rango [-1, 1]");
        System.out.println("   ✓ Altura minimizada");

        // DEMOSTRACIÓN 4: Caso complejo
        System.out.println("\n\n--- DEMOSTRACIÓN 4: Múltiples rotaciones LR ---");
        ArbolAVL8 arbol3 = new ArbolAVL8();
        int[] valoresComplejos = { 50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 35 };

        for (int val : valoresComplejos) {
            System.out.println("\n>>> Insertando: " + val);
            arbol3.insertar(val);
        }

        System.out.println("\n=== ÁRBOL FINAL COMPLEJO ===");
        arbol3.mostrarArbol();
        arbol3.inorden();
    }
}
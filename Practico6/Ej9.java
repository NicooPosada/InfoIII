/*
 * 9) Consulta por rango [a,b]
Hacé un in-order acotado para devolver claves en el rango [a,b] en orden, evitando visitar
fuera del intervalo.
Objetivo: devolver claves en orden con a ≤ key ≤ b.
Tarea: in-order acotado: si key < a ir a derecha, si key > b ir a izquierda, si está en
rango agregar y recorrer ambos lados.
 */

package Practico6;

import java.util.ArrayList;
import java.util.List;

// Clase que representa un nodo del árbol
class RBNode9<K, V> {
    K key;
    @SuppressWarnings("unused")
    V value;
    @SuppressWarnings("unused")
    boolean color; // true = ROJO, false = NEGRO
    RBNode9<K, V> left;
    RBNode9<K, V> right;
    @SuppressWarnings("unused")
    RBNode9<K, V> parent;

    static final boolean RED = true;
    static final boolean BLACK = false;

    public RBNode9(K key, V value, boolean color, RBNode9<K, V> nil) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = nil;
        this.right = nil;
        this.parent = nil;
    }

    public RBNode9() {
        this.key = null;
        this.value = null;
        this.color = BLACK;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Clase principal del árbol con consulta por rango
class RBTree9<K extends Comparable<K>, V> {
    final RBNode9<K, V> NIL;
    RBNode9<K, V> root;
    public int nodesVisited = 0; // Contador de nodos visitados (público para acceso en pruebas)

    public RBTree9() {
        NIL = new RBNode9<>();
        root = NIL;
    }

    /**
     * Consulta por rango [a, b]
     * Devuelve todas las claves en el rango [a, b] en orden ascendente
     * Optimizado para evitar visitar nodos fuera del intervalo
     * 
     * @param a límite inferior del rango (inclusive)
     * @param b límite superior del rango (inclusive)
     * @return lista de claves en el rango [a, b] en orden
     */
    public List<K> rangeQuery(K a, K b) {
        List<K> result = new ArrayList<>();
        nodesVisited = 0;
        System.out.println("\n=== CONSULTA POR RANGO [" + a + ", " + b + "] ===");
        rangeQueryHelper(root, a, b, result);
        System.out.println("Nodos visitados: " + nodesVisited);
        return result;
    }

    /**
     * Método auxiliar recursivo para la consulta por rango
     * Implementa un in-order acotado
     * 
     * @param node   nodo actual
     * @param a      límite inferior
     * @param b      límite superior
     * @param result lista donde se acumulan los resultados
     */
    private void rangeQueryHelper(RBNode9<K, V> node, K a, K b, List<K> result) {
        if (node == NIL) {
            return;
        }

        nodesVisited++;
        System.out.println(" Visitando nodo: " + node.key);

        int cmpA = node.key.compareTo(a); // comparar con límite inferior
        int cmpB = node.key.compareTo(b); // comparar con límite superior

        // Si node.key > a, puede haber valores en el subárbol izquierdo
        if (cmpA > 0) {
            System.out.println(" → " + node.key + " > " + a + ", explorar izquierda");
            rangeQueryHelper(node.left, a, b, result);
        } else {
            System.out.println(" → " + node.key + " ≤ " + a + ", no explorar izquierda");
        }

        // Si a ≤ node.key ≤ b, agregar a la lista
        if (cmpA >= 0 && cmpB <= 0) {
            System.out.println(" ✓ " + node.key + " está en el rango [" + a + ", " + b + "]");
            result.add(node.key);
        } else {
            System.out.println(" ✗ " + node.key + " NO está en el rango");
        }

        // Si node.key < b, puede haber valores en el subárbol derecho
        if (cmpB < 0) {
            System.out.println(" → " + node.key + " < " + b + ", explorar derecha");
            rangeQueryHelper(node.right, a, b, result);
        } else {
            System.out.println(" → " + node.key + " ≥ " + b + ", no explorar derecha");
        }
    }

    /**
     * Versión simplificada sin mensajes de debug
     */
    public List<K> rangeQuerySimple(K a, K b) {
        List<K> result = new ArrayList<>();
        rangeQuerySimpleHelper(root, a, b, result);
        return result;
    }

    private void rangeQuerySimpleHelper(RBNode9<K, V> node, K a, K b, List<K> result) {
        if (node == NIL) {
            return;
        }

        // Si node.key > a, explorar izquierda
        if (node.key.compareTo(a) > 0) {
            rangeQuerySimpleHelper(node.left, a, b, result);
        }

        // Si está en el rango, agregarlo
        if (node.key.compareTo(a) >= 0 && node.key.compareTo(b) <= 0) {
            result.add(node.key);
        }

        // Si node.key < b, explorar derecha
        if (node.key.compareTo(b) < 0) {
            rangeQuerySimpleHelper(node.right, a, b, result);
        }
    }

    /**
     * Recorrido in-order completo (para comparación)
     * Visita TODOS los nodos del árbol
     */
    public List<K> inorderComplete() {
        List<K> result = new ArrayList<>();
        nodesVisited = 0;
        inorderCompleteHelper(root, result);
        return result;
    }

    private void inorderCompleteHelper(RBNode9<K, V> node, List<K> result) {
        if (node != NIL) {
            nodesVisited++;
            inorderCompleteHelper(node.left, result);
            result.add(node.key);
            inorderCompleteHelper(node.right, result);
        }
    }

    // Inserción BST simple
    public void insertBST(K key, V value) {
        RBNode9<K, V> newNode = new RBNode9<>(key, value, RBNode9.RED, NIL);

        if (root == NIL) {
            root = newNode;
            root.color = RBNode9.BLACK;
            return;
        }

        RBNode9<K, V> current = root;
        RBNode9<K, V> parent = NIL;

        while (current != NIL) {
            parent = current;
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else if (key.compareTo(current.key) > 0) {
                current = current.right;
            } else {
                current.value = value;
                return;
            }
        }

        newNode.parent = parent;
        if (key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    // Método para visualizar el árbol
    public void printTree() {
        System.out.println("\nEstructura del árbol:");
        printTree(root, "", true);
    }

    private void printTree(RBNode9<K, V> node, String prefix, boolean isTail) {
        if (node != NIL) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.key);

            if (node.left != NIL || node.right != NIL) {
                printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
                printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    // Contar total de nodos
    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(RBNode9<K, V> node) {
        if (node == NIL)
            return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
}

public class Ej9 {
    public static void main(String[] args) {
        System.out.println("=== PRUEBAS DE CONSULTA POR RANGO ===\n");

        // Crear árbol con valores del 1 al 15
        RBTree9<Integer, String> tree = new RBTree9<>();

        int[] valores = { 8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15 };

        System.out.println("Insertando valores: ");
        for (int val : valores) {
            tree.insertBST(val, "val_" + val);
            System.out.print(val + " ");
        }
        System.out.println();

        tree.printTree();

        int totalNodes = tree.countNodes();
        System.out.println("\nTotal de nodos en el árbol: " + totalNodes);

        // PRUEBA 1: Rango pequeño
        System.out.println("\n--- PRUEBA 1: Rango pequeño [5, 7] ---");
        List<Integer> result1 = tree.rangeQuery(5, 7);
        System.out.println("Resultado: " + result1);
        System.out.println("Eficiencia: " + tree.nodesVisited + "/" + totalNodes + " nodos visitados");

        // PRUEBA 2: Rango medio
        System.out.println("\n--- PRUEBA 2: Rango medio [6, 12] ---");
        List<Integer> result2 = tree.rangeQuery(6, 12);
        System.out.println("Resultado: " + result2);
        System.out.println("Eficiencia: " + tree.nodesVisited + "/" + totalNodes + " nodos visitados");

        // PRUEBA 3: Rango grande
        System.out.println("\n--- PRUEBA 3: Rango grande [1, 15] ---");
        List<Integer> result3 = tree.rangeQuery(1, 15);
        System.out.println("Resultado: " + result3);
        System.out.println("Eficiencia: " + tree.nodesVisited + "/" + totalNodes + " nodos visitados");

        // PRUEBA 4: Rango al inicio
        System.out.println("\n--- PRUEBA 4: Rango al inicio [1, 3] ---");
        List<Integer> result4 = tree.rangeQuery(1, 3);
        System.out.println("Resultado: " + result4);
        System.out.println("Eficiencia: " + tree.nodesVisited + "/" + totalNodes + " nodos visitados");

        // PRUEBA 5: Rango al final
        System.out.println("\n--- PRUEBA 5: Rango al final [13, 15] ---");
        List<Integer> result5 = tree.rangeQuery(13, 15);
        System.out.println("Resultado: " + result5);
        System.out.println("Eficiencia: " + tree.nodesVisited + "/" + totalNodes + " nodos visitados");

        // PRUEBA 6: Rango vacío (no hay elementos)
        System.out.println("\n--- PRUEBA 6: Rango vacío [50, 60] ---");
        List<Integer> result6 = tree.rangeQuery(50, 60);
        System.out.println("Resultado: " + result6);
        System.out.println("Eficiencia: " + tree.nodesVisited + "/" + totalNodes + " nodos visitados");

        // PRUEBA 7: Un solo elemento
        System.out.println("\n--- PRUEBA 7: Un solo elemento [8, 8] ---");
        List<Integer> result7 = tree.rangeQuery(8, 8);
        System.out.println("Resultado: " + result7);
        System.out.println("Eficiencia: " + tree.nodesVisited + "/" + totalNodes + " nodos visitados");

        // PRUEBA 8: Comparación con recorrido completo
        System.out.println("\n--- PRUEBA 8: Comparación con inorder completo ---");
        System.out.println("Buscando rango [4, 10]...");

        // Método optimizado
        List<Integer> resultOptimized = tree.rangeQuerySimple(4, 10);
        int visitedOptimized = tree.nodesVisited;

        // Método no optimizado (recorrido completo)
        List<Integer> resultComplete = tree.inorderComplete();
        int visitedComplete = tree.nodesVisited;

        // Filtrar manualmente
        List<Integer> filteredComplete = new ArrayList<>();
        for (Integer key : resultComplete) {
            if (key >= 4 && key <= 10) {
                filteredComplete.add(key);
            }
        }

        System.out.println("Resultado optimizado: " + resultOptimized);
        System.out.println("Nodos visitados (optimizado): " + visitedOptimized);
        System.out.println("\nRecorrido completo: " + resultComplete);
        System.out.println("Nodos visitados (completo): " + visitedComplete);
        System.out.println("Resultado filtrado: " + filteredComplete);
        System.out.println("\nMejora: " + (visitedComplete - visitedOptimized) + " nodos menos visitados");
        System.out.println("Porcentaje de eficiencia: " +
                String.format("%.1f%%", (1.0 - (double) visitedOptimized / visitedComplete) * 100));

        // PRUEBA 9: Múltiples consultas
        System.out.println("\n\n--- PRUEBA 9: Tabla de múltiples consultas ---");
        System.out.println("┌──────────────┬─────────────────────┬───────────────┐");
        System.out.println("│    Rango     │      Resultado      │ Nodos visitados│");
        System.out.println("├──────────────┼─────────────────────┼───────────────┤");

        int[][] rangos = { { 1, 5 }, { 3, 8 }, { 7, 11 }, { 10, 15 }, { 2, 14 } };

        for (int[] rango : rangos) {
            List<Integer> resultado = tree.rangeQuerySimple(rango[0], rango[1]);
            System.out.printf("│ [%2d, %2d]    │ %-19s │ %7d/%2d     │%n",
                    rango[0], rango[1], resultado, tree.nodesVisited, totalNodes);
        }
        System.out.println("└──────────────┴─────────────────────┴───────────────┘");
    }
}
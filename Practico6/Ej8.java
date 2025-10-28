/*
 * 8) successor y predecessor
Implementá successor y predecessor de un nodo BST; probá con claves {5,10,15}.
Objetivo: obtener siguiente/anterior en orden.
Tarea: implementar RBNode<K,V> successor(node) y predecessor(node) usando
reglas BST.
Successor (sucesor): el siguiente nodo más grande (la menor clave > clave(x)).
Predecessor (predecesor): el anterior nodo más chico (la mayor clave < clave(x)).
 */

package Practico6;

// Clase que representa un nodo del árbol
class RBNode8<K, V> {
    K key;
    V value;
    boolean color; // true = ROJO, false = NEGRO
    RBNode8<K, V> left;
    RBNode8<K, V> right;
    RBNode8<K, V> parent;

    static final boolean RED = true;
    static final boolean BLACK = false;

    public RBNode8(K key, V value, boolean color, RBNode8<K, V> nil) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = nil;
        this.right = nil;
        this.parent = nil;
    }

    public RBNode8() {
        this.key = null;
        this.value = null;
        this.color = BLACK;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Clase principal del árbol con successor y predecessor
class RBTree8<K extends Comparable<K>, V> {
    final RBNode8<K, V> NIL;
    RBNode8<K, V> root;

    public RBTree8() {
        NIL = new RBNode8<>();
        root = NIL;
    }

    /**
     * Encuentra el sucesor de un nodo (el siguiente nodo más grande en orden)
     * 
     * Algoritmo:
     * 1. Si el nodo tiene subárbol derecho, el sucesor es el mínimo de ese subárbol
     * 2. Si no, sube por los padres hasta encontrar un nodo que sea hijo izquierdo
     * 
     * @param node el nodo del cual encontrar el sucesor
     * @return el nodo sucesor, o NIL si no existe
     */
    public RBNode8<K, V> successor(RBNode8<K, V> node) {
        if (node == NIL) {
            return NIL;
        }

        // Caso 1: Si el nodo tiene subárbol derecho
        // El sucesor es el mínimo del subárbol derecho
        if (node.right != NIL) {
            System.out.println("  → Tiene subárbol derecho, buscar mínimo");
            return minimum(node.right);
        }

        // Caso 2: No tiene subárbol derecho
        // Sube por los padres hasta encontrar un nodo que sea hijo izquierdo
        System.out.println("  → No tiene subárbol derecho, subir por padres");
        RBNode8<K, V> y = node.parent;
        RBNode8<K, V> x = node;

        while (y != NIL && x == y.right) {
            System.out.println("    Subiendo: " + y.key + " (x es hijo derecho)");
            x = y;
            y = y.parent;
        }

        return y;
    }

    /**
     * Encuentra el predecesor de un nodo (el anterior nodo más chico en orden)
     * 
     * Algoritmo:
     * 1. Si el nodo tiene subárbol izquierdo, el predecesor es el máximo de ese subárbol
     * 2. Si no, sube por los padres hasta encontrar un nodo que sea hijo derecho
     * 
     * @param node el nodo del cual encontrar el predecesor
     * @return el nodo predecesor, o NIL si no existe
     */
    public RBNode8<K, V> predecessor(RBNode8<K, V> node) {
        if (node == NIL) {
            return NIL;
        }

        // Caso 1: Si el nodo tiene subárbol izquierdo
        // El predecesor es el máximo del subárbol izquierdo
        if (node.left != NIL) {
            System.out.println("  → Tiene subárbol izquierdo, buscar máximo");
            return maximum(node.left);
        }

        // Caso 2: No tiene subárbol izquierdo
        // Sube por los padres hasta encontrar un nodo que sea hijo derecho
        System.out.println("  → No tiene subárbol izquierdo, subir por padres");
        RBNode8<K, V> y = node.parent;
        RBNode8<K, V> x = node;

        while (y != NIL && x == y.left) {
            System.out.println("    Subiendo: " + y.key + " (x es hijo izquierdo)");
            x = y;
            y = y.parent;
        }

        return y;
    }

    /**
     * Encuentra el nodo con la clave mínima en un subárbol
     * 
     * @param node raíz del subárbol
     * @return el nodo con la clave mínima
     */
    public RBNode8<K, V> minimum(RBNode8<K, V> node) {
        if (node == NIL) {
            return NIL;
        }

        while (node.left != NIL) {
            System.out.println("    Yendo a la izquierda: " + node.left.key);
            node = node.left;
        }

        return node;
    }

    /**
     * Encuentra el nodo con la clave máxima en un subárbol
     * 
     * @param node raíz del subárbol
     * @return el nodo con la clave máxima
     */
    public RBNode8<K, V> maximum(RBNode8<K, V> node) {
        if (node == NIL) {
            return NIL;
        }

        while (node.right != NIL) {
            System.out.println("    Yendo a la derecha: " + node.right.key);
            node = node.right;
        }

        return node;
    }

    /**
     * Recorrido en orden (inorder) del árbol
     * Útil para verificar el orden de los nodos
     */
    public void inorderTraversal() {
        System.out.print("Recorrido en orden: ");
        inorderTraversal(root);
        System.out.println();
    }

    private void inorderTraversal(RBNode8<K, V> node) {
        if (node != NIL) {
            inorderTraversal(node.left);
            System.out.print(node.key + "(" + node.value + ") ");
            inorderTraversal(node.right);
        }
    }

    // Método para buscar un nodo por su clave
    public RBNode8<K, V> search(K key) {
        RBNode8<K, V> current = root;
        while (current != NIL && !key.equals(current.key)) {
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current;
    }

    // Inserción BST simple
    public RBNode8<K, V> insertBST(K key, V value) {
        RBNode8<K, V> newNode = new RBNode8<>(key, value, RBNode8.RED, NIL);
        
        if (root == NIL) {
            root = newNode;
            root.color = RBNode8.BLACK;
            return newNode;
        }

        RBNode8<K, V> current = root;
        RBNode8<K, V> parent = NIL;

        while (current != NIL) {
            parent = current;
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else if (key.compareTo(current.key) > 0) {
                current = current.right;
            } else {
                current.value = value;
                return current;
            }
        }

        newNode.parent = parent;
        if (key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        return newNode;
    }

    // Método para visualizar el árbol
    public void printTree() {
        System.out.println("Estructura del árbol:");
        printTree(root, "", true);
    }

    private void printTree(RBNode8<K, V> node, String prefix, boolean isTail) {
        if (node != NIL) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + 
                             node.key + ":" + node.value + ":" + (node.color == RBNode8.RED ? "R" : "N"));
            
            if (node.left != NIL || node.right != NIL) {
                printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
                printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }
}

public class Ej8 {
    public static void main(String[] args) {
        System.out.println("=== PRUEBAS DE SUCCESSOR Y PREDECESSOR ===\n");

        // PRUEBA 1: Árbol simple con {5, 10, 15}
        System.out.println("--- PRUEBA 1: Árbol simple {5, 10, 15} ---");
        RBTree8<Integer, String> tree1 = new RBTree8<>();
        
        tree1.insertBST(10, "diez");
        tree1.insertBST(5, "cinco");
        tree1.insertBST(15, "quince");
        
        tree1.printTree();
        tree1.inorderTraversal();

        // Probar successor
        System.out.println("\nSuccessor de cada nodo:");
        for (int key : new int[]{5, 10, 15}) {
            RBNode8<Integer, String> node = tree1.search(key);
            System.out.println("\nNodo: " + key);
            RBNode8<Integer, String> succ = tree1.successor(node);
            System.out.println("Successor: " + (succ != tree1.NIL ? succ.key : "NIL"));
        }

        // Probar predecessor
        System.out.println("\n\nPredecessor de cada nodo:");
        for (int key : new int[]{5, 10, 15}) {
            RBNode8<Integer, String> node = tree1.search(key);
            System.out.println("\nNodo: " + key);
            RBNode8<Integer, String> pred = tree1.predecessor(node);
            System.out.println("Predecessor: " + (pred != tree1.NIL ? pred.key : "NIL"));
        }

        // PRUEBA 2: Árbol más complejo
        System.out.println("\n\n--- PRUEBA 2: Árbol más complejo ---");
        RBTree8<Integer, String> tree2 = new RBTree8<>();
        
        int[] valores = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 65};
        for (int val : valores) {
            tree2.insertBST(val, "val_" + val);
        }
        
        tree2.printTree();
        tree2.inorderTraversal();

        // Probar casos especiales
        System.out.println("\n\nCasos especiales:");
        
        // Caso: Nodo con subárbol derecho
        System.out.println("\n1. Nodo 25 (tiene subárbol derecho):");
        RBNode8<Integer, String> node25 = tree2.search(25);
        RBNode8<Integer, String> succ25 = tree2.successor(node25);
        System.out.println("Successor: " + (succ25 != tree2.NIL ? succ25.key : "NIL"));

        // Caso: Nodo sin subárbol derecho
        System.out.println("\n2. Nodo 27 (sin subárbol derecho):");
        RBNode8<Integer, String> node27 = tree2.search(27);
        RBNode8<Integer, String> succ27 = tree2.successor(node27);
        System.out.println("Successor: " + (succ27 != tree2.NIL ? succ27.key : "NIL"));

        // Caso: Máximo del árbol (no tiene sucesor)
        System.out.println("\n3. Nodo 80 (máximo del árbol):");
        RBNode8<Integer, String> node80 = tree2.search(80);
        RBNode8<Integer, String> succ80 = tree2.successor(node80);
        System.out.println("Successor: " + (succ80 != tree2.NIL ? succ80.key : "NIL"));

        // Caso: Nodo con subárbol izquierdo
        System.out.println("\n4. Nodo 75 (tiene subárbol izquierdo):");
        RBNode8<Integer, String> node75 = tree2.search(75);
        RBNode8<Integer, String> pred75 = tree2.predecessor(node75);
        System.out.println("Predecessor: " + (pred75 != tree2.NIL ? pred75.key : "NIL"));

        // Caso: Nodo sin subárbol izquierdo
        System.out.println("\n5. Nodo 55 (sin subárbol izquierdo):");
        RBNode8<Integer, String> node55 = tree2.search(55);
        RBNode8<Integer, String> pred55 = tree2.predecessor(node55);
        System.out.println("Predecessor: " + (pred55 != tree2.NIL ? pred55.key : "NIL"));

        // Caso: Mínimo del árbol (no tiene predecesor)
        System.out.println("\n6. Nodo 5 (mínimo del árbol):");
        RBNode8<Integer, String> node5 = tree2.search(5);
        RBNode8<Integer, String> pred5 = tree2.predecessor(node5);
        System.out.println("Predecessor: " + (pred5 != tree2.NIL ? pred5.key : "NIL"));

        // PRUEBA 3: Recorrido completo usando successor
        System.out.println("\n\n--- PRUEBA 3: Recorrido usando successor ---");
        RBNode8<Integer, String> min = tree2.minimum(tree2.root);
        System.out.print("Recorrido desde el mínimo: ");
        RBNode8<Integer, String> current = min;
        while (current != tree2.NIL) {
            System.out.print(current.key + " ");
            current = tree2.successor(current);
        }
        System.out.println();

        // PRUEBA 4: Recorrido completo usando predecessor
        System.out.println("\n--- PRUEBA 4: Recorrido usando predecessor ---");
        RBNode8<Integer, String> max = tree2.maximum(tree2.root);
        System.out.print("Recorrido desde el máximo: ");
        current = max;
        while (current != tree2.NIL) {
            System.out.print(current.key + " ");
            current = tree2.predecessor(current);
        }
        System.out.println();

        // PRUEBA 5: Tabla de sucesores y predecesores
        System.out.println("\n\n--- PRUEBA 5: Tabla de sucesores y predecesores ---");
        System.out.println("┌──────────┬────────────┬──────────────┐");
        System.out.println("│   Nodo   │  Successor │  Predecessor │");
        System.out.println("├──────────┼────────────┼──────────────┤");
        
        for (int val : new int[]{5, 10, 25, 27, 30, 50, 55, 60, 65, 75, 80}) {
            RBNode8<Integer, String> node = tree2.search(val);
            if (node != tree2.NIL) {
                RBNode8<Integer, String> succ = tree2.successor(node);
                RBNode8<Integer, String> pred = tree2.predecessor(node);
                System.out.printf("│ %8d │ %10s │ %12s │%n", 
                                val,
                                (succ != tree2.NIL ? succ.key : "NIL"),
                                (pred != tree2.NIL ? pred.key : "NIL"));
            }
        }
        System.out.println("└──────────┴────────────┴──────────────┘");
    }
}
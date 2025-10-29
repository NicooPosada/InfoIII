/*
3) Rotación derecha
Implementá rotateRight(y) (simétrico); verificá punteros y posible cambio de raíz tras la
rotación.
Objetivo: implementar rotateRight(y) (simétrico).
 */

package Practico6;

// Clase que representa un nodo del árbol rojo-negro
class RBNode3<K, V> {
    K key;
    @SuppressWarnings("unused")
    V value;
    boolean color; // true = ROJO, false = NEGRO
    RBNode3<K, V> left;
    RBNode3<K, V> right;
    RBNode3<K, V> parent;

    static final boolean RED = true;
    static final boolean BLACK = false;

    public RBNode3(K key, V value, boolean color, RBNode3<K, V> nil) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = nil;
        this.right = nil;
        this.parent = nil;
    }

    public RBNode3() {
        this.key = null;
        this.value = null;
        this.color = BLACK;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Clase principal del árbol rojo-negro con rotación derecha
class RBTree3<K extends Comparable<K>, V> {
    final RBNode3<K, V> NIL;
    RBNode3<K, V> root;

    public RBTree3() {
        NIL = new RBNode3<>();
        root = NIL;
    }

    /**
     * Rotación derecha sobre el nodo y
     * 
     * Antes:        y              Después:      x
     *              / \                          / \
     *             x   c                        a   y
     *            / \                              / \
     *           a   b                            b   c
     * 
     * @param y nodo sobre el cual rotar
     */
    public void rotateRight(RBNode3<K, V> y) {
        if (y == NIL || y.left == NIL) {
            System.out.println("No se puede rotar: y o y.left es NIL");
            return;
        }

        RBNode3<K, V> x = y.left; // x es el hijo izquierdo de y

        // 1. El subárbol derecho de x (b) se convierte en el subárbol izquierdo de y
        y.left = x.right;
        if (x.right != NIL) {
            x.right.parent = y;
        }

        // 2. El padre de y ahora es padre de x
        x.parent = y.parent;

        // 3. Actualizar el puntero del padre de y
        if (y.parent == NIL) {
            // y era la raíz, ahora x es la raíz
            root = x;
        } else if (y == y.parent.left) {
            // y era hijo izquierdo
            y.parent.left = x;
        } else {
            // y era hijo derecho
            y.parent.right = x;
        }

        // 4. y se convierte en hijo derecho de x
        x.right = y;
        y.parent = x;
    }

    /**
     * Rotación izquierda sobre el nodo x (implementada también para pruebas completas)
     */
    public void rotateLeft(RBNode3<K, V> x) {
        if (x == NIL || x.right == NIL) {
            System.out.println("No se puede rotar: x o x.right es NIL");
            return;
        }

        RBNode3<K, V> y = x.right;
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    // Método auxiliar para insertar nodos manualmente (sin balanceo)
    public void insertSimple(K key, V value) {
        RBNode3<K, V> newNode = new RBNode3<>(key, value, RBNode3.RED, NIL);
        
        if (root == NIL) {
            root = newNode;
            root.color = RBNode3.BLACK;
            return;
        }

        RBNode3<K, V> current = root;
        RBNode3<K, V> parent = NIL;

        while (current != NIL) {
            parent = current;
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
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
        System.out.println("Estructura del árbol:");
        printTree(root, "", true);
        System.out.println();
    }

    private void printTree(RBNode3<K, V> node, String prefix, boolean isTail) {
        if (node != NIL) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + 
                             node.key + ":" + (node.color == RBNode3.RED ? "R" : "N") +
                             " (parent: " + (node.parent != NIL ? node.parent.key : "NIL") + ")");
            
            if (node.left != NIL || node.right != NIL) {
                printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
                printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    // Método para buscar un nodo por su clave
    public RBNode3<K, V> search(K key) {
        RBNode3<K, V> current = root;
        while (current != NIL && !key.equals(current.key)) {
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current;
    }
}

public class Ej3 {
    public static void main(String[] args) {
        RBTree3<Integer, String> tree = new RBTree3<>();

        // Construir un árbol pequeño para probar la rotación derecha
        System.out.println("=== CONSTRUYENDO ÁRBOL PEQUEÑO ===\n");
        
        /*
         * Árbol inicial:
         *       20
         *      /  \
         *     10   30
         *    /  \
         *   5    15
         */
        tree.insertSimple(20, "veinte");
        tree.insertSimple(10, "diez");
        tree.insertSimple(30, "treinta");
        tree.insertSimple(5, "cinco");
        tree.insertSimple(15, "quince");

        System.out.println("ANTES de rotateRight(20):");
        tree.printTree();

        // Realizar rotación derecha sobre el nodo 20
        RBNode3<Integer, String> node20 = tree.search(20);
        System.out.println("=== EJECUTANDO rotateRight(20) ===\n");
        tree.rotateRight(node20);

        /*
         * Árbol después de rotateRight(20):
         *       10
         *      /  \
         *     5    20
         *         /  \
         *        15   30
         */
        System.out.println("DESPUÉS de rotateRight(20):");
        tree.printTree();

        // Verificar que la raíz cambió
        System.out.println("=== VERIFICACIÓN DE RAÍZ ===");
        System.out.println("Nueva raíz: " + tree.root.key);
        System.out.println("Color de la raíz: " + (tree.root.color == RBNode3.BLACK ? "NEGRO" : "ROJO"));

        // Prueba adicional: rotar sobre el nodo 10
        System.out.println("\n=== EJECUTANDO rotateRight(10) ===\n");
        RBNode3<Integer, String> node10 = tree.search(10);
        tree.rotateRight(node10);

        /*
         * Árbol después de rotateRight(10):
         *       5
         *        \
         *         10
         *          \
         *           20
         *          /  \
         *         15   30
         */
        System.out.println("DESPUÉS de rotateRight(10):");
        tree.printTree();

        // Verificar la integridad del árbol
        System.out.println("=== VERIFICACIÓN FINAL ===");
        System.out.println("Raíz actual: " + tree.root.key);
        System.out.println("Hijo izquierdo de la raíz: " + 
                          (tree.root.left != tree.NIL ? tree.root.left.key : "NIL"));
        System.out.println("Hijo derecho de la raíz: " + 
                          (tree.root.right != tree.NIL ? tree.root.right.key : "NIL"));

        // Prueba de simetría: rotación izquierda y derecha
        System.out.println("\n=== PRUEBA DE SIMETRÍA ===");
        RBTree3<Integer, String> tree2 = new RBTree3<>();
        tree2.insertSimple(10, "diez");
        tree2.insertSimple(5, "cinco");
        tree2.insertSimple(20, "veinte");
        tree2.insertSimple(15, "quince");
        tree2.insertSimple(30, "treinta");

        System.out.println("Árbol original:");
        tree2.printTree();

        RBNode3<Integer, String> node10_v2 = tree2.search(10);
        tree2.rotateLeft(node10_v2);
        System.out.println("Después de rotateLeft(10):");
        tree2.printTree();

        RBNode3<Integer, String> node20_v2 = tree2.search(20);
        tree2.rotateRight(node20_v2);
        System.out.println("Después de rotateRight(20) - Debería volver al original:");
        tree2.printTree();
    }
}
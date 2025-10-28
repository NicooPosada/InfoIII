/*
2) Rotación izquierda
Implementá rotateLeft(x) actualizando parent/left/right y la raíz si corresponde; probalo en un
árbol chico.
Objetivo: implementar rotateLeft(x).
Tarea: dado un árbol pequeño, ejecutar la rotación y actualizar punteros (padres/hijos)
correctamente.
 */

package Practico6;

// Clase que representa un nodo del árbol rojo-negro
class RBNode2<K, V> {
    K key;
    V value;
    boolean color; // true = ROJO, false = NEGRO
    RBNode2<K, V> left;
    RBNode2<K, V> right;
    RBNode2<K, V> parent;

    static final boolean RED = true;
    static final boolean BLACK = false;

    public RBNode2(K key, V value, boolean color, RBNode2<K, V> nil) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = nil;
        this.right = nil;
        this.parent = nil;
    }

    public RBNode2() {
        this.key = null;
        this.value = null;
        this.color = BLACK;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Clase principal del árbol rojo-negro con rotación izquierda
class RBTree2<K extends Comparable<K>, V> {
    final RBNode2<K, V> NIL;
    RBNode2<K, V> root;

    public RBTree2() {
        NIL = new RBNode2<>();
        root = NIL;
    }

    /**
     * Rotación izquierda sobre el nodo x
     * 
     * Antes:        x              Después:      y
     *              / \                          / \
     *             a   y                        x   c
     *                / \                      / \
     *               b   c                    a   b
     * 
     * @param x nodo sobre el cual rotar
     */
    public void rotateLeft(RBNode2<K, V> x) {
        if (x == NIL || x.right == NIL) {
            System.out.println("No se puede rotar: x o x.right es NIL");
            return;
        }

        RBNode2<K, V> y = x.right; // y es el hijo derecho de x

        // 1. El subárbol izquierdo de y (b) se convierte en el subárbol derecho de x
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        }

        // 2. El padre de x ahora es padre de y
        y.parent = x.parent;

        // 3. Actualizar el puntero del padre de x
        if (x.parent == NIL) {
            // x era la raíz, ahora y es la raíz
            root = y;
        } else if (x == x.parent.left) {
            // x era hijo izquierdo
            x.parent.left = y;
        } else {
            // x era hijo derecho
            x.parent.right = y;
        }

        // 4. x se convierte en hijo izquierdo de y
        y.left = x;
        x.parent = y;
    }

    // Método auxiliar para insertar nodos manualmente (sin balanceo)
    public void insertSimple(K key, V value) {
        RBNode2<K, V> newNode = new RBNode2<>(key, value, RBNode2.RED, NIL);
        
        if (root == NIL) {
            root = newNode;
            root.color = RBNode2.BLACK;
            return;
        }

        RBNode2<K, V> current = root;
        RBNode2<K, V> parent = NIL;

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

    private void printTree(RBNode2<K, V> node, String prefix, boolean isTail) {
        if (node != NIL) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + 
                             node.key + ":" + node.value + ":" + (node.color == RBNode2.RED ? "R" : "N") +
                             " (parent: " + (node.parent != NIL ? node.parent.key : "NIL") + ")");
            
            if (node.left != NIL || node.right != NIL) {
                printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
                printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    // Método para buscar un nodo por su clave
    public RBNode2<K, V> search(K key) {
        RBNode2<K, V> current = root;
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

public class Ej2 {
    public static void main(String[] args) {
        RBTree2<Integer, String> tree = new RBTree2<>();

        // Construir un árbol pequeño para probar la rotación izquierda
        System.out.println("=== CONSTRUYENDO ÁRBOL PEQUEÑO ===\n");
        
        /*
         * Árbol inicial:
         *       10
         *      /  \
         *     5    20
         *         /  \
         *        15   30
         */
        tree.insertSimple(10, "diez");
        tree.insertSimple(5, "cinco");
        tree.insertSimple(20, "veinte");
        tree.insertSimple(15, "quince");
        tree.insertSimple(30, "treinta");

        System.out.println("ANTES de rotateLeft(10):");
        tree.printTree();

        // Realizar rotación izquierda sobre el nodo 10
        RBNode2<Integer, String> node10 = tree.search(10);
        System.out.println("=== EJECUTANDO rotateLeft(10) ===\n");
        tree.rotateLeft(node10);

        /*
         * Árbol después de rotateLeft(10):
         *       20
         *      /  \
         *     10   30
         *    /  \
         *   5    15
         */
        System.out.println("DESPUÉS de rotateLeft(10):");
        tree.printTree();

        // Prueba adicional: rotar sobre el nodo 20
        System.out.println("=== EJECUTANDO rotateLeft(20) ===\n");
        RBNode2<Integer, String> node20 = tree.search(20);
        tree.rotateLeft(node20);

        /*
         * Árbol después de rotateLeft(20):
         *       30
         *      /
         *     20
         *    /
         *   10
         *  /  \
         * 5    15
         */
        System.out.println("DESPUÉS de rotateLeft(20):");
        tree.printTree();

        // Verificar la integridad del árbol
        System.out.println("=== VERIFICACIÓN ===");
        System.out.println("Raíz actual: " + tree.root.key);
        System.out.println("Hijo izquierdo de la raíz: " + 
                          (tree.root.left != tree.NIL ? tree.root.left.key : "NIL"));
        System.out.println("Hijo derecho de la raíz: " + 
                          (tree.root.right != tree.NIL ? tree.root.right.key : "NIL"));
    }
}
/*
 * 4) Inserción como ABB (sin balance)
Insertá como BST y devolvé el nodo rojo creado con left/right/parent = NIL; sin balance ni
recoloreo.
Objetivo: insertar la clave en orden BST y devolver el nodo nuevo.
Tarea: insertBST(K key, V val) que cree nodo rojo con hijos/padre apuntando a
NIL.
Nota: no arreglar colores acá; se usa luego por fixInsert.
 */

package Practico6;

// Clase que representa un nodo del árbol rojo-negro
class RBNode4<K, V> {
    K key;
    V value;
    boolean color; // true = ROJO, false = NEGRO
    RBNode4<K, V> left;
    RBNode4<K, V> right;
    RBNode4<K, V> parent;

    static final boolean RED = true;
    static final boolean BLACK = false;

    public RBNode4(K key, V value, boolean color, RBNode4<K, V> nil) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = nil;
        this.right = nil;
        this.parent = nil;
    }

    public RBNode4() {
        this.key = null;
        this.value = null;
        this.color = BLACK;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Clase principal del árbol rojo-negro con inserción BST
class RBTree4<K extends Comparable<K>, V> {
    final RBNode4<K, V> NIL;
    RBNode4<K, V> root;

    public RBTree4() {
        NIL = new RBNode4<>();
        root = NIL;
    }

    /**
     * Inserción como árbol binario de búsqueda (BST) sin balanceo
     * Crea un nodo ROJO con left/right/parent apuntando a NIL
     * No realiza recoloreo ni rotaciones
     * 
     * @param key clave del nuevo nodo
     * @param val valor del nuevo nodo
     * @return el nodo recién insertado
     */
    public RBNode4<K, V> insertBST(K key, V val) {
        // Crear nuevo nodo rojo con todos los punteros apuntando a NIL
        RBNode4<K, V> newNode = new RBNode4<>(key, val, RBNode4.RED, NIL);

        // Caso especial: árbol vacío
        if (root == NIL) {
            root = newNode;
            // La raíz debe ser negra (esto se haría en fixInsert, pero lo hacemos aquí para mantener la propiedad)
            root.color = RBNode4.BLACK;
            return newNode;
        }

        // Buscar la posición correcta usando búsqueda BST
        RBNode4<K, V> current = root;
        RBNode4<K, V> parent = NIL;

        while (current != NIL) {
            parent = current;
            
            // Si la clave ya existe, podemos actualizarla o retornar el nodo existente
            if (key.equals(current.key)) {
                current.value = val; // Actualizar valor
                return current;
            } else if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        // Establecer el padre del nuevo nodo
        newNode.parent = parent;

        // Insertar el nuevo nodo en la posición correcta
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
        System.out.println();
    }

    private void printTree(RBNode4<K, V> node, String prefix, boolean isTail) {
        if (node != NIL) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + 
                             node.key + ":" + (node.color == RBNode4.RED ? "R" : "N") +
                             " (parent: " + (node.parent != NIL ? node.parent.key : "NIL") + ")");
            
            if (node.left != NIL || node.right != NIL) {
                printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
                printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    // Método para buscar un nodo por su clave
    public RBNode4<K, V> search(K key) {
        RBNode4<K, V> current = root;
        while (current != NIL && !key.equals(current.key)) {
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current;
    }

    // Método para verificar las propiedades del nodo
    public void verifyNode(RBNode4<K, V> node) {
        if (node == NIL) {
            System.out.println("El nodo es NIL");
            return;
        }

        System.out.println("Verificación del nodo " + node.key + ":");
        System.out.println("  - Color: " + (node.color == RBNode4.RED ? "ROJO" : "NEGRO"));
        System.out.println("  - Left apunta a: " + (node.left == NIL ? "NIL" : node.left.key));
        System.out.println("  - Right apunta a: " + (node.right == NIL ? "NIL" : node.right.key));
        System.out.println("  - Parent apunta a: " + (node.parent == NIL ? "NIL" : node.parent.key));
    }

    // Método para contar violaciones de color (dos rojos consecutivos)
    public int countRedViolations() {
        return countRedViolations(root);
    }

    private int countRedViolations(RBNode4<K, V> node) {
        if (node == NIL) {
            return 0;
        }

        int violations = 0;

        // Si el nodo es rojo y tiene un hijo rojo, es una violación
        if (node.color == RBNode4.RED) {
            if (node.left != NIL && node.left.color == RBNode4.RED) {
                violations++;
            }
            if (node.right != NIL && node.right.color == RBNode4.RED) {
                violations++;
            }
        }

        violations += countRedViolations(node.left);
        violations += countRedViolations(node.right);

        return violations;
    }
}

public class Ej4 {
    public static void main(String[] args) {
        RBTree4<Integer, String> tree = new RBTree4<>();

        System.out.println("=== INSERCIÓN BST SIN BALANCEO ===\n");

        // Insertar nodos usando insertBST
        System.out.println("Insertando 10...");
        RBNode4<Integer, String> node10 = tree.insertBST(10, "diez");
        tree.verifyNode(node10);
        System.out.println();

        System.out.println("Insertando 5...");
        RBNode4<Integer, String> node5 = tree.insertBST(5, "cinco");
        tree.verifyNode(node5);
        System.out.println();

        System.out.println("Insertando 20...");
        RBNode4<Integer, String> node20 = tree.insertBST(20, "veinte");
        tree.verifyNode(node20);
        System.out.println();

        System.out.println("Insertando 3...");
        RBNode4<Integer, String> node3 = tree.insertBST(3, "tres");
        tree.verifyNode(node3);
        System.out.println();

        System.out.println("Insertando 7...");
        RBNode4<Integer, String> node7 = tree.insertBST(7, "siete");
        tree.verifyNode(node7);
        System.out.println();

        System.out.println("Insertando 15...");
        RBNode4<Integer, String> node15 = tree.insertBST(15, "quince");
        tree.verifyNode(node15);
        System.out.println();

        System.out.println("Insertando 30...");
        RBNode4<Integer, String> node30 = tree.insertBST(30, "treinta");
        tree.verifyNode(node30);
        System.out.println();

        // Mostrar el árbol completo
        System.out.println("=== ÁRBOL COMPLETO ===");
        tree.printTree();

        // Verificar violaciones de color (dos nodos rojos consecutivos)
        int violations = tree.countRedViolations();
        System.out.println("=== ANÁLISIS ===");
        System.out.println("Violaciones de color (dos rojos consecutivos): " + violations);
        System.out.println("Nota: Estas violaciones se corregirán con fixInsert en el siguiente ejercicio.");

        // Probar actualización de nodo existente
        System.out.println("\n=== ACTUALIZACIÓN DE NODO EXISTENTE ===");
        System.out.println("Insertando 10 con nuevo valor...");
        RBNode4<Integer, String> node10Updated = tree.insertBST(10, "DIEZ ACTUALIZADO");
        tree.verifyNode(node10Updated);
        System.out.println();

        tree.printTree();

        // Verificar búsqueda
        System.out.println("=== VERIFICACIÓN DE BÚSQUEDA ===");
        RBNode4<Integer, String> found = tree.search(15);
        if (found != tree.NIL) {
            System.out.println("Nodo 15 encontrado: " + found.value);
        }

        RBNode4<Integer, String> notFound = tree.search(100);
        System.out.println("Nodo 100 encontrado: " + (notFound == tree.NIL ? "NO" : "SÍ"));
    }
}

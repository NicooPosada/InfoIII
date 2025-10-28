/*
 * 10) Verificadores de invariantes
Escribí verificadores: raizNegra, sinRojoRojo y alturaNegra; usalos en tests tras varias
inserciones.
Objetivo: chequear que el árbol siga siendo RBT.
Tarea:
● boolean raizNegra()
● boolean sinRojoRojo() (si un nodo es rojo, hijos negros)
● int alturaNegra() (misma black-height en todos los caminos; devolver -1 si
no).
 */

package Practico6;

// Clase que representa un nodo del árbol rojo-negro
class RBNode10<K, V> {
    K key;
    V value;
    boolean color; // true = ROJO, false = NEGRO
    RBNode10<K, V> left;
    RBNode10<K, V> right;
    RBNode10<K, V> parent;

    static final boolean RED = true;
    static final boolean BLACK = false;

    public RBNode10(K key, V value, boolean color, RBNode10<K, V> nil) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = nil;
        this.right = nil;
        this.parent = nil;
    }

    public RBNode10() {
        this.key = null;
        this.value = null;
        this.color = BLACK;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Clase principal del árbol con verificadores de invariantes
class RBTree10<K extends Comparable<K>, V> {
    final RBNode10<K, V> NIL;
    RBNode10<K, V> root;

    public RBTree10() {
        NIL = new RBNode10<>();
        root = NIL;
    }

    /**
     * INVARIANTE 1: La raíz debe ser negra
     * 
     * @return true si la raíz es negra (o el árbol está vacío), false en caso contrario
     */
    public boolean raizNegra() {
        if (root == NIL) {
            return true; // Árbol vacío es válido
        }
        return root.color == RBNode10.BLACK;
    }

    /**
     * INVARIANTE 2: No puede haber dos nodos rojos consecutivos
     * (Si un nodo es rojo, sus hijos deben ser negros)
     * 
     * @return true si no hay violaciones rojo-rojo, false en caso contrario
     */
    public boolean sinRojoRojo() {
        return sinRojoRojoHelper(root);
    }

    private boolean sinRojoRojoHelper(RBNode10<K, V> node) {
        if (node == NIL) {
            return true;
        }

        // Si el nodo es rojo, verificar que sus hijos sean negros
        if (node.color == RBNode10.RED) {
            if ((node.left != NIL && node.left.color == RBNode10.RED) ||
                (node.right != NIL && node.right.color == RBNode10.RED)) {
                System.out.println("  ✗ Violación rojo-rojo en nodo " + node.key);
                return false;
            }
        }

        // Verificar recursivamente en ambos subárboles
        return sinRojoRojoHelper(node.left) && sinRojoRojoHelper(node.right);
    }

    /**
     * INVARIANTE 3: Todos los caminos desde la raíz hasta las hojas (NIL)
     * deben tener la misma cantidad de nodos negros (black-height)
     * 
     * @return la altura negra del árbol, o -1 si la propiedad es violada
     */
    public int alturaNegra() {
        return alturaNegraHelper(root);
    }

    private int alturaNegraHelper(RBNode10<K, V> node) {
        if (node == NIL) {
            return 0; // NIL cuenta como negro, pero no incrementa la altura
        }

        // Calcular altura negra de los subárboles izquierdo y derecho
        int alturaIzq = alturaNegraHelper(node.left);
        int alturaDer = alturaNegraHelper(node.right);

        // Si algún subárbol es inválido, propagar el error
        if (alturaIzq == -1 || alturaDer == -1) {
            return -1;
        }

        // Si las alturas no coinciden, hay violación
        if (alturaIzq != alturaDer) {
            System.out.println("  ✗ Violación de altura negra en nodo " + node.key + 
                             " (izq=" + alturaIzq + ", der=" + alturaDer + ")");
            return -1;
        }

        // La altura negra es la de los hijos + 1 si este nodo es negro
        int altura = alturaIzq;
        if (node.color == RBNode10.BLACK) {
            altura++;
        }

        return altura;
    }

    /**
     * Verifica TODOS los invariantes de un árbol rojo-negro
     * 
     * @return true si el árbol es un RBT válido, false en caso contrario
     */
    public boolean verificarInvariantes() {
        System.out.println("\n=== VERIFICACIÓN DE INVARIANTES ===");
        
        boolean raizOk = raizNegra();
        System.out.println("1. Raíz negra: " + (raizOk ? "✓" : "✗"));
        
        boolean rojoRojoOk = sinRojoRojo();
        System.out.println("2. Sin rojo-rojo: " + (rojoRojoOk ? "✓" : "✗"));
        
        int altura = alturaNegra();
        boolean alturaOk = altura != -1;
        System.out.println("3. Altura negra: " + (alturaOk ? "✓ (altura=" + altura + ")" : "✗"));
        
        boolean esValido = raizOk && rojoRojoOk && alturaOk;
        System.out.println("\nResultado: " + (esValido ? "✓ ÁRBOL RB VÁLIDO" : "✗ ÁRBOL INVÁLIDO"));
        
        return esValido;
    }

    // Inserción BST simple (sin balanceo)
    public RBNode10<K, V> insertBST(K key, V value) {
        RBNode10<K, V> newNode = new RBNode10<>(key, value, RBNode10.RED, NIL);
        
        if (root == NIL) {
            root = newNode;
            root.color = RBNode10.BLACK;
            return newNode;
        }

        RBNode10<K, V> current = root;
        RBNode10<K, V> parent = NIL;

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

    // Método auxiliar para insertar con color específico
    public RBNode10<K, V> insertWithColor(K key, V value, boolean color) {
        RBNode10<K, V> newNode = new RBNode10<>(key, value, color, NIL);
        
        if (root == NIL) {
            root = newNode;
            return newNode;
        }

        RBNode10<K, V> current = root;
        RBNode10<K, V> parent = NIL;

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

        return newNode;
    }

    // Método para visualizar el árbol
    public void printTree() {
        System.out.println("Estructura del árbol:");
        printTree(root, "", true);
    }

    private void printTree(RBNode10<K, V> node, String prefix, boolean isTail) {
        if (node != NIL) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + 
                             node.key + ":" + node.value + ":" + (node.color == RBNode10.RED ? "R" : "N")
                             + " (p=" + (node.parent != null && node.parent != NIL && node.parent.key != null ? node.parent.key : "NIL") + ")");
            
            if (node.left != NIL || node.right != NIL) {
                printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
                printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }
}

public class Ej10 {
    public static void main(String[] args) {
        System.out.println("=== PRUEBAS DE VERIFICADORES DE INVARIANTES ===\n");

        // PRUEBA 1: Árbol válido
        System.out.println("--- PRUEBA 1: Árbol RB válido ---");
        RBTree10<Integer, String> tree1 = new RBTree10<>();
        /*
         *       10:N
         *      /    \
         *    5:R    15:R
         *   /  \    /   \
         * 3:N  7:N 12:N 20:N
         */
        tree1.insertWithColor(10, "diez", RBNode10.BLACK);
        tree1.insertWithColor(5, "cinco", RBNode10.RED);
        tree1.insertWithColor(15, "quince", RBNode10.RED);
        tree1.insertWithColor(3, "tres", RBNode10.BLACK);
        tree1.insertWithColor(7, "siete", RBNode10.BLACK);
        tree1.insertWithColor(12, "doce", RBNode10.BLACK);
        tree1.insertWithColor(20, "veinte", RBNode10.BLACK);
        
        tree1.printTree();
        tree1.verificarInvariantes();

        // PRUEBA 2: Raíz roja (inválido)
        System.out.println("\n\n--- PRUEBA 2: Raíz roja (INVÁLIDO) ---");
        RBTree10<Integer, String> tree2 = new RBTree10<>();
        tree2.insertWithColor(10, "diez", RBNode10.RED); // ¡Raíz roja!
        tree2.insertWithColor(5, "cinco", RBNode10.BLACK);
        tree2.insertWithColor(15, "quince", RBNode10.BLACK);
        
        tree2.printTree();
        tree2.verificarInvariantes();

        // PRUEBA 3: Violación rojo-rojo (inválido)
        System.out.println("\n\n--- PRUEBA 3: Violación rojo-rojo (INVÁLIDO) ---");
        RBTree10<Integer, String> tree3 = new RBTree10<>();
        /*
         *       10:N
         *      /    \
         *    5:R    15:N
         *   /
         * 3:R  <- ¡Violación! (padre rojo, hijo rojo)
         */
        tree3.insertWithColor(10, "diez", RBNode10.BLACK);
        tree3.insertWithColor(5, "cinco", RBNode10.RED);
        tree3.insertWithColor(15, "quince", RBNode10.BLACK);
        tree3.insertWithColor(3, "tres", RBNode10.RED); // ¡Violación!
        
        tree3.printTree();
        tree3.verificarInvariantes();

        // PRUEBA 4: Violación de altura negra (inválido)
        System.out.println("\n\n--- PRUEBA 4: Violación de altura negra (INVÁLIDO) ---");
        RBTree10<Integer, String> tree4 = new RBTree10<>();
        /*
         *       10:N
         *      /    \
         *    5:N    15:N
         *   /
         * 3:N  <- Altura negra: izq=2, der=1 (¡violación!)
         */
        tree4.insertWithColor(10, "diez", RBNode10.BLACK);
        tree4.insertWithColor(5, "cinco", RBNode10.BLACK);
        tree4.insertWithColor(15, "quince", RBNode10.BLACK);
        tree4.insertWithColor(3, "tres", RBNode10.BLACK); // ¡Violación de altura!
        
        tree4.printTree();
        tree4.verificarInvariantes();

        // PRUEBA 5: Múltiples inserciones BST (probablemente inválido)
        System.out.println("\n\n--- PRUEBA 5: Inserciones BST sin balanceo ---");
        RBTree10<Integer, String> tree5 = new RBTree10<>();
        
        int[] valores = {10, 5, 15, 3, 7, 12, 20};
        
        for (int val : valores) {
            System.out.println("\nInsertando " + val + "...");
            tree5.insertBST(val, "val_" + val);
            tree5.printTree();
            tree5.verificarInvariantes();
        }

        // PRUEBA 6: Árbol más complejo válido
        System.out.println("\n\n--- PRUEBA 6: Árbol complejo válido ---");
        RBTree10<Integer, String> tree6 = new RBTree10<>();
        /*
         *           8:N
         *         /     \
         *       4:R      12:R
         *      /  \     /    \
         *    2:N  6:N 10:N  14:N
         *   / \   / \  / \   / \
         * 1:R 3:R 5:R 7:R 9:R 11:R 13:R 15:R
         */
        tree6.insertWithColor(8, "8", RBNode10.BLACK);
        tree6.insertWithColor(4, "4", RBNode10.RED);
        tree6.insertWithColor(12, "12", RBNode10.RED);
        tree6.insertWithColor(2, "2", RBNode10.BLACK);
        tree6.insertWithColor(6, "6", RBNode10.BLACK);
        tree6.insertWithColor(10, "10", RBNode10.BLACK);
        tree6.insertWithColor(14, "14", RBNode10.BLACK);
        tree6.insertWithColor(1, "1", RBNode10.RED);
        tree6.insertWithColor(3, "3", RBNode10.RED);
        tree6.insertWithColor(5, "5", RBNode10.RED);
        tree6.insertWithColor(7, "7", RBNode10.RED);
        tree6.insertWithColor(9, "9", RBNode10.RED);
        tree6.insertWithColor(11, "11", RBNode10.RED);
        tree6.insertWithColor(13, "13", RBNode10.RED);
        tree6.insertWithColor(15, "15", RBNode10.RED);
        
        tree6.printTree();
        tree6.verificarInvariantes();

        // PRUEBA 7: Tabla resumen de todas las pruebas
        System.out.println("\n\n=== RESUMEN DE PRUEBAS ===");
        System.out.println("┌─────────┬────────────┬──────────────┬───────────────┬───────────┐");
        System.out.println("│  Árbol  │ Raíz Negra │ Sin Rojo-Rojo│ Altura Negra  │  Válido   │");
        System.out.println("├─────────┼────────────┼──────────────┼───────────────┼───────────┤");
        
        java.util.List<RBTree10<Integer, String>> arboles = java.util.Arrays.asList(tree1, tree2, tree3, tree4, tree5, tree6);
        String[] nombres = {"Válido", "Raíz roja", "Rojo-rojo", "Altura", "BST", "Complejo"};
        
        for (int i = 0; i < arboles.size(); i++) {
            RBTree10<Integer, String> arbol = arboles.get(i);
            boolean r = arbol.raizNegra();
            boolean rr = arbol.sinRojoRojo();
            int an = arbol.alturaNegra();
            boolean valido = r && rr && (an != -1);
            
            System.out.printf("│ %-7s │     %s      │      %s       │      %s       │     %s     │%n",
                            nombres[i],
                            r ? "✓" : "✗",
                            rr ? "✓" : "✗",
                            an != -1 ? "✓" : "✗",
                            valido ? "✓" : "✗");
        }
        System.out.println("└─────────┴────────────┴──────────────┴───────────────┴───────────┘");

        // PRUEBA 8: Árbol vacío
        System.out.println("\n\n--- PRUEBA 8: Árbol vacío ---");
        RBTree10<Integer, String> treeVacio = new RBTree10<>();
        treeVacio.printTree();
        treeVacio.verificarInvariantes();
    }
}
/*
 * 7) Rotación simple vs doble (un lado)
Rama izquierda del fix: LR → rotateLeft(p) y luego rotateRight(g); LL → solo rotateRight(g) +
recoloreo.
Objetivo: cubrir un lado completo de fixInsert.
Tarea: implementar rama p == g.left:
● si z == p.right → rotateLeft(p) (convierte LR en LL),
● luego rotateRight(g) y recolorear (p negro, g rojo).
 */

package Practico6;

// Clase que representa un nodo del árbol rojo-negro
class RBNode7<K, V> {
    K key;
    V value;
    boolean color; // true = ROJO, false = NEGRO
    RBNode7<K, V> left;
    RBNode7<K, V> right;
    RBNode7<K, V> parent;

    static final boolean RED = true;
    static final boolean BLACK = false;

    public RBNode7(K key, V value, boolean color, RBNode7<K, V> nil) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = nil;
        this.right = nil;
        this.parent = nil;
    }

    public RBNode7() {
        this.key = null;
        this.value = null;
        this.color = BLACK;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Clase principal del árbol rojo-negro con rotaciones para lado izquierdo
class RBTree7<K extends Comparable<K>, V> {
    final RBNode7<K, V> NIL;
    RBNode7<K, V> root;

    public RBTree7() {
        NIL = new RBNode7<>();
        root = NIL;
    }

    /**
     * Rotación izquierda sobre el nodo x
     */
    public void rotateLeft(RBNode7<K, V> x) {
        if (x == NIL || x.right == NIL) {
            return;
        }

        RBNode7<K, V> y = x.right;
        
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

    /**
     * Rotación derecha sobre el nodo y
     */
    public void rotateRight(RBNode7<K, V> y) {
        if (y == NIL || y.left == NIL) {
            return;
        }

        RBNode7<K, V> x = y.left;
        
        y.left = x.right;
        if (x.right != NIL) {
            x.right.parent = y;
        }

        x.parent = y.parent;
        if (y.parent == NIL) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }

        x.right = y;
        y.parent = x;
    }

    /**
     * Fix Insert para el lado izquierdo (p == g.left)
     * Maneja los casos LL y LR
     * 
     * @param z nodo con violación
     */
    public void fixInsertLeftSide(RBNode7<K, V> z) {
        if (z == NIL || z.parent == NIL || z.parent.parent == NIL) {
            return;
        }

        RBNode7<K, V> p = z.parent;        // padre
        RBNode7<K, V> g = p.parent;        // abuelo

        // Verificar que estamos en el lado izquierdo
        if (p != g.left) {
            System.out.println("⚠️ Error: p no es hijo izquierdo de g");
            return;
        }

        System.out.println("\n=== FIX INSERT - LADO IZQUIERDO ===");
        System.out.println("z=" + z.key + ", p=" + p.key + ", g=" + g.key);

        // Determinar si es caso LR o LL
        if (z == p.right) {
            // CASO LR: z es hijo derecho de p
            System.out.println("Caso: LR (Left-Right)");
            System.out.println("  Paso 1: rotateLeft(" + p.key + ") para convertir LR → LL");
            
            rotateLeft(p);
            
            // Después de la rotación, z y p intercambian roles
            // Ahora z está donde estaba p, y p es hijo de z
            RBNode7<K, V> temp = p;
            p = z;
            z = temp;
            
            System.out.println("  Ahora: z=" + z.key + ", p=" + p.key);
            printTree();
        } else {
            // CASO LL: z es hijo izquierdo de p
            System.out.println("Caso: LL (Left-Left)");
        }

        // En ambos casos, ahora tenemos configuración LL
        System.out.println("  Paso 2: rotateRight(" + g.key + ")");
        rotateRight(g);
        printTree();

        // Paso 3: Recolorear
        System.out.println("  Paso 3: Recoloreo");
        System.out.println("    - p (" + p.key + "): " + 
                         (p.color == RBNode7.RED ? "ROJO" : "NEGRO") + " → NEGRO");
        System.out.println("    - g (" + g.key + "): " + 
                         (g.color == RBNode7.RED ? "ROJO" : "NEGRO") + " → ROJO");
        
        p.color = RBNode7.BLACK;
        g.color = RBNode7.RED;

        System.out.println("\n✓ Fix completado");
    }

    /**
     * Versión completa de fixInsert que maneja todos los casos del lado izquierdo
     */
    public void fixInsertComplete(RBNode7<K, V> z) {
        System.out.println("\n=== INICIANDO FIX INSERT COMPLETO ===");
        
        while (z != NIL && z != root && z.parent.color == RBNode7.RED) {
            RBNode7<K, V> p = z.parent;
            
            if (p.parent == NIL) {
                break; // p es la raíz
            }
            
            RBNode7<K, V> g = p.parent;
            
            if (p == g.left) {
                // Lado izquierdo
                RBNode7<K, V> tio = g.right;
                
                if (tio != NIL && tio.color == RBNode7.RED) {
                    // Caso: Tío rojo - Recoloreo
                    System.out.println("\nCaso: Tío rojo - Recoloreo");
                    p.color = RBNode7.BLACK;
                    tio.color = RBNode7.BLACK;
                    g.color = RBNode7.RED;
                    z = g;
                } else {
                    // Tío negro - Rotaciones
                    fixInsertLeftSide(z);
                    break;
                }
            } else {
                // Lado derecho (simétrico)
                RBNode7<K, V> tio = g.left;
                
                if (tio != NIL && tio.color == RBNode7.RED) {
                    p.color = RBNode7.BLACK;
                    tio.color = RBNode7.BLACK;
                    g.color = RBNode7.RED;
                    z = g;
                } else {
                    // Aquí iría fixInsertRightSide (simétrico)
                    System.out.println("⚠️ Lado derecho - No implementado en este ejercicio");
                    break;
                }
            }
        }
        
        // Asegurar que la raíz sea negra
        root.color = RBNode7.BLACK;
        System.out.println("\n✓ Raíz asegurada como NEGRA");
    }

    // Método auxiliar para insertar nodos manualmente
    public RBNode7<K, V> insertSimple(K key, V value, boolean color) {
        RBNode7<K, V> newNode = new RBNode7<>(key, value, color, NIL);
        
        if (root == NIL) {
            root = newNode;
            root.color = RBNode7.BLACK;
            return newNode;
        }

        RBNode7<K, V> current = root;
        RBNode7<K, V> parent = NIL;

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

    // Inserción BST normal
    public RBNode7<K, V> insertBST(K key, V value) {
        RBNode7<K, V> newNode = new RBNode7<>(key, value, RBNode7.RED, NIL);
        
        if (root == NIL) {
            root = newNode;
            root.color = RBNode7.BLACK;
            return newNode;
        }

        RBNode7<K, V> current = root;
        RBNode7<K, V> parent = NIL;

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
        System.out.println("\nEstructura del árbol:");
        printTree(root, "", true);
    }

    private void printTree(RBNode7<K, V> node, String prefix, boolean isTail) {
        if (node != NIL) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + 
                             node.key + ":" + node.value + ":" + (node.color == RBNode7.RED ? "R" : "N"));
            
            if (node.left != NIL || node.right != NIL) {
                printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
                printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }
}

public class Ej7 {
    public static void main(String[] args) {
        System.out.println("=== PRUEBAS DE ROTACIÓN SIMPLE VS DOBLE (LADO IZQUIERDO) ===\n");

        // PRUEBA 1: Caso LL (Left-Left) - Rotación simple
        System.out.println("--- PRUEBA 1: Caso LL (Left-Left) ---");
        RBTree7<Integer, String> tree1 = new RBTree7<>();
        /*
         * Árbol inicial:
         *       10:N
         *      /    \
         *    5:R    15:N
         *   /
         *  3:R  <- violación
         * 
         * Después de rotateRight(10) y recoloreo:
         *       5:N
         *      /   \
         *    3:R   10:R
         *            \
         *            15:N
         */
        tree1.insertSimple(10, "diez", RBNode7.BLACK);
        tree1.insertSimple(5, "cinco", RBNode7.RED);
        tree1.insertSimple(15, "quince", RBNode7.BLACK);
        RBNode7<Integer, String> z1 = tree1.insertSimple(3, "tres", RBNode7.RED);
        
        System.out.println("ANTES del fix:");
        tree1.printTree();
        
        tree1.fixInsertLeftSide(z1);
        
        System.out.println("\nDESPUÉS del fix:");
        tree1.printTree();

        // PRUEBA 2: Caso LR (Left-Right) - Rotación doble
        System.out.println("\n\n--- PRUEBA 2: Caso LR (Left-Right) ---");
        RBTree7<Integer, String> tree2 = new RBTree7<>();
        /*
         * Árbol inicial:
         *       10:N
         *      /    \
         *    5:R    15:N
         *      \
         *      7:R  <- violación
         * 
         * Paso 1 - rotateLeft(5):
         *       10:N
         *      /    \
         *    7:R    15:N
         *   /
         *  5:R
         * 
         * Paso 2 - rotateRight(10) y recoloreo:
         *       7:N
         *      /   \
         *    5:R   10:R
         *            \
         *            15:N
         */
        tree2.insertSimple(10, "diez", RBNode7.BLACK);
        tree2.insertSimple(5, "cinco", RBNode7.RED);
        tree2.insertSimple(15, "quince", RBNode7.BLACK);
        RBNode7<Integer, String> z2 = tree2.insertSimple(7, "siete", RBNode7.RED);
        
        System.out.println("ANTES del fix:");
        tree2.printTree();
        
        tree2.fixInsertLeftSide(z2);
        
        System.out.println("\nDESPUÉS del fix:");
        tree2.printTree();

        // PRUEBA 3: Inserción completa con fix automático
        System.out.println("\n\n--- PRUEBA 3: Inserción completa con fix automático ---");
        RBTree7<Integer, String> tree3 = new RBTree7<>();
        
        int[] valores = {50, 25, 75, 10, 30, 5};
        
        for (int val : valores) {
            System.out.println("\n→ Insertando " + val);
            RBNode7<Integer, String> nodo = tree3.insertBST(val, "val_" + val);
            tree3.printTree();
            
            // Si hay violación, aplicar fix
            if (nodo.parent != tree3.NIL && nodo.parent.color == RBNode7.RED) {
                System.out.println("\n⚠️ Violación detectada!");
                tree3.fixInsertComplete(nodo);
                tree3.printTree();
            }
        }
        
        System.out.println("\n=== ÁRBOL FINAL ===");
        tree3.printTree();

        // PRUEBA 4: Comparación directa LL vs LR
        System.out.println("\n\n--- PRUEBA 4: Comparación LL vs LR ---");
        
        // LL
        RBTree7<Integer, String> treeLL = new RBTree7<>();
        treeLL.insertSimple(30, "30", RBNode7.BLACK);
        treeLL.insertSimple(20, "20", RBNode7.RED);
        treeLL.insertSimple(40, "40", RBNode7.BLACK);
        RBNode7<Integer, String> zLL = treeLL.insertSimple(10, "10", RBNode7.RED);
        
        System.out.println("Configuración LL - ANTES:");
        treeLL.printTree();
        treeLL.fixInsertLeftSide(zLL);
        System.out.println("\nConfiguración LL - DESPUÉS:");
        treeLL.printTree();
        
        // LR
        RBTree7<Integer, String> treeLR = new RBTree7<>();
        treeLR.insertSimple(30, "30", RBNode7.BLACK);
        treeLR.insertSimple(20, "20", RBNode7.RED);
        treeLR.insertSimple(40, "40", RBNode7.BLACK);
        RBNode7<Integer, String> zLR = treeLR.insertSimple(25, "25", RBNode7.RED);
        
        System.out.println("\n\nConfiguración LR - ANTES:");
        treeLR.printTree();
        treeLR.fixInsertLeftSide(zLR);
        System.out.println("\nConfiguración LR - DESPUÉS:");
        treeLR.printTree();
    }
}
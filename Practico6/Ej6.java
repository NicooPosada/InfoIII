/*
 * 6) Recoloreo por tío rojo
Tío rojo: padre y tío → negros, abuelo → rojo; continuá desde el abuelo. Asegurá raíz negra
al final.
Objetivo: implementar el caso fácil de fixInsert.
Tarea: si p y tío son rojos: poner negros a p y tío, rojo al abuelo g, y "subir" z = g.
 */

package Practico6;

// Clase que representa un nodo del árbol rojo-negro
class RBNode6<K, V> {
    K key;
    V value;
    boolean color; // true = ROJO, false = NEGRO
    RBNode6<K, V> left;
    RBNode6<K, V> right;
    RBNode6<K, V> parent;

    static final boolean RED = true;
    static final boolean BLACK = false;

    public RBNode6(K key, V value, boolean color, RBNode6<K, V> nil) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = nil;
        this.right = nil;
        this.parent = nil;
    }

    public RBNode6() {
        this.key = null;
        this.value = null;
        this.color = BLACK;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Clase principal del árbol rojo-negro con recoloreo
class RBTree6<K extends Comparable<K>, V> {
    final RBNode6<K, V> NIL;
    RBNode6<K, V> root;
    private int recolorCount = 0; // Contador de recoloreos

    public RBTree6() {
        NIL = new RBNode6<>();
        root = NIL;
    }

    /**
     * Recoloreo cuando el tío es rojo
     * - Padre y tío se vuelven negros
     * - Abuelo se vuelve rojo
     * - Se "sube" la violación al abuelo (z = g)
     * - Al final, asegura que la raíz sea negra
     * 
     * @param z nodo con violación (rojo con padre rojo)
     * @return el nuevo nodo a verificar (abuelo)
     */
    public RBNode6<K, V> recolorearTioRojo(RBNode6<K, V> z) {
        if (z == NIL || z.parent == NIL || z.parent.parent == NIL) {
            return NIL;
        }

        RBNode6<K, V> p = z.parent; // padre
        RBNode6<K, V> g = p.parent; // abuelo
        RBNode6<K, V> tio; // tío

        // Determinar quién es el tío
        if (p == g.left) {
            tio = g.right;
        } else {
            tio = g.left;
        }

        // Verificar que el tío sea rojo (condición para recoloreo)
        if (tio == NIL || tio.color == RBNode6.BLACK) {
            System.out.println("El tío no es rojo, no se puede aplicar recoloreo");
            return NIL;
        }

        System.out.println("Recoloreo #" + (++recolorCount));
        System.out.println(" - Padre " + p.key + ": ROJO → NEGRO");
        System.out.println(" - Tío " + tio.key + ": ROJO → NEGRO");
        System.out.println(" - Abuelo " + g.key + ": NEGRO → ROJO");

        // Realizar el recoloreo
        p.color = RBNode6.BLACK;
        tio.color = RBNode6.BLACK;
        g.color = RBNode6.RED;

        // "Subir" la violación al abuelo
        System.out.println(" - Subir z al abuelo: " + g.key);

        return g; // Retornar el abuelo como nuevo z
    }

    /**
     * Aplica recoloreo iterativamente hasta que no haya más violaciones
     * o se necesite rotación
     * 
     * @param z nodo inicial con violación
     */
    public void fixInsertTioRojo(RBNode6<K, V> z) {
        recolorCount = 0;
        System.out.println("\n=== INICIANDO FIX INSERT (SOLO TÍO ROJO) ===");
        System.out.println("Nodo inicial z: " + z.key);

        while (z != NIL && z.parent != NIL && z.parent.color == RBNode6.RED) {
            RBNode6<K, V> p = z.parent;

            // Si el padre es la raíz, no hay abuelo
            if (p.parent == NIL) {
                break;
            }

            RBNode6<K, V> g = p.parent;
            RBNode6<K, V> tio = (p == g.left) ? g.right : g.left;

            // Solo procesar si el tío es rojo
            if (tio != NIL && tio.color == RBNode6.RED) {
                z = recolorearTioRojo(z);
                if (z == NIL)
                    break;
            } else {
                System.out.println("Tío negro detectado en z=" + z.key + ", se requieren rotaciones");
                break;
            }
        }

        // IMPORTANTE: Asegurar que la raíz sea siempre negra
        if (root.color == RBNode6.RED) {
            System.out.println("\nCorrección final: Raíz " + root.key + " → NEGRO");
            root.color = RBNode6.BLACK;
        }

        System.out.println("\nFix Insert completado (" + recolorCount + " recoloreos)");
    }

    // Método auxiliar para insertar nodos manualmente
    public RBNode6<K, V> insertSimple(K key, V value, boolean color) {
        RBNode6<K, V> newNode = new RBNode6<>(key, value, color, NIL);

        if (root == NIL) {
            root = newNode;
            root.color = RBNode6.BLACK;
            return newNode;
        }

        RBNode6<K, V> current = root;
        RBNode6<K, V> parent = NIL;

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

    // Inserción BST normal que devuelve el nodo insertado
    public RBNode6<K, V> insertBST(K key, V value) {
        RBNode6<K, V> newNode = new RBNode6<>(key, value, RBNode6.RED, NIL);

        if (root == NIL) {
            root = newNode;
            root.color = RBNode6.BLACK;
            return newNode;
        }

        RBNode6<K, V> current = root;
        RBNode6<K, V> parent = NIL;

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

    private void printTree(RBNode6<K, V> node, String prefix, boolean isTail) {
        if (node != NIL) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") +
                    node.key + "(" + node.value + "):" + (node.color == RBNode6.RED ? "R" : "N"));

            if (node.left != NIL || node.right != NIL) {
                printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
                printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    // Método para contar violaciones de color
    public int countRedViolations() {
        return countRedViolations(root);
    }

    private int countRedViolations(RBNode6<K, V> node) {
        if (node == NIL)
            return 0;

        int violations = 0;
        if (node.color == RBNode6.RED) {
            if ((node.left != NIL && node.left.color == RBNode6.RED) ||
                    (node.right != NIL && node.right.color == RBNode6.RED)) {
                violations++;
            }
        }

        violations += countRedViolations(node.left);
        violations += countRedViolations(node.right);
        return violations;
    }
}

public class Ej6 {
    public static void main(String[] args) {
        System.out.println("=== PRUEBAS DE RECOLOREO POR TÍO ROJO ===\n");

        // PRUEBA 1: Caso simple de tío rojo
        System.out.println("--- PRUEBA 1: Caso simple ---");
        RBTree6<Integer, String> tree1 = new RBTree6<>();
        /*
         * Árbol inicial (con violación):
         * 10:N
         * / \
         * 5:R 15:R
         * /
         * 3:R <- violación
         */
        tree1.insertSimple(10, "diez", RBNode6.BLACK);
        tree1.insertSimple(5, "cinco", RBNode6.RED);
        tree1.insertSimple(15, "quince", RBNode6.RED);
        RBNode6<Integer, String> z1 = tree1.insertSimple(3, "tres", RBNode6.RED);

        System.out.println("ANTES del recoloreo:");
        tree1.printTree();
        System.out.println("Violaciones: " + tree1.countRedViolations());

        tree1.fixInsertTioRojo(z1);

        System.out.println("\nDESPUÉS del recoloreo:");
        tree1.printTree();
        System.out.println("Violaciones: " + tree1.countRedViolations());

        // PRUEBA 2: Múltiples recoloreos (la violación sube)
        System.out.println("\n\n--- PRUEBA 2: Múltiples recoloreos ---");
        RBTree6<Integer, String> tree2 = new RBTree6<>();
        /*
         * Construir un árbol más grande donde la violación sube
         */
        tree2.insertSimple(20, "veinte", RBNode6.BLACK);
        tree2.insertSimple(10, "diez", RBNode6.RED);
        tree2.insertSimple(30, "treinta", RBNode6.RED);
        tree2.insertSimple(5, "cinco", RBNode6.BLACK);
        tree2.insertSimple(15, "quince", RBNode6.BLACK);
        tree2.insertSimple(25, "veinticinco", RBNode6.BLACK);
        tree2.insertSimple(35, "treintaycinco", RBNode6.BLACK);
        RBNode6<Integer, String> z2 = tree2.insertSimple(3, "tres", RBNode6.RED);
        // Crear violación adicional
        tree2.insertSimple(7, "siete", RBNode6.RED);

        System.out.println("ANTES del recoloreo:");
        tree2.printTree();
        System.out.println("Violaciones: " + tree2.countRedViolations());

        tree2.fixInsertTioRojo(z2);

        System.out.println("\nDESPUÉS del recoloreo:");
        tree2.printTree();
        System.out.println("Violaciones: " + tree2.countRedViolations());

        // PRUEBA 3: Inserción completa con fix
        System.out.println("\n\n--- PRUEBA 3: Inserción con fix automático ---");
        RBTree6<Integer, String> tree3 = new RBTree6<>();

        int[] valores = { 10, 5, 15, 3, 7, 12, 20, 1 };

        for (int val : valores) {
            System.out.println("\n→ Insertando " + val);
            RBNode6<Integer, String> nodo = tree3.insertBST(val, "val_" + val);
            tree3.printTree();

            // Si hay violación, aplicar fix
            if (nodo.parent != tree3.NIL && nodo.parent.color == RBNode6.RED) {
                System.out.println("Violación detectada!");
                tree3.fixInsertTioRojo(nodo);
                tree3.printTree();
            }
        }

        System.out.println("\n=== ÁRBOL FINAL ===");
        tree3.printTree();
        System.out.println("Violaciones restantes: " + tree3.countRedViolations());
        System.out.println("Color de la raíz: " + (tree3.root.color == RBNode6.BLACK ? "NEGRO ✓" : "ROJO ✗"));

        // PRUEBA 4: Verificar que la raíz siempre es negra
        System.out.println("\n\n--- PRUEBA 4: Raíz siempre negra ---");
        RBTree6<Integer, String> tree4 = new RBTree6<>();
        tree4.insertSimple(10, "diez", RBNode6.BLACK);
        tree4.insertSimple(5, "cinco", RBNode6.RED);
        tree4.insertSimple(15, "quince", RBNode6.RED);

        System.out.println("Árbol inicial:");
        tree4.printTree();

        // Simular que la raíz se vuelve roja durante el proceso
        tree4.root.color = RBNode6.RED;
        System.out.println("\nRaíz forzada a ROJO:");
        tree4.printTree();

        // Aplicar fix (debería corregir la raíz)
        RBNode6<Integer, String> z4 = tree4.insertSimple(3, "tres", RBNode6.RED);
        tree4.fixInsertTioRojo(z4);

        System.out.println("\nDespués del fix:");
        tree4.printTree();
        System.out.println("Color de la raíz: " + (tree4.root.color == RBNode6.BLACK ? "NEGRO ✓" : "ROJO ✗"));
    }
}
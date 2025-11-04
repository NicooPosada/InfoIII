/*
 * 5) Clasificador de caso para fixInsert
Dado z,p,g, clasificá: TÍO_ROJO, LL, RR, LR o RL para decidir recoloreo o rotación.
Objetivo: decidir qué hacer tras una inserción.
Tarea: función Caso clasificar(z) que devuelva: TIO_ROJO, LL, RR, LR, RL.
 */

package Practico6;

// Enumeración para los casos de fixInsert
enum Caso {
    TIO_ROJO, // El tío es rojo -> Recoloreo
    LL, // Left-Left -> Rotación derecha simple
    RR, // Right-Right -> Rotación izquierda simple
    LR, // Left-Right -> Rotación doble (izquierda-derecha)
    RL, // Right-Left -> Rotación doble (derecha-izquierda)
    NINGUNO // No hay violación o caso especial
}

// Clase que representa un nodo del árbol rojo-negro
class RBNode5<K, V> {
    K key;
    V value;
    boolean color; // true = ROJO, false = NEGRO
    RBNode5<K, V> left;
    RBNode5<K, V> right;
    RBNode5<K, V> parent;

    static final boolean RED = true;
    static final boolean BLACK = false;

    public RBNode5(K key, V value, boolean color, RBNode5<K, V> nil) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = nil;
        this.right = nil;
        this.parent = nil;
    }

    public RBNode5() {
        this.key = null;
        this.value = null;
        this.color = BLACK;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Clase principal del árbol rojo-negro con clasificador de casos
class RBTree5<K extends Comparable<K>, V> {
    final RBNode5<K, V> NIL;
    RBNode5<K, V> root;

    public RBTree5() {
        NIL = new RBNode5<>();
        root = NIL;
    }

    /**
     * Clasificador de caso para fixInsert
     * Dado un nodo z (recién insertado o actual en el fix), determina qué caso
     * aplicar
     * 
     * @param z nodo a clasificar (generalmente rojo con padre rojo)
     * @return el caso correspondiente
     */
    public Caso clasificar(RBNode5<K, V> z) {
        // Si z es NIL, la raíz, o su padre es negro, no hay violación
        if (z == NIL || z == root || z.parent.color == RBNode5.BLACK) {
            return Caso.NINGUNO;
        }

        RBNode5<K, V> p = z.parent; // padre de z

        // Si el padre es la raíz, no hay abuelo
        if (p == root) {
            return Caso.NINGUNO;
        }

        RBNode5<K, V> g = p.parent; // abuelo de z
        RBNode5<K, V> tio; // tío de z

        // Determinar quién es el tío (hermano del padre)
        if (p == g.left) {
            tio = g.right;
        } else {
            tio = g.left;
        }

        // CASO 1: TÍO ROJO -> Recoloreo
        if (tio != NIL && tio.color == RBNode5.RED) {
            return Caso.TIO_ROJO;
        }

        // CASOS 2-5: TÍO NEGRO (o NIL) -> Rotaciones
        // Analizar la configuración z-p-g

        if (p == g.left) {
            // Padre es hijo izquierdo del abuelo
            if (z == p.left) {
                // CASO LL: z es hijo izquierdo de p
                return Caso.LL;
            } else {
                // CASO LR: z es hijo derecho de p
                return Caso.LR;
            }
        } else {
            // Padre es hijo derecho del abuelo
            if (z == p.right) {
                // CASO RR: z es hijo derecho de p
                return Caso.RR;
            } else {
                // CASO RL: z es hijo izquierdo de p
                return Caso.RL;
            }
        }
    }

    // Método para explicar el caso clasificado
    public void explicarCaso(RBNode5<K, V> z, Caso caso) {
        System.out.println("\n=== CLASIFICACIÓN DEL CASO ===");
        System.out.println(
                "Nodo z: " + z.key + " = " + z.value + " (color: " + (z.color == RBNode5.RED ? "ROJO" : "NEGRO") + ")");

        if (z.parent != NIL) {
            System.out.println("Padre p: " + z.parent.key + " = " + z.parent.value + " (color: "
                    + (z.parent.color == RBNode5.RED ? "ROJO" : "NEGRO") + ")");

            if (z.parent.parent != NIL) {
                System.out.println("Abuelo g: " + z.parent.parent.key + " = " + z.parent.parent.value + " (color: "
                        + (z.parent.parent.color == RBNode5.RED ? "ROJO" : "NEGRO") + ")");

                RBNode5<K, V> tio = (z.parent == z.parent.parent.left) ? z.parent.parent.right : z.parent.parent.left;
                System.out.println("Tío: " + (tio == NIL ? "NIL" : (tio.key + " = " + tio.value)) + " (color: "
                        + (tio.color == RBNode5.RED ? "ROJO" : "NEGRO") + ")");
            }
        }

        System.out.println("\nCaso clasificado: " + caso);

        switch (caso) {
            case TIO_ROJO -> System.out.println("Acción: RECOLOREO (cambiar color de padre, tío y abuelo)");
            case LL -> {
                System.out.println("Acción: ROTACIÓN DERECHA simple sobre el abuelo");
                System.out.println("Configuración: z-p-g alineados a la izquierda");
            }
            case RR -> {
                System.out.println("Acción: ROTACIÓN IZQUIERDA simple sobre el abuelo");
                System.out.println("Configuración: z-p-g alineados a la derecha");
            }
            case LR -> {
                System.out.println("Acción: ROTACIÓN DOBLE (primero izquierda sobre p, luego derecha sobre g)");
                System.out.println("Configuración: p izquierda de g, z derecha de p (forma de codo)");
            }
            case RL -> {
                System.out.println("Acción: ROTACIÓN DOBLE (primero derecha sobre p, luego izquierda sobre g)");
                System.out.println("Configuración: p derecha de g, z izquierda de p (forma de codo)");
            }
            case NINGUNO -> System.out.println("Acción: No se requiere corrección");
        }
    }

    // Método auxiliar para insertar nodos manualmente (sin balanceo)
    public RBNode5<K, V> insertSimple(K key, V value, boolean color) {
        RBNode5<K, V> newNode = new RBNode5<>(key, value, color, NIL);

        if (root == NIL) {
            root = newNode;
            return newNode;
        }

        RBNode5<K, V> current = root;
        RBNode5<K, V> parent = NIL;

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
        System.out.println("\nEstructura del árbol:");
        printTree(root, "", true);
    }

    private void printTree(RBNode5<K, V> node, String prefix, boolean isTail) {
        if (node != NIL) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") +
                    node.key + "=" + node.value + ":" + (node.color == RBNode5.RED ? "R" : "N"));

            if (node.left != NIL || node.right != NIL) {
                printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
                printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }
}

public class Ej5 {
    public static void main(String[] args) {
        RBTree5<Integer, String> tree;

        System.out.println("=== PRUEBAS DEL CLASIFICADOR DE CASOS ===");

        // CASO 1: TÍO ROJO
        System.out.println("\n--- CASO TÍO ROJO ---");
        tree = new RBTree5<>();
        /*
         * 10:N
         * / \
         * 5:R 15:R
         * /
         * 3:R <- z (violación: padre rojo)
         */
        tree.insertSimple(10, "diez", RBNode5.BLACK);
        tree.insertSimple(5, "cinco", RBNode5.RED);
        tree.insertSimple(15, "quince", RBNode5.RED);
        RBNode5<Integer, String> z1 = tree.insertSimple(3, "tres", RBNode5.RED);
        tree.printTree();
        Caso caso1 = tree.clasificar(z1);
        tree.explicarCaso(z1, caso1);

        // CASO 2: LL (Left-Left)
        System.out.println("\n\n--- CASO LL (Left-Left) ---");
        tree = new RBTree5<>();
        /*
         * 10:N
         * / \
         * 5:R 15:N
         * /
         * 3:R <- z
         */
        tree.insertSimple(10, "diez", RBNode5.BLACK);
        tree.insertSimple(5, "cinco", RBNode5.RED);
        tree.insertSimple(15, "quince", RBNode5.BLACK);
        RBNode5<Integer, String> z2 = tree.insertSimple(3, "tres", RBNode5.RED);
        tree.printTree();
        Caso caso2 = tree.clasificar(z2);
        tree.explicarCaso(z2, caso2);

        // CASO 3: RR (Right-Right)
        System.out.println("\n\n--- CASO RR (Right-Right) ---");
        tree = new RBTree5<>();
        /*
         * 10:N
         * / \
         * 5:N 15:R
         * \
         * 20:R <- z
         */
        tree.insertSimple(10, "diez", RBNode5.BLACK);
        tree.insertSimple(5, "cinco", RBNode5.BLACK);
        tree.insertSimple(15, "quince", RBNode5.RED);
        RBNode5<Integer, String> z3 = tree.insertSimple(20, "veinte", RBNode5.RED);
        tree.printTree();
        Caso caso3 = tree.clasificar(z3);
        tree.explicarCaso(z3, caso3);

        // CASO 4: LR (Left-Right)
        System.out.println("\n\n--- CASO LR (Left-Right) ---");
        tree = new RBTree5<>();
        /*
         * 10:N
         * / \
         * 5:R 15:N
         * \
         * 7:R <- z
         */
        tree.insertSimple(10, "diez", RBNode5.BLACK);
        tree.insertSimple(5, "cinco", RBNode5.RED);
        tree.insertSimple(15, "quince", RBNode5.BLACK);
        RBNode5<Integer, String> z4 = tree.insertSimple(7, "siete", RBNode5.RED);
        tree.printTree();
        Caso caso4 = tree.clasificar(z4);
        tree.explicarCaso(z4, caso4);

        // CASO 5: RL (Right-Left)
        System.out.println("\n\n--- CASO RL (Right-Left) ---");
        tree = new RBTree5<>();
        /*
         * 10:N
         * / \
         * 5:N 15:R
         * /
         * 12:R <- z
         */
        tree.insertSimple(10, "diez", RBNode5.BLACK);
        tree.insertSimple(5, "cinco", RBNode5.BLACK);
        tree.insertSimple(15, "quince", RBNode5.RED);
        RBNode5<Integer, String> z5 = tree.insertSimple(12, "doce", RBNode5.RED);
        tree.printTree();
        Caso caso5 = tree.clasificar(z5);
        tree.explicarCaso(z5, caso5);

        // CASO 6: NINGUNO (sin violación)
        System.out.println("\n\n--- CASO NINGUNO (sin violación) ---");
        tree = new RBTree5<>();
        tree.insertSimple(10, "diez", RBNode5.BLACK);
        RBNode5<Integer, String> z6 = tree.insertSimple(5, "cinco", RBNode5.RED);
        tree.printTree();
        Caso caso6 = tree.clasificar(z6);
        tree.explicarCaso(z6, caso6);

        System.out.println("\n\n=== RESUMEN DE CASOS ===");
        System.out.println("TÍO_ROJO: Recoloreo del padre, tío y abuelo");
        System.out.println("LL: Rotación derecha simple sobre el abuelo");
        System.out.println("RR: Rotación izquierda simple sobre el abuelo");
        System.out.println("LR: Rotación doble (izq sobre padre, der sobre abuelo)");
        System.out.println("RL: Rotación doble (der sobre padre, izq sobre abuelo)");
    }
}
/*PRÁCTICO ÁRBOL ROJINEGRO

1) Nodo y NIL sentinel (No hay nodo)
Definí RBNode y un NIL negro único; inicializá root = NIL y que todos los punteros vacíos
apunten a NIL.
Objetivo: definir la estructura mínima con NIL compartido (negro).
Tarea: crear RBNode<K,V> con key,val,color,left,right,parent y en RBTree un
final RBNode<K,V> NIL negro; root = NIL.
Entrega: clase con constructor que setee hijos/padre a NIL.
*/

package Practico6;

// Clase que representa un nodo del árbol rojo-negro
class RBNode<K, V> {
    K key;
    V value;
    boolean color; // true = ROJO, false = NEGRO
    RBNode<K, V> left;
    RBNode<K, V> right;
    RBNode<K, V> parent;

    // Constantes para los colores
    static final boolean RED = true;
    static final boolean BLACK = false;

    // Constructor para nodos normales
    public RBNode(K key, V value, boolean color, RBNode<K, V> nil) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = nil;
        this.right = nil;
        this.parent = nil;
    }

    // Constructor para el nodo NIL (centinela)
    public RBNode() {
        this.key = null;
        this.value = null;
        this.color = BLACK; // NIL siempre es negro
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

// Clase principal del árbol rojo-negro
class RBTree<K extends Comparable<K>, V> {
    // NIL es un centinela negro compartido por todos los nodos
    final RBNode<K, V> NIL;
    RBNode<K, V> root;

    // Constructor del árbol
    public RBTree() {
        // Crear el centinela NIL (negro)
        NIL = new RBNode<>();
        // Inicializar la raíz como NIL
        root = NIL;
    }

    // Método para verificar si el árbol está vacío
    public boolean isEmpty() {
        return root == NIL;
    }

    // Método toString para visualizar el árbol
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Árbol vacío";
        }
        StringBuilder sb = new StringBuilder();
        toString(root, sb, "", true);
        return sb.toString();
    }

    private void toString(RBNode<K, V> node, StringBuilder sb, String prefix, boolean isTail) {
        if (node != NIL) {
            sb.append(prefix).append(isTail ? "└── " : "├── ");
            sb.append(node.key).append(":");
            sb.append(node.color == RBNode.RED ? "R" : "N");
            sb.append("\n");
            
            if (node.left != NIL || node.right != NIL) {
                toString(node.left, sb, prefix + (isTail ? "    " : "│   "), false);
                toString(node.right, sb, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }
}

// Clase principal con método main para probar
public class Ej1 {
    public static void main(String[] args) {
        // Crear un árbol rojo-negro
        RBTree<Integer, String> arbol = new RBTree<>();

        // Verificar que el árbol se inicializa correctamente
        System.out.println("Árbol inicializado:");
        System.out.println("¿Está vacío? " + arbol.isEmpty());
        System.out.println("Root apunta a NIL: " + (arbol.root == arbol.NIL));
        System.out.println("Color de NIL: " + (arbol.NIL.color == RBNode.BLACK ? "NEGRO" : "ROJO"));
        System.out.println("\n" + arbol);

        // Crear un nodo de prueba para verificar la estructura
        RBNode<Integer, String> nodo = new RBNode<>(10, "Diez", RBNode.RED, arbol.NIL);
        System.out.println("\nNodo de prueba creado:");
        System.out.println("Key: " + nodo.key);
        System.out.println("Value: " + nodo.value);
        System.out.println("Color: " + (nodo.color == RBNode.RED ? "ROJO" : "NEGRO"));
        System.out.println("Left apunta a NIL: " + (nodo.left == arbol.NIL));
        System.out.println("Right apunta a NIL: " + (nodo.right == arbol.NIL));
        System.out.println("Parent apunta a NIL: " + (nodo.parent == arbol.NIL));
    }
}


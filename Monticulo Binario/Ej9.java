/*
9. Seguimiento del estado interno
Agregá al MinHeap un método:
void printArray();
que imprima el contenido interno del arreglo después de cada operación (add o poll).
Ejecutá una secuencia de inserciones y eliminaciones para observar cómo cambia el heap
internamente.

cd "/home/fabricio-posada/InfoIII/Monticulo Binario" && javac Ej9.java && java Ej9
 */

public class Ej9 {

    static class MinHeap {
        private final int[] heap;
        private int size;
        private final int capacity;
        private boolean modoDebug; // Para controlar si se muestra el array automáticamente

        public MinHeap(int capacity) {
            this(capacity, false);
        }

        public MinHeap(int capacity, boolean modoDebug) {
            this.capacity = capacity;
            this.heap = new int[capacity];
            this.size = 0;
            this.modoDebug = modoDebug;
        }

        private int parent(int i) {
            return (i - 1) / 2;
        }

        private int leftChild(int i) {
            return 2 * i + 1;
        }

        private int rightChild(int i) {
            return 2 * i + 2;
        }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        private void percolateUp(int i) {
            while (i > 0 && heap[parent(i)] > heap[i]) {
                swap(i, parent(i));
                i = parent(i);
            }
        }

        private void percolateDown(int i) {
            while (true) {
                int minIndex = i;
                int left = leftChild(i);
                int right = rightChild(i);

                if (left < size && heap[left] < heap[minIndex]) {
                    minIndex = left;
                }

                if (right < size && heap[right] < heap[minIndex]) {
                    minIndex = right;
                }

                if (i == minIndex) {
                    break;
                }

                swap(i, minIndex);
                i = minIndex;
            }
        }

        // Método add con seguimiento
        public void add(int valor) {
            if (size == capacity) {
                throw new IllegalStateException("Heap está lleno");
            }

            System.out.println("\nADD(" + valor + ")");

            heap[size] = valor;
            System.out.print(" Antes de percolate: ");
            printArray();

            percolateUp(size);
            size++;

            System.out.print(" Después de add:     ");
            printArray();

            if (modoDebug) {
                printTree();
            }
        }

        // Método poll con seguimiento
        public int poll() {
            if (isEmpty()) {
                throw new IllegalStateException("Heap está vacío");
            }

            int min = heap[0];
            System.out.println("\nPOLL() - Eliminando: " + min);

            System.out.print(" Antes de poll:      ");
            printArray();

            size--;
            if (size > 0) {
                heap[0] = heap[size];
                heap[size] = 0; // Limpiar la posición (opcional, para visualización)

                System.out.print(" Último al inicio:   ");
                printArray();

                percolateDown(0);

                System.out.print(" Después de poll:    ");
                printArray();
            } else {
                heap[0] = 0;
                System.out.print(" Heap vacío:         ");
                printArray();
            }

            if (modoDebug) {
                printTree();
            }

            return min;
        }

        public int peek() {
            if (isEmpty()) {
                throw new IllegalStateException("Heap está vacío");
            }
            return heap[0];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // Método printArray() - Imprime el contenido interno del arreglo
        public void printArray() {
            System.out.print("[");
            for (int i = 0; i < capacity; i++) {
                if (i < size) {
                    System.out.print(heap[i]);
                } else {
                    System.out.print("_"); // Posiciones vacías
                }
                if (i < capacity - 1)
                    System.out.print(", ");
            }
            System.out.print("] (size=" + size + ")");
            System.out.println();
        }

        // Método para imprimir solo elementos válidos
        public void printArrayValido() {
            System.out.print("[");
            for (int i = 0; i < size; i++) {
                System.out.print(heap[i]);
                if (i < size - 1)
                    System.out.print(", ");
            }
            System.out.println("]");
        }

        // Visualización en forma de árbol
        public void printTree() {
            System.out.println(" Estructura árbol:");
            if (size == 0) {
                System.out.println("    (vacío)");
                return;
            }

            int nodosEnNivel = 1;
            int index = 0;

            while (index < size) {
                System.out.print("    ");
                for (int i = 0; i < nodosEnNivel && index < size; i++) {
                    System.out.print(heap[index]);
                    if (i < nodosEnNivel - 1 && index + 1 < size) {
                        System.out.print(" ");
                    }
                    index++;
                }
                System.out.println();
                nodosEnNivel *= 2;
            }
        }

        // Método para imprimir estado completo
        public void printEstadoCompleto() {
            System.out.println("┌─ ESTADO DEL HEAP ─────────────────");
            System.out.print("│ Array interno: ");
            printArrayValido();
            System.out.println("│ Tamaño: " + size + " / " + capacity);
            if (!isEmpty()) {
                System.out.println("│ Mínimo (raíz): " + heap[0]);
            }
            System.out.println("│ Estructura:");
            printTreeInterno();
            System.out.println("└───────────────────────────────────");
        }

        private void printTreeInterno() {
            if (size == 0) {
                System.out.println("│   (vacío)");
                return;
            }

            int nodosEnNivel = 1;
            int index = 0;

            while (index < size) {
                System.out.print("│   ");
                for (int i = 0; i < nodosEnNivel && index < size; i++) {
                    System.out.print(heap[index]);
                    if (i < nodosEnNivel - 1 && index + 1 < size) {
                        System.out.print(" ");
                    }
                    index++;
                }
                System.out.println();
                nodosEnNivel *= 2;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 9: SEGUIMIENTO DEL ESTADO INTERNO ===\n");

        // Crear heap con capacidad 10
        MinHeap heap = new MinHeap(10, false);

        System.out.println("SECUENCIA DE OPERACIONES");
        System.out.println("=".repeat(60));

        // Estado inicial
        System.out.println("\nEstado inicial:");
        heap.printArray();

        // Secuencia de inserciones
        System.out.println("\n" + "─".repeat(60));
        System.out.println("FASE 1: INSERCIONES");
        System.out.println("─".repeat(60));

        heap.add(50);
        heap.add(30);
        heap.add(40);
        heap.add(10);
        heap.add(20);
        heap.add(60);
        heap.add(5);

        // Estado después de inserciones
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Estado después de todas las inserciones:");
        heap.printEstadoCompleto();

        // Secuencia de eliminaciones
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FASE 2: ELIMINACIONES");
        System.out.println("=".repeat(60));

        heap.poll();
        heap.poll();
        heap.poll();

        // Estado después de eliminaciones
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Estado después de 3 eliminaciones:");
        heap.printEstadoCompleto();

        // Más inserciones intercaladas
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FASE 3: OPERACIONES INTERCALADAS");
        System.out.println("=".repeat(60));

        heap.add(15);
        heap.add(8);
        heap.poll();
        heap.add(25);

        // Estado final
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ESTADO FINAL:");
        heap.printEstadoCompleto();

        // Vaciar el heap completamente
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FASE 4: VACIANDO EL HEAP");
        System.out.println("=".repeat(60));

        while (!heap.isEmpty()) {
            heap.poll();
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("ESTADO FINAL (vacío):");
        heap.printEstadoCompleto();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("\nOBSERVACIONES:");
        System.out.println(" Cada ADD coloca el elemento al final y percola hacia arriba");
        System.out.println(" Cada POLL mueve el último al inicio y percola hacia abajo");
        System.out.println(" El array muestra todas las posiciones (incluidas las vacías '_')");
        System.out.println(" La propiedad del heap se mantiene en cada operación");
    }
}

/*
3. Implementar percolateDown
Completá el método poll() para que, al eliminar el mínimo (la raíz), el último elemento
suba y luego "percole hacia abajo" hasta restaurar la propiedad del montículo.
Mostrá el contenido del arreglo antes y después de cada eliminación.

cd "/home/fabricio-posada/InfoIII/Monticulo Binario" && javac Ej3.java && java Ej3
 */

public class Ej3 {

    static class MinHeap {
        private final int[] heap;
        private int size;
        private final int capacity;

        public MinHeap(int capacity) {
            this.capacity = capacity;
            this.heap = new int[capacity];
            this.size = 0;
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

        // PercolateUp (para add)
        private void percolateUp(int i) {
            while (i > 0 && heap[parent(i)] > heap[i]) {
                swap(i, parent(i));
                i = parent(i);
            }
        }

        // PercolateDown con visualización detallada
        private void percolateDown(int i) {
            System.out.println(" Percolando hacia abajo desde índice " + i + " (valor: " + heap[i] + ")");

            while (true) {
                int minIndex = i;
                int left = leftChild(i);
                int right = rightChild(i);

                // Verificar hijo izquierdo
                if (left < size && heap[left] < heap[minIndex]) {
                    minIndex = left;
                }

                // Verificar hijo derecho
                if (right < size && heap[right] < heap[minIndex]) {
                    minIndex = right;
                }

                // Si no hay que intercambiar, terminamos
                if (i == minIndex) {
                    System.out.println(" Detenido en índice " + i + " (menor que sus hijos)");
                    break;
                }

                // Mostrar el intercambio
                String childType = (minIndex == left) ? "izquierdo" : "derecho";
                System.out.println(" Intercambio: heap[" + i + "]=" + heap[i] +
                        " ↔ heap[" + minIndex + "]=" + heap[minIndex] +
                        " (hijo " + childType + " es menor)");

                swap(i, minIndex);
                mostrarHeap();
                i = minIndex;
            }
        }

        // Método add
        public void add(int valor) {
            if (size == capacity) {
                throw new IllegalStateException("Heap está lleno");
            }
            heap[size] = valor;
            percolateUp(size);
            size++;
        }

        // Método poll con visualización
        public int poll() {
            if (isEmpty()) {
                throw new IllegalStateException("Heap está vacío");
            }

            System.out.println("\n--- Eliminando el mínimo ---");
            int min = heap[0];
            System.out.println(" Valor a eliminar: " + min + " (raíz)");

            System.out.print(" Estado ANTES:  ");
            mostrarHeap();

            // Mover el último elemento a la raíz
            size--;
            if (size > 0) {
                heap[0] = heap[size];
                System.out.println(" Moviendo último elemento (" + heap[0] + ") a la raíz");
                mostrarHeap();

                // Percolar hacia abajo
                percolateDown(0);
            }

            System.out.print(" Estado DESPUÉS: ");
            mostrarHeap();
            System.out.println(" Eliminación completa, retornando: " + min);

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

        private void mostrarHeap() {
            System.out.print("[");
            for (int i = 0; i < size; i++) {
                System.out.print(heap[i]);
                if (i < size - 1)
                    System.out.print(", ");
            }
            System.out.println("]");
        }

        public void mostrarHeapCompleto() {
            System.out.print("Heap: [");
            for (int i = 0; i < size; i++) {
                System.out.print(heap[i]);
                if (i < size - 1)
                    System.out.print(", ");
            }
            System.out.println("]");
        }

        // Visualización en forma de árbol
        public void mostrarArbol() {
            System.out.println("Árbol:");
            if (size == 0) {
                System.out.println("  (vacío)");
                return;
            }
            mostrarArbolRecursivo(0, "", true);
        }

        private void mostrarArbolRecursivo(int index, String prefix, boolean isRoot) {
            if (index >= size)
                return;

            int right = 2 * index + 2;
            if (right < size) {
                mostrarArbolRecursivo(right, prefix + (isRoot ? "    " : "│   "), false);
            }

            System.out.println(prefix + (isRoot ? "" : "└── ") + heap[index]);

            int left = 2 * index + 1;
            if (left < size) {
                mostrarArbolRecursivo(left, prefix + (isRoot ? "" : "    "), false);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 3: PERCOLATE DOWN ===");
        System.out.println("Demostración del proceso de percolación hacia abajo\n");

        MinHeap minHeap = new MinHeap(10);

        // Insertar valores
        System.out.println("Insertando valores: 5, 10, 15, 20, 25, 30, 35");
        minHeap.add(5);
        minHeap.add(10);
        minHeap.add(15);
        minHeap.add(20);
        minHeap.add(25);
        minHeap.add(30);
        minHeap.add(35);

        System.out.println("\n" + "=".repeat(60));
        minHeap.mostrarHeapCompleto();
        minHeap.mostrarArbol();
        System.out.println("=".repeat(60));

        // Eliminar elementos y mostrar el proceso
        minHeap.poll();
        System.out.println("\n" + "=".repeat(60));
        minHeap.mostrarArbol();
        System.out.println("=".repeat(60));

        minHeap.poll();
        System.out.println("\n" + "=".repeat(60));
        minHeap.mostrarArbol();
        System.out.println("=".repeat(60));

        minHeap.poll();
        System.out.println("\n" + "=".repeat(60));
        minHeap.mostrarArbol();
        System.out.println("=".repeat(60));
    }
}

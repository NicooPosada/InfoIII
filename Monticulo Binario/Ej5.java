/*
5. Construcción desde un arreglo (heapify)
Implementá un constructor alternativo:
public MinHeap(int[] datos)
que reciba un arreglo y construya el heap utilizando el método heapify (bottom-up).
Mostrá paso a paso cómo se reorganiza el arreglo original hasta formar un montículo
válido.

cd "/home/fabricio-posada/InfoIII/Monticulo Binario" && javac Ej5.java && java Ej5
 */

public class Ej5 {

    static class MinHeap {
        private final int[] heap;
        private int size;
        private final int capacity;

        // Constructor original
        public MinHeap(int capacity) {
            this.capacity = capacity;
            this.heap = new int[capacity];
            this.size = 0;
        }

        // Constructor alternativo con heapify (bottom-up)
        public MinHeap(int[] datos) {
            this.capacity = datos.length;
            this.size = datos.length;
            this.heap = new int[capacity];

            // Copiar datos al heap
            System.arraycopy(datos, 0, heap, 0, datos.length);

            System.out.println("=== CONSTRUCCIÓN DEL HEAP (HEAPIFY BOTTOM-UP) ===");
            System.out.print("Arreglo original: ");
            mostrarArray();
            System.out.println("\nIniciando heapify desde el último nodo no-hoja...\n");

            // Heapify: empezar desde el último nodo no-hoja
            // El último nodo no-hoja está en la posición (size/2 - 1)
            for (int i = size / 2 - 1; i >= 0; i--) {
                System.out.println("─".repeat(60));
                System.out.println("Procesando nodo en índice " + i + " (valor: " + heap[i] + ")");
                percolateDownConLog(i);
            }

            System.out.println("=".repeat(60));
            System.out.println("Heapify completado!");
            System.out.print("Heap final: ");
            mostrarArray();
            System.out.println();
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

        // PercolateDown estándar (sin logs)
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

        // PercolateDown con logging para heapify
        private void percolateDownConLog(int i) {
            boolean huboIntercambio = false;
            int posicionInicial = i;

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
                    if (!huboIntercambio) {
                        System.out.println(" Ya cumple la propiedad del heap (menor que sus hijos)");
                    }
                    break;
                }

                // Mostrar el intercambio
                String childType = (minIndex == left) ? "izquierdo" : "derecho";
                System.out.println(" Intercambio: heap[" + i + "]=" + heap[i] +
                        " ↔ heap[" + minIndex + "]=" + heap[minIndex] +
                        " (hijo " + childType + " es menor)");

                swap(i, minIndex);
                huboIntercambio = true;
                System.out.print(" Estado: ");
                mostrarArray();

                i = minIndex;
            }

            if (huboIntercambio) {
                System.out.println(" Percolación completa desde índice " + posicionInicial);
            }
        }

        public void add(int valor) {
            if (size == capacity) {
                throw new IllegalStateException("Heap está lleno");
            }
            heap[size] = valor;
            percolateUp(size);
            size++;
        }

        public int poll() {
            if (isEmpty()) {
                throw new IllegalStateException("Heap está vacío");
            }

            int min = heap[0];
            size--;
            if (size > 0) {
                heap[0] = heap[size];
                percolateDown(0);
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

        private void mostrarArray() {
            System.out.print("[");
            for (int i = 0; i < size; i++) {
                System.out.print(heap[i]);
                if (i < size - 1)
                    System.out.print(", ");
            }
            System.out.println("]");
        }

        public void printTree() {
            if (size == 0) {
                System.out.println("(heap vacío)");
                return;
            }

            int nodosEnNivel = 1;
            int index = 0;

            while (index < size) {
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
        System.out.println("=== EJERCICIO 5: CONSTRUCCIÓN DESDE ARREGLO (HEAPIFY) ===\n");

        // Caso 1: Arreglo desordenado
        System.out.println("CASO 1: Arreglo desordenado");
        int[] datos1 = { 20, 15, 30, 10, 5, 25, 40, 12 };
        MinHeap heap1 = new MinHeap(datos1);

        System.out.println("\nEstructura del heap resultante:");
        heap1.printTree();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Caso 2: Arreglo casi ordenado
        System.out.println("CASO 2: Arreglo ya ordenado ascendentemente");
        int[] datos2 = { 5, 10, 15, 20, 25, 30, 35 };
        MinHeap heap2 = new MinHeap(datos2);

        System.out.println("\nEstructura del heap resultante:");
        heap2.printTree();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Caso 3: Arreglo ordenado descendentemente (peor caso)
        System.out.println("CASO 3: Arreglo ordenado descendentemente");
        int[] datos3 = { 50, 40, 30, 20, 10 };
        MinHeap heap3 = new MinHeap(datos3);

        System.out.println("\nEstructura del heap resultante:");
        heap3.printTree();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("VERIFICACIÓN: Extrayendo elementos en orden");
        System.out.println("=".repeat(60));

        System.out.print("Extrayendo del heap1: ");
        while (!heap1.isEmpty()) {
            System.out.print(heap1.poll() + " ");
        }
        System.out.println("(orden ascendente ✓)");
    }
}

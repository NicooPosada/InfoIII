/*
7. Implementar MaxHeap
Crea una nueva clase MaxHeap similar a MinHeap, pero que mantenga en la raíz el mayor
valor.
Mostrá el orden de eliminación (de mayor a menor) con el arreglo:
{10, 3, 15, 8, 6, 12}.

cd "/home/fabricio-posada/InfoIII/Monticulo Binario" && javac Ej7.java && java Ej7
 */

public class Ej7 {
    
    static class MaxHeap {
        private final int[] heap;
        private int size;
        private final int capacity;
        
        // Constructor básico
        public MaxHeap(int capacity) {
            this.capacity = capacity;
            this.heap = new int[capacity];
            this.size = 0;
        }
        
        // Constructor con heapify (bottom-up)
        public MaxHeap(int[] datos) {
            this.capacity = datos.length;
            this.size = datos.length;
            this.heap = new int[capacity];
            
            System.arraycopy(datos, 0, heap, 0, datos.length);
            
            System.out.println("Construyendo MaxHeap con heapify...");
            System.out.print("Arreglo original: ");
            mostrarArray();
            
            // Heapify desde el último nodo no-hoja
            for (int i = size / 2 - 1; i >= 0; i--) {
                percolateDown(i);
            }
            
            System.out.print("MaxHeap resultante: ");
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
        
        // PercolateUp - DIFERENCIA: comparamos con > en lugar de <
        private void percolateUp(int i) {
            while (i > 0 && heap[parent(i)] < heap[i]) {  // Cambio clave: < en lugar de >
                swap(i, parent(i));
                i = parent(i);
            }
        }
        
        // PercolateDown - DIFERENCIA: buscamos el MAYOR hijo, no el menor
        private void percolateDown(int i) {
            while (true) {
                int maxIndex = i;
                int left = leftChild(i);
                int right = rightChild(i);
                
                // Buscar el hijo MAYOR (cambio clave)
                if (left < size && heap[left] > heap[maxIndex]) {  // > en lugar de <
                    maxIndex = left;
                }
                
                if (right < size && heap[right] > heap[maxIndex]) {  // > en lugar de <
                    maxIndex = right;
                }
                
                // Si no hay que intercambiar, terminamos
                if (i == maxIndex) {
                    break;
                }
                
                swap(i, maxIndex);
                i = maxIndex;
            }
        }
        
        // Agregar elemento
        public void add(int valor) {
            if (size == capacity) {
                throw new IllegalStateException("Heap está lleno");
            }
            
            System.out.println("  → Insertando: " + valor);
            heap[size] = valor;
            percolateUp(size);
            size++;
            System.out.print("     Estado: ");
            mostrarArray();
        }
        
        // Eliminar el MÁXIMO (raíz)
        public int poll() {
            if (isEmpty()) {
                throw new IllegalStateException("Heap está vacío");
            }
            
            int max = heap[0];
            size--;
            
            if (size > 0) {
                heap[0] = heap[size];
                percolateDown(0);
            }
            
            return max;
        }
        
        // Ver el MÁXIMO sin eliminarlo
        public int peek() {
            if (isEmpty()) {
                throw new IllegalStateException("Heap está vacío");
            }
            return heap[0];
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public int getSize() {
            return size;
        }
        
        private void mostrarArray() {
            System.out.print("[");
            for (int i = 0; i < size; i++) {
                System.out.print(heap[i]);
                if (i < size - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
        
        // Mostrar heap por niveles
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
        
        // Visualización en forma de árbol
        public void printTreeDetallado() {
            System.out.println("Estructura del MaxHeap:");
            if (size == 0) {
                System.out.println("  (vacío)");
                return;
            }
            printTreeRecursivo(0, "", true);
        }
        
        private void printTreeRecursivo(int index, String prefix, boolean isRoot) {
            if (index >= size) return;
            
            int right = 2 * index + 2;
            if (right < size) {
                printTreeRecursivo(right, prefix + (isRoot ? "    " : "│   "), false);
            }
            
            System.out.println(prefix + (isRoot ? "" : "└── ") + heap[index]);
            
            int left = 2 * index + 1;
            if (left < size) {
                printTreeRecursivo(left, prefix + (isRoot ? "" : "    "), false);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 7: MAXHEAP ===\n");
        
        // CASO 1: Arreglo del enunciado
        System.out.println("CASO 1: Arreglo del enunciado {10, 3, 15, 8, 6, 12}");
        System.out.println("─".repeat(60));
        int[] arr1 = {10, 3, 15, 8, 6, 12};
        MaxHeap maxHeap1 = new MaxHeap(arr1);
        
        System.out.println("\nEstructura del MaxHeap:");
        maxHeap1.printTree();
        
        System.out.println("\n" + "─".repeat(60));
        System.out.println("Eliminando elementos (de MAYOR a MENOR):");
        System.out.println("─".repeat(60));
        
        while (!maxHeap1.isEmpty()) {
            int max = maxHeap1.poll();
            System.out.print("Eliminado: " + max);
            if (!maxHeap1.isEmpty()) {
                System.out.print(" → Heap restante: ");
                maxHeap1.printTree();
            } else {
                System.out.println(" → Heap vacío");
            }
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // CASO 2: Insertando uno por uno
        System.out.println("CASO 2: Insertando elementos uno por uno");
        System.out.println("─".repeat(60));
        MaxHeap maxHeap2 = new MaxHeap(10);
        
        int[] valores = {10, 3, 15, 8, 6, 12};
        for (int valor : valores) {
            maxHeap2.add(valor);
        }
        
        System.out.println("\nEstructura final:");
        maxHeap2.printTreeDetallado();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // CASO 3: Comparación MinHeap vs MaxHeap
        System.out.println("CASO 3: Comparación - mismo arreglo");
        System.out.println("─".repeat(60));
        int[] arr3 = {5, 2, 8, 1, 9, 3};
        
        MaxHeap maxHeap3 = new MaxHeap(arr3.clone());
        System.out.println("\nOrden de eliminación en MaxHeap (mayor a menor):");
        System.out.print("  ");
        while (!maxHeap3.isEmpty()) {
            System.out.print(maxHeap3.poll());
            if (!maxHeap3.isEmpty()) System.out.print(" → ");
        }
        System.out.println();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("\n✓ DIFERENCIAS CLAVE MinHeap vs MaxHeap:");
        System.out.println("  • MinHeap: raíz = MÍNIMO | percolateUp/Down compara con <");
        System.out.println("  • MaxHeap: raíz = MÁXIMO | percolateUp/Down compara con >");
        System.out.println("\n✓ USO:");
        System.out.println("  • MinHeap: obtener mínimos rápidamente (priority queues)");
        System.out.println("  • MaxHeap: obtener máximos rápidamente, HeapSort descendente");
    }
}

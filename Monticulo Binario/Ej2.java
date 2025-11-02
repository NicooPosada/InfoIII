/*
2. Implementar percolateUp
Completá el método add(int valor) para que, al insertar un nuevo elemento, este
"percole hacia arriba" manteniendo la propiedad del montículo.
Mostrá por consola cada intercambio que se realiza durante la inserción.

cd "/home/fabricio-posada/InfoIII/Monticulo Binario" && javac Ej2.java && java Ej2
 */

public class Ej2 {
    
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
        
        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
        
        // PercolateUp con visualización de intercambios
        private void percolateUp(int i) {
            System.out.println("  → Percolando hacia arriba desde índice " + i + " (valor: " + heap[i] + ")");
            
            while (i > 0 && heap[parent(i)] > heap[i]) {
                int parentIndex = parent(i);
                System.out.println("    ✓ Intercambio: heap[" + i + "]=" + heap[i] + 
                                 " ↔ heap[" + parentIndex + "]=" + heap[parentIndex] + 
                                 " (padre es mayor)");
                
                swap(i, parentIndex);
                mostrarHeap();
                i = parentIndex;
            }
            
            if (i == 0) {
                System.out.println("    • Llegó a la raíz (índice 0)");
            } else {
                System.out.println("    • Detenido en índice " + i + " (padre es menor o igual)");
            }
        }
        
        // Método add que usa percolateUp
        public void add(int valor) {
            if (size == capacity) {
                throw new IllegalStateException("Heap está lleno");
            }
            
            System.out.println("\n--- Insertando valor: " + valor + " ---");
            heap[size] = valor;
            System.out.println("  → Insertado en índice " + size);
            size++;
            mostrarHeap();
            
            percolateUp(size - 1);
            
            System.out.println("  ✓ Inserción completa");
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public int peek() {
            if (isEmpty()) {
                throw new IllegalStateException("Heap está vacío");
            }
            return heap[0];
        }
        
        private void mostrarHeap() {
            System.out.print("    Estado: [");
            for (int i = 0; i < size; i++) {
                System.out.print(heap[i]);
                if (i < size - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
        
        public void mostrarHeapCompleto() {
            System.out.print("Heap final: [");
            for (int i = 0; i < size; i++) {
                System.out.print(heap[i]);
                if (i < size - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
        
        // Visualización en forma de árbol
        public void mostrarArbol() {
            System.out.println("\nRepresentación en árbol:");
            if (size == 0) {
                System.out.println("  (vacío)");
                return;
            }
            mostrarArbolRecursivo(0, "", true);
        }
        
        private void mostrarArbolRecursivo(int index, String prefix, boolean isRoot) {
            if (index >= size) return;
            
            // Mostrar hijo derecho
            int right = 2 * index + 2;
            if (right < size) {
                mostrarArbolRecursivo(right, prefix + (isRoot ? "    " : "│   "), false);
            }
            
            // Mostrar nodo actual
            System.out.println(prefix + (isRoot ? "" : "└── ") + heap[index]);
            
            // Mostrar hijo izquierdo
            int left = 2 * index + 1;
            if (left < size) {
                mostrarArbolRecursivo(left, prefix + (isRoot ? "" : "    "), false);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 2: PERCOLATE UP ===");
        System.out.println("Demostración del proceso de percolación hacia arriba\n");
        
        MinHeap minHeap = new MinHeap(10);
        
        // Insertar valores y mostrar el proceso completo
        minHeap.add(50);
        minHeap.add(30);
        minHeap.add(40);
        minHeap.add(10);  // Este va a percolar mucho
        minHeap.add(20);
        minHeap.add(5);   // Este va a llegar a la raíz
        
        System.out.println("\n" + "=".repeat(50));
        minHeap.mostrarHeapCompleto();
        System.out.println("Mínimo actual: " + minHeap.peek());
        minHeap.mostrarArbol();
    }
}

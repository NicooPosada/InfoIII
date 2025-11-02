/*
6. Implementar Heapsort
Utilizando tu clase MinHeap, escribí un método estático:
public static void heapsort(int[] arr)
que ordene un arreglo de forma ascendente utilizando el montículo binario (insertando
todos los elementos y extrayéndolos en orden).
Probalo con el arreglo:
int[] arr = {9, 4, 7, 1, 6, 2};

cd "/home/fabricio-posada/InfoIII/Monticulo Binario" && javac Ej6.java && java Ej6
 */

public class Ej6 {
    
    static class MinHeap {
        private final int[] heap;
        private int size;
        private final int capacity;
        
        public MinHeap(int capacity) {
            this.capacity = capacity;
            this.heap = new int[capacity];
            this.size = 0;
        }
        
        // Constructor con heapify (bottom-up) - más eficiente O(n)
        public MinHeap(int[] datos) {
            this.capacity = datos.length;
            this.size = datos.length;
            this.heap = new int[capacity];
            
            System.arraycopy(datos, 0, heap, 0, datos.length);
            
            // Heapify desde el último nodo no-hoja
            for (int i = size / 2 - 1; i >= 0; i--) {
                percolateDown(i);
            }
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
        
        public int getSize() {
            return size;
        }
    }
    
    // Método heapsort - versión con inserción uno por uno
    public static void heapsort(int[] arr) {
        System.out.println("=== HEAPSORT (con inserciones) ===");
        System.out.print("Arreglo original: ");
        mostrarArreglo(arr);
        
        // Crear heap e insertar todos los elementos
        MinHeap heap = new MinHeap(arr.length);
        
        System.out.println("\nInsertando elementos al heap:");
        for (int i = 0; i < arr.length; i++) {
            System.out.println("  → Insertando: " + arr[i]);
            heap.add(arr[i]);
        }
        
        // Extraer elementos en orden
        System.out.println("\nExtrayendo elementos en orden:");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = heap.poll();
            System.out.println("  → Extraído: " + arr[i]);
        }
        
        System.out.print("\nArreglo ordenado: ");
        mostrarArreglo(arr);
        System.out.println();
    }
    
    // Método heapsort optimizado - versión con heapify (más eficiente)
    public static void heapsortOptimizado(int[] arr) {
        System.out.println("=== HEAPSORT OPTIMIZADO (con heapify) ===");
        System.out.print("Arreglo original: ");
        mostrarArreglo(arr);
        
        // Crear heap usando heapify (O(n) en lugar de O(n log n))
        System.out.println("\nConstruyendo heap con heapify (bottom-up)...");
        MinHeap heap = new MinHeap(arr);
        System.out.println("✓ Heap construido");
        
        // Extraer elementos en orden
        System.out.println("\nExtrayendo elementos en orden:");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = heap.poll();
            System.out.println("  → Extraído: " + arr[i]);
        }
        
        System.out.print("\nArreglo ordenado: ");
        mostrarArreglo(arr);
        System.out.println();
    }
    
    // Método auxiliar para mostrar arreglo
    private static void mostrarArreglo(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 6: HEAPSORT ===\n");
        
        // Caso 1: Arreglo del enunciado
        System.out.println("CASO 1: Arreglo del enunciado");
        System.out.println("─".repeat(60));
        int[] arr1 = {9, 4, 7, 1, 6, 2};
        heapsort(arr1);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Caso 2: Mismo arreglo con método optimizado
        System.out.println("CASO 2: Mismo arreglo con heapsort optimizado");
        System.out.println("─".repeat(60));
        int[] arr2 = {9, 4, 7, 1, 6, 2};
        heapsortOptimizado(arr2);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Caso 3: Arreglo más grande
        System.out.println("CASO 3: Arreglo más grande");
        System.out.println("─".repeat(60));
        int[] arr3 = {50, 30, 20, 15, 10, 8, 16, 25, 40, 35};
        heapsortOptimizado(arr3);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Caso 4: Arreglo ya ordenado
        System.out.println("CASO 4: Arreglo ya ordenado");
        System.out.println("─".repeat(60));
        int[] arr4 = {1, 2, 3, 4, 5};
        heapsortOptimizado(arr4);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Caso 5: Arreglo ordenado al revés
        System.out.println("CASO 5: Arreglo ordenado descendentemente");
        System.out.println("─".repeat(60));
        int[] arr5 = {10, 8, 6, 4, 2};
        heapsortOptimizado(arr5);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("\n✓ Todos los casos de prueba completados");
        System.out.println("\nComplejidad temporal:");
        System.out.println("  • heapsort():           O(n log n) - construcción con inserciones");
        System.out.println("  • heapsortOptimizado(): O(n log n) - pero usa heapify O(n) inicial");
    }
}

/*
PRÁCTICO MONTÍCULO BINARIO
1. Crear estructura básica de MinHeap
Implementá una clase MinHeap en Java que permita almacenar enteros en un montículo
binario mínimo utilizando un arreglo.
Debe contener al menos:
int[] heap;
int size;
y los métodos:
void add(int valor);
int poll();
int peek();
boolean isEmpty();

Probá agregar los valores: 20, 5, 15, 3, 11 y mostrálos en orden de extracción.

*/

public class Ej1 {
    
    static class MinHeap {
        private final int[] heap;
        private int size;
        private final int capacity;
        
        public MinHeap(int capacity) {
            this.capacity = capacity;
            this.heap = new int[capacity];
            this.size = 0;
        }
        
        // Métodos auxiliares para navegar el árbol
        private int parent(int i) {
            return (i - 1) / 2;
        }
        
        private int leftChild(int i) {
            return 2 * i + 1;
        }
        
        private int rightChild(int i) {
            return 2 * i + 2;
        }
        
        // Intercambiar dos elementos
        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
        
        // Subir un elemento (heapify up)
        private void heapifyUp(int i) {
            while (i > 0 && heap[parent(i)] > heap[i]) {
                swap(i, parent(i));
                i = parent(i);
            }
        }
        
        // Bajar un elemento (heapify down)
        private void heapifyDown(int i) {
            int minIndex = i;
            int left = leftChild(i);
            int right = rightChild(i);
            
            if (left < size && heap[left] < heap[minIndex]) {
                minIndex = left;
            }
            
            if (right < size && heap[right] < heap[minIndex]) {
                minIndex = right;
            }
            
            if (i != minIndex) {
                swap(i, minIndex);
                heapifyDown(minIndex);
            }
        }
        
        // Agregar un valor
        public void add(int valor) {
            if (size == capacity) {
                throw new IllegalStateException("Heap está lleno");
            }
            heap[size] = valor;
            heapifyUp(size);
            size++;
        }
        
        // Extraer el mínimo
        public int poll() {
            if (isEmpty()) {
                throw new IllegalStateException("Heap está vacío");
            }
            int min = heap[0];
            heap[0] = heap[size - 1];
            size--;
            heapifyDown(0);
            return min;
        }
        
        // Ver el mínimo sin extraer
        public int peek() {
            if (isEmpty()) {
                throw new IllegalStateException("Heap está vacío");
            }
            return heap[0];
        }
        
        // Verificar si está vacío
        public boolean isEmpty() {
            return size == 0;
        }
        
        // Método para visualizar el heap
        public void mostrar() {
            System.out.print("Heap: [");
            for (int i = 0; i < size; i++) {
                System.out.print(heap[i]);
                if (i < size - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== MONTÍCULO BINARIO MÍNIMO ===\n");
        
        MinHeap minHeap = new MinHeap(10);
        
        // Agregar valores: 20, 5, 15, 3, 11
        System.out.println("Agregando valores: 20, 5, 15, 3, 11");
        minHeap.add(20);
        System.out.print("Después de agregar 20: ");
        minHeap.mostrar();
        
        minHeap.add(5);
        System.out.print("Después de agregar 5:  ");
        minHeap.mostrar();
        
        minHeap.add(15);
        System.out.print("Después de agregar 15: ");
        minHeap.mostrar();
        
        minHeap.add(3);
        System.out.print("Después de agregar 3:  ");
        minHeap.mostrar();
        
        minHeap.add(11);
        System.out.print("Después de agregar 11: ");
        minHeap.mostrar();
        
        // Extraer en orden
        System.out.println("\n--- Extrayendo valores (orden de menor a mayor) ---");
        while (!minHeap.isEmpty()) {
            int valor = minHeap.poll();
            System.out.println("Extraído: " + valor);
            if (!minHeap.isEmpty()) {
                System.out.print("Heap restante: ");
                minHeap.mostrar();
            }
        }
        
        System.out.println("\n✓ Heap vacío: " + minHeap.isEmpty());
    }
}

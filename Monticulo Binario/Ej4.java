/*
4. Mostrar el heap en forma de árbol
Agregá a la clase un método:
void printTree();

que muestre el montículo en forma jerárquica (una línea por nivel), para visualizar su
estructura después de cada inserción y eliminación.
Ejemplo de salida:
3
5 15
20 11

cd "/home/fabricio-posada/InfoIII/Monticulo Binario" && javac Ej4.java && java Ej4
 */

public class Ej4 {
    
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
        
        // Método printTree() - Mostrar heap por niveles
        public void printTree() {
            if (size == 0) {
                System.out.println("(heap vacío)");
                return;
            }
            
            int nodosEnNivel = 1;
            int index = 0;
            
            while (index < size) {
                // Imprimir nodos del nivel actual
                for (int i = 0; i < nodosEnNivel && index < size; i++) {
                    System.out.print(heap[index]);
                    if (i < nodosEnNivel - 1 && index + 1 < size) {
                        System.out.print(" ");
                    }
                    index++;
                }
                System.out.println();
                
                // El siguiente nivel tiene el doble de nodos
                nodosEnNivel *= 2;
            }
        }
        
        // Método alternativo con más detalles visuales
        public void printTreeDetallado() {
            if (size == 0) {
                System.out.println("(heap vacío)");
                return;
            }
            
            int nivel = 0;
            int nodosEnNivel = 1;
            int index = 0;
            
            while (index < size) {
                System.out.print("Nivel " + nivel + ": ");
                
                for (int i = 0; i < nodosEnNivel && index < size; i++) {
                    System.out.print(heap[index]);
                    if (i < nodosEnNivel - 1 && index + 1 < size) {
                        System.out.print(" - ");
                    }
                    index++;
                }
                System.out.println();
                
                nodosEnNivel *= 2;
                nivel++;
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 4: MOSTRAR HEAP EN FORMA DE ÁRBOL ===\n");
        
        MinHeap minHeap = new MinHeap(15);
        
        // Ejemplo del enunciado: 3, 5, 15, 20, 11
        System.out.println("Insertando: 3");
        minHeap.add(3);
        minHeap.printTree();
        System.out.println();
        
        System.out.println("Insertando: 5");
        minHeap.add(5);
        minHeap.printTree();
        System.out.println();
        
        System.out.println("Insertando: 15");
        minHeap.add(15);
        minHeap.printTree();
        System.out.println();
        
        System.out.println("Insertando: 20");
        minHeap.add(20);
        minHeap.printTree();
        System.out.println();
        
        System.out.println("Insertando: 11");
        minHeap.add(11);
        minHeap.printTree();
        System.out.println();
        
        System.out.println("=".repeat(50));
        System.out.println("\nAhora agregamos más valores...\n");
        
        System.out.println("Insertando: 8");
        minHeap.add(8);
        minHeap.printTree();
        System.out.println();
        
        System.out.println("Insertando: 25");
        minHeap.add(25);
        minHeap.printTree();
        System.out.println();
        
        System.out.println("Insertando: 1");
        minHeap.add(1);
        minHeap.printTree();
        System.out.println();
        
        System.out.println("=".repeat(50));
        System.out.println("\nEliminando elementos (poll)...\n");
        
        System.out.println("Eliminando mínimo: " + minHeap.poll());
        minHeap.printTree();
        System.out.println();
        
        System.out.println("Eliminando mínimo: " + minHeap.poll());
        minHeap.printTree();
        System.out.println();
        
        System.out.println("Eliminando mínimo: " + minHeap.poll());
        minHeap.printTree();
        System.out.println();
        
        System.out.println("=".repeat(50));
        System.out.println("\nVISUALIZACIÓN DETALLADA:");
        System.out.println("=".repeat(50));
        minHeap.printTreeDetallado();
    }
}

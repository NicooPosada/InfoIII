/*
10. Integrador – Agenda de tareas con prioridad
Implementá un programa completo que gestione una agenda de tareas usando un
MinHeap.
Cada tarea tiene:
class Tarea {
String descripcion;
int prioridad; // menor número = más urgente
}

La aplicación debe permitir (desde main):
1. Agregar tareas.
2. Ver la próxima tarea urgente (peek).
3. Completar la tarea más urgente (poll).
4. Mostrar todas las tareas pendientes en orden de prioridad.

📈 Desafío extra: Mostrar el heap visualmente después de cada operación.

cd "/home/fabricio-posada/InfoIII/Monticulo Binario" && javac Ej10.java && java Ej10
 */

import java.util.Scanner;

public class Ej10 {
    
    // Clase Tarea
    static class Tarea {
        String descripcion;
        int prioridad; // menor número = más urgente
        int numeroCreacion; // Para desempatar tareas con misma prioridad
        
        public Tarea(String descripcion, int prioridad, int numeroCreacion) {
            this.descripcion = descripcion;
            this.prioridad = prioridad;
            this.numeroCreacion = numeroCreacion;
        }
        
        @Override
        public String toString() {
            return "[P" + prioridad + "] " + descripcion;
        }
        
        public String toStringDetallado() {
            String urgencia;
            if (prioridad <= 1) urgencia = " URGENTE";
            else if (prioridad <= 3) urgencia = " ALTA";
            else if (prioridad <= 5) urgencia = " MEDIA";
            else urgencia = " BAJA";
            
            return urgencia + " - " + descripcion + " (Prioridad: " + prioridad + ")";
        }
    }
    
    // Agenda de Tareas usando MinHeap
    static class AgendaTareas {
        private final Tarea[] heap;
        private int size;
        private final int capacity;
        private int contadorTareas;
        
        public AgendaTareas(int capacity) {
            this.capacity = capacity;
            this.heap = new Tarea[capacity];
            this.size = 0;
            this.contadorTareas = 0;
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
            Tarea temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
        
        // Comparar tareas: primero por prioridad, luego por orden de creación
        private boolean esMasUrgente(Tarea t1, Tarea t2) {
            if (t1.prioridad != t2.prioridad) {
                return t1.prioridad < t2.prioridad; // Menor número = más urgente
            }
            return t1.numeroCreacion < t2.numeroCreacion; // Primera creada es más urgente
        }
        
        private void percolateUp(int i) {
            while (i > 0 && esMasUrgente(heap[i], heap[parent(i)])) {
                swap(i, parent(i));
                i = parent(i);
            }
        }
        
        private void percolateDown(int i) {
            while (true) {
                int masUrgenteIndex = i;
                int left = leftChild(i);
                int right = rightChild(i);
                
                if (left < size && esMasUrgente(heap[left], heap[masUrgenteIndex])) {
                    masUrgenteIndex = left;
                }
                
                if (right < size && esMasUrgente(heap[right], heap[masUrgenteIndex])) {
                    masUrgenteIndex = right;
                }
                
                if (i == masUrgenteIndex) {
                    break;
                }
                
                swap(i, masUrgenteIndex);
                i = masUrgenteIndex;
            }
        }
        
        // 1. Agregar tarea
        public void agregarTarea(String descripcion, int prioridad) {
            if (size == capacity) {
                System.out.println(" Error: Agenda llena, no se pueden agregar más tareas");
                return;
            }
            
            Tarea nuevaTarea = new Tarea(descripcion, prioridad, contadorTareas++);
            heap[size] = nuevaTarea;
            percolateUp(size);
            size++;
            
            System.out.println("✓ Tarea agregada: " + nuevaTarea.toStringDetallado());
        }
        
        // 2. Ver próxima tarea urgente (peek)
        public Tarea verProximaTarea() {
            if (isEmpty()) {
                System.out.println(" No hay tareas pendientes");
                return null;
            }
            return heap[0];
        }
        
        // 3. Completar tarea más urgente (poll)
        public Tarea completarTarea() {
            if (isEmpty()) {
                System.out.println(" No hay tareas para completar");
                return null;
            }
            
            Tarea tareaCompletada = heap[0];
            size--;
            
            if (size > 0) {
                heap[0] = heap[size];
                percolateDown(0);
            }
            
            System.out.println(" Tarea completada: " + tareaCompletada.toStringDetallado());
            return tareaCompletada;
        }
        
        // 4. Mostrar todas las tareas pendientes en orden de prioridad
        public void mostrarTareasEnOrden() {
            if (isEmpty()) {
                System.out.println(" No hay tareas pendientes");
                return;
            }
            
            System.out.println("\n TAREAS PENDIENTES (en orden de prioridad):");
            System.out.println("─".repeat(70));
            
            // Crear copia del heap para no modificar el original
            Tarea[] copia = new Tarea[size];
            System.arraycopy(heap, 0, copia, 0, size);
            int sizeCopia = size;
            
            int numero = 1;
            while (sizeCopia > 0) {
                // Extraer el mínimo de la copia
                Tarea tarea = copia[0];
                System.out.println(numero + ". " + tarea.toStringDetallado());
                
                // Reorganizar la copia
                sizeCopia--;
                if (sizeCopia > 0) {
                    copia[0] = copia[sizeCopia];
                    percolateDownCopia(copia, 0, sizeCopia);
                }
                numero++;
            }
            System.out.println("─".repeat(70));
        }
        
        private void percolateDownCopia(Tarea[] arr, int i, int tamaño) {
            while (true) {
                int masUrgenteIndex = i;
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                
                if (left < tamaño && esMasUrgente(arr[left], arr[masUrgenteIndex])) {
                    masUrgenteIndex = left;
                }
                
                if (right < tamaño && esMasUrgente(arr[right], arr[masUrgenteIndex])) {
                    masUrgenteIndex = right;
                }
                
                if (i == masUrgenteIndex) {
                    break;
                }
                
                Tarea temp = arr[i];
                arr[i] = arr[masUrgenteIndex];
                arr[masUrgenteIndex] = temp;
                i = masUrgenteIndex;
            }
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public int getSize() {
            return size;
        }
        
        // Desafío extra: Mostrar heap visualmente
        public void mostrarHeapVisual() {
            if (isEmpty()) {
                System.out.println("(heap vacío)");
                return;
            }
            
            System.out.println("\n VISUALIZACIÓN DEL HEAP:");
            System.out.println("─".repeat(70));
            mostrarArbolRecursivo(0, "", true);
            System.out.println("─".repeat(70));
        }
        
        private void mostrarArbolRecursivo(int index, String prefix, boolean isRoot) {
            if (index >= size) return;
            
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
        try (Scanner scanner = new Scanner(System.in)) {
            AgendaTareas agenda = new AgendaTareas(50);
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║       AGENDA DE TAREAS CON PRIORIDAD - MinHeap                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println("\nPrioridades: 1 = Urgente | 2-3 = Alta | 4-5 = Media | 6+ = Baja\n");
        
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\n" + "═".repeat(70));
            System.out.println("MENÚ DE OPCIONES");
            System.out.println("═".repeat(70));
            System.out.println("1.  Agregar tarea");
            System.out.println("2.  Ver próxima tarea urgente");
            System.out.println("3.  Completar tarea más urgente");
            System.out.println("4.  Mostrar todas las tareas en orden de prioridad");
            System.out.println("5.  Mostrar heap visualmente");
            System.out.println("6.  Estadísticas");
            System.out.println("0.  Salir");
            System.out.println("═".repeat(70));
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            System.out.println();
            
            switch (opcion) {
                case 1:
                    System.out.println("─── AGREGAR NUEVA TAREA ───");
                    System.out.print("Descripción de la tarea: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Prioridad (1=más urgente, 10=menos urgente): ");
                    int prioridad = scanner.nextInt();
                    scanner.nextLine();
                    
                    agenda.agregarTarea(descripcion, prioridad);
                    agenda.mostrarHeapVisual();
                    break;
                    
                case 2:
                    System.out.println("─── PRÓXIMA TAREA URGENTE ───");
                    Tarea proxima = agenda.verProximaTarea();
                    if (proxima != null) {
                        System.out.println(" Próxima tarea: " + proxima.toStringDetallado());
                    }
                    break;
                    
                case 3:
                    System.out.println("─── COMPLETAR TAREA ───");
                    agenda.completarTarea();
                    if (!agenda.isEmpty()) {
                        agenda.mostrarHeapVisual();
                    } else {
                        System.out.println(" ¡Todas las tareas completadas!");
                    }
                    break;
                    
                case 4:
                    agenda.mostrarTareasEnOrden();
                    break;
                    
                case 5:
                    agenda.mostrarHeapVisual();
                    break;
                    
                case 6:
                    System.out.println("─── ESTADÍSTICAS ───");
                    System.out.println(" Tareas pendientes: " + agenda.getSize());
                    if (!agenda.isEmpty()) {
                        Tarea siguiente = agenda.verProximaTarea();
                        System.out.println(" Siguiente tarea: " + siguiente);
                    }
                    break;
                    
                case 0:
                    System.out.println(" ¡Hasta luego!");
                    continuar = false;
                    break;
                    
                default:
                    System.out.println(" Opción inválida");
            }
        }
        
        System.out.println("\n" + "═".repeat(70));
        System.out.println("✓ Programa finalizado");
        System.out.println("═".repeat(70));
        }
    }
}

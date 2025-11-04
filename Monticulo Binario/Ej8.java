/*
8. Cola de prioridad de pacientes

Creá una clase:
class Paciente {
String nombre;
int prioridad; // 1 = alta, 2 = media, 3 = baja
}

y usá un MinHeap para implementar una cola de prioridad médica con los métodos:
void ingresar(Paciente p);
Paciente atender();
Simulá el ingreso y atención de 5 pacientes, mostrando el orden de atención.

cd "/home/fabricio-posada/InfoIII/Monticulo Binario" && javac Ej8.java && java Ej8
 */

public class Ej8 {

    // Clase Paciente
    static class Paciente {
        String nombre;
        int prioridad; // 1 = alta, 2 = media, 3 = baja
        int numeroIngreso; // Para desempatar pacientes con misma prioridad (FIFO)

        public Paciente(String nombre, int prioridad, int numeroIngreso) {
            this.nombre = nombre;
            this.prioridad = prioridad;
            this.numeroIngreso = numeroIngreso;
        }

        @Override
        public String toString() {
            String nivelPrioridad = switch (prioridad) {
                case 1 -> "ALTA";
                case 2 -> "MEDIA";
                case 3 -> "BAJA";
                default -> "DESCONOCIDA";
            };
            return nombre + " (Prioridad: " + nivelPrioridad + ")";
        }
    }

    // Cola de Prioridad usando MinHeap
    static class ColaPrioridadPacientes {
        private final Paciente[] heap;
        private int size;
        private final int capacity;
        private int contadorIngreso; // Para asignar número de ingreso

        public ColaPrioridadPacientes(int capacity) {
            this.capacity = capacity;
            this.heap = new Paciente[capacity];
            this.size = 0;
            this.contadorIngreso = 0;
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
            Paciente temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        // Comparar pacientes: primero por prioridad, luego por orden de llegada
        private boolean tieneMayorPrioridad(Paciente p1, Paciente p2) {
            if (p1.prioridad != p2.prioridad) {
                return p1.prioridad < p2.prioridad; // Menor número = mayor prioridad
            }
            // Si tienen la misma prioridad, el que llegó primero tiene mayor prioridad
            return p1.numeroIngreso < p2.numeroIngreso;
        }

        private void percolateUp(int i) {
            while (i > 0 && tieneMayorPrioridad(heap[i], heap[parent(i)])) {
                swap(i, parent(i));
                i = parent(i);
            }
        }

        private void percolateDown(int i) {
            while (true) {
                int maxPriorityIndex = i;
                int left = leftChild(i);
                int right = rightChild(i);

                if (left < size && tieneMayorPrioridad(heap[left], heap[maxPriorityIndex])) {
                    maxPriorityIndex = left;
                }

                if (right < size && tieneMayorPrioridad(heap[right], heap[maxPriorityIndex])) {
                    maxPriorityIndex = right;
                }

                if (i == maxPriorityIndex) {
                    break;
                }

                swap(i, maxPriorityIndex);
                i = maxPriorityIndex;
            }
        }

        // Ingresar paciente a la cola
        public void ingresar(Paciente p) {
            if (size == capacity) {
                throw new IllegalStateException("Cola de prioridad está llena");
            }

            p.numeroIngreso = contadorIngreso++;
            heap[size] = p;
            percolateUp(size);
            size++;

            System.out.println(" Ingresó: " + p);
            System.out.println(" Pacientes en espera: " + size);
        }

        // Atender al paciente con mayor prioridad
        public Paciente atender() {
            if (isEmpty()) {
                System.out.println(" No hay pacientes para atender");
                return null;
            }

            Paciente pacienteAtendido = heap[0];
            size--;

            if (size > 0) {
                heap[0] = heap[size];
                percolateDown(0);
            }

            System.out.println(" Atendiendo: " + pacienteAtendido);
            System.out.println(" Pacientes restantes: " + size);

            return pacienteAtendido;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int getSize() {
            return size;
        }

        // Mostrar todos los pacientes en la cola (sin orden específico)
        public void mostrarCola() {
            if (isEmpty()) {
                System.out.println("  (cola vacía)");
                return;
            }

            System.out.println(" Pacientes en cola:");
            for (int i = 0; i < size; i++) {
                System.out.println("    " + (i + 1) + ". " + heap[i]);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 8: COLA DE PRIORIDAD DE PACIENTES ===\n");

        ColaPrioridadPacientes cola = new ColaPrioridadPacientes(20);

        System.out.println("SIMULACIÓN DE ATENCIÓN EN HOSPITAL");
        System.out.println("=".repeat(60));
        System.out.println("Prioridades: 1 = ALTA | 2 = MEDIA | 3 = BAJA\n");

        // Ingreso de pacientes
        System.out.println("─── INGRESO DE PACIENTES ───\n");

        cola.ingresar(new Paciente("Juan Pérez", 3, 0)); // Baja
        System.out.println();

        cola.ingresar(new Paciente("María García", 1, 0)); // Alta
        System.out.println();

        cola.ingresar(new Paciente("Pedro López", 2, 0)); // Media
        System.out.println();

        cola.ingresar(new Paciente("Ana Martínez", 1, 0)); // Alta
        System.out.println();

        cola.ingresar(new Paciente("Carlos Rodríguez", 3, 0)); // Baja
        System.out.println();

        cola.ingresar(new Paciente("Laura Fernández", 2, 0)); // Media
        System.out.println();

        cola.ingresar(new Paciente("Roberto Sánchez", 1, 0)); // Alta
        System.out.println();

        System.out.println("=".repeat(60));
        cola.mostrarCola();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("─── ATENCIÓN DE PACIENTES ───\n");

        int orden = 1;
        while (!cola.isEmpty()) {
            System.out.println("Paciente #" + orden + ":");
            cola.atender();
            System.out.println();
            orden++;
        }

        System.out.println("=".repeat(60));
        System.out.println("Todos los pacientes han sido atendidos\n");

        // Escenario adicional: llegada intercalada
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ESCENARIO 2: Llegadas y atenciones intercaladas");
        System.out.println("=".repeat(60) + "\n");

        ColaPrioridadPacientes cola2 = new ColaPrioridadPacientes(20);

        System.out.println("1. Llegan 2 pacientes:");
        cola2.ingresar(new Paciente("Sofía Torres", 2, 0));
        cola2.ingresar(new Paciente("Diego Ruiz", 3, 0));
        System.out.println();

        System.out.println("2. Se atiende un paciente:");
        cola2.atender();
        System.out.println();

        System.out.println("3. Llega paciente de urgencia:");
        cola2.ingresar(new Paciente("Emergencia Crítica", 1, 0));
        System.out.println();

        System.out.println("4. Se atiende (debe ser la emergencia):");
        cola2.atender();
        System.out.println();

        System.out.println("5. Se atiende el último:");
        cola2.atender();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("\nCONCLUSIÓN:");
        System.out.println(" Los pacientes se atienden según PRIORIDAD (1 > 2 > 3)");
        System.out.println(" Con misma prioridad, se atienden por orden de llegada (FIFO)");
        System.out.println(" Complejidad: O(log n) para ingresar y atender");
    }
}

//cd "/home/fabricio-posada/InfoIII/Practico Integrador" && javac *.java && java Main

import java.util.Scanner;

public class Main {
    private static SistemaTurnos sistema;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        sistema = new SistemaTurnos();

        // Cargar datos iniciales (Ejercicio 1)
        sistema.cargarDatosIniciales();

        // Menú principal
        mostrarMenuPrincipal();

        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("-------------------------------------------");
            System.out.println("MENÚ PRINCIPAL");
            System.out.println("-------------------------------------------");
            System.out.println("1) Ver agenda de un médico");
            System.out.println("2) Buscar próximo turno disponible");
            System.out.println("3) Simular sala de espera");
            System.out.println("4) Programar recordatorios");
            System.out.println("5) Consultar índice de pacientes (Hash)");
            System.out.println("6) Consolidador de agendas");
            System.out.println("7) Reportes de ordenamiento");
            System.out.println("8) Auditoría Undo/Redo");
            System.out.println("9) Planificador de quirófano");
            System.out.println("10) Estadísticas del sistema");
            System.out.println("0) Salir");
            System.out.println("-------------------------------------------");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            System.out.println();

            switch (opcion) {
                case 1 -> verAgendaMedico();
                case 2 -> buscarProximoTurno();
                case 3 -> simularSalaEspera();
                case 4 -> programarRecordatorios();
                case 5 -> consultarIndicePacientes();
                case 6 -> consolidarAgendas();
                case 7 -> reportesOrdenamiento();
                case 8 -> auditoriaUndoRedo();
                case 9 -> planificadorQuirofano();
                case 10 -> sistema.mostrarEstadisticas();
                case 0 -> {
                    System.out.println("-------------------------------------------");
                    System.out.println("Fin de ejecución.");
                    System.out.println("-------------------------------------------");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }

            if (continuar) {
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }

    // ==================== EJERCICIO 1: Implementado en SistemaTurnos
    // ====================

    // ==================== EJERCICIO 2: Ver agenda de un médico
    // ====================
    private static void verAgendaMedico() {
        System.out.println("-------------------------------------------");
        System.out.println("[VER AGENDA DE UN MÉDICO]");
        System.out.println("-------------------------------------------");

        sistema.listarMedicos();

        System.out.print("\nIngrese la matrícula del médico: ");
        String matricula = scanner.nextLine().toUpperCase();

        sistema.verAgendaMedico(matricula);
    }

    // ==================== EJERCICIO 3: Buscar próximo turno ====================
    private static void buscarProximoTurno() {
        System.out.println("-------------------------------------------");
        System.out.println("[BUSCAR PRÓXIMO TURNO DISPONIBLE]");
        System.out.println("-------------------------------------------");

        sistema.listarMedicos();

        System.out.print("\nIngrese la matrícula del médico: ");
        String matricula = scanner.nextLine().toUpperCase();

        System.out.print("Duración del turno (minutos): ");
        int duracion = scanner.nextInt();
        scanner.nextLine();

        sistema.buscarProximoTurno(matricula, duracion);
    }

    // ==================== EJERCICIO 4: Sala de espera ====================
    private static void simularSalaEspera() {
        System.out.println("-------------------------------------------");
        System.out.println("[SIMULAR SALA DE ESPERA - COLA CIRCULAR]");
        System.out.println("-------------------------------------------");
        sistema.simularSalaEspera();
    }

    // ==================== EJERCICIO 5: Recordatorios ====================
    private static void programarRecordatorios() {
        System.out.println("-------------------------------------------");
        System.out.println("[PROGRAMAR RECORDATORIOS - HEAP]");
        System.out.println("-------------------------------------------");
        sistema.programarRecordatorios();
    }

    // ==================== EJERCICIO 6: Índice pacientes ====================
    private static void consultarIndicePacientes() {
        System.out.println("-------------------------------------------");
        System.out.println("[ÍNDICE DE PACIENTES - HASH TABLE]");
        System.out.println("-------------------------------------------");
        sistema.consultarIndicePacientes();
    }

    // ==================== EJERCICIO 7: Consolidar agendas ====================
    private static void consolidarAgendas() {
        System.out.println("-------------------------------------------");
        System.out.println("[CONSOLIDADOR DE AGENDAS - MERGE]");
        System.out.println("-------------------------------------------");
        sistema.consolidarAgendas();
    }

    // ==================== EJERCICIO 8: Reportes ====================
    private static void reportesOrdenamiento() {
        System.out.println("-------------------------------------------");
        System.out.println("[REPORTES CON ORDENAMIENTOS]");
        System.out.println("-------------------------------------------");
        sistema.reportesOrdenamiento();
    }

    // ==================== EJERCICIO 9: Undo/Redo ====================
    private static void auditoriaUndoRedo() {
        System.out.println("-------------------------------------------");
        System.out.println("[AUDITORÍA UNDO/REDO - PILAS]");
        System.out.println("-------------------------------------------");
        sistema.auditoriaUndoRedo();
    }

    // ==================== EJERCICIO 10: Quirófano ====================
    private static void planificadorQuirofano() {
        System.out.println("-------------------------------------------");
        System.out.println("[PLANIFICADOR DE QUIRÓFANO - HEAPS]");
        System.out.println("-------------------------------------------");
        sistema.planificadorQuirofano();
    }
}
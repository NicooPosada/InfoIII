import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SistemaTurnos {
    // Estructuras de datos principales
    private Map<String, Paciente> pacientes; // DNI → Paciente
    private Map<String, Medico> medicos; // Matrícula → Médico
    private List<Turno> turnos; // Lista de turnos
    private Map<String, Turno> turnosPorId; // ID → Turno (para búsqueda rápida)
    private Map<String, List<Turno>> turnosPorMedico; // Matrícula → Lista de turnos

    // Contadores para validación
    private int turnosRechazados;
    private int turnosDuplicados;

    public SistemaTurnos() {
        pacientes = new HashMap<>();
        medicos = new HashMap<>();
        turnos = new ArrayList<>();
        turnosPorId = new HashMap<>();
        turnosPorMedico = new HashMap<>();
        turnosRechazados = 0;
        turnosDuplicados = 0;
    }

    // ==================== EJERCICIO 1: CARGA INICIAL ====================

    public void cargarDatosIniciales() {
        System.out.println("===========================================");
        System.out.println("SISTEMA DE GESTIÓN DE TURNOS MÉDICOS");
        System.out.println("===========================================");
        System.out.println("Cargando datos iniciales...\n");

        // Cargar pacientes
        int pacientesCargados = cargarPacientes("pacientes.csv");
        System.out.printf("> Leyendo pacientes.csv ... [OK] (%d registros)%n", pacientesCargados);

        // Cargar médicos
        int medicosCargados = cargarMedicos("medicos.csv");
        System.out.printf("> Leyendo medicos.csv ...... [OK] (%d registros)%n", medicosCargados);

        // Cargar turnos con validación
        int turnosCargados = cargarTurnos("turnos.csv");
        System.out.printf("> Leyendo turnos.csv ....... [OK] (%d registros)%n", turnosCargados);

        // Mostrar validaciones
        System.out.println("> Validando datos ...");
        if (turnosRechazados > 0) {
            System.out.printf("  - %d turnos rechazados (fecha pasada o duración inválida)%n", turnosRechazados);
        }
        if (turnosDuplicados > 0) {
            System.out.printf("  - %d turnos duplicados por ID%n", turnosDuplicados);
        }

        System.out.println("> Estructuras internas inicializadas correctamente.\n");
    }

    private int cargarPacientes(String archivo) {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine(); // Saltar encabezado

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 2) {
                    String dni = datos[0].trim();
                    String nombre = datos[1].trim();
                    pacientes.put(dni, new Paciente(dni, nombre));
                    contador++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer " + archivo + ": " + e.getMessage());
        }
        return contador;
    }

    private int cargarMedicos(String archivo) {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine(); // Saltar encabezado

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3) {
                    String matricula = datos[0].trim();
                    String nombre = datos[1].trim();
                    String especialidad = datos[2].trim();
                    Medico medico = new Medico(matricula, nombre, especialidad);
                    medicos.put(matricula, medico);
                    turnosPorMedico.put(matricula, new ArrayList<>());
                    contador++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer " + archivo + ": " + e.getMessage());
        }
        return contador;
    }

    private int cargarTurnos(String archivo) {
        int contador = 0;
        Set<String> idsVistos = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine(); // Saltar encabezado

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 6) {
                    String id = datos[0].trim();
                    String dniPaciente = datos[1].trim();
                    String matriculaMedico = datos[2].trim();
                    String fechaHoraStr = datos[3].trim();
                    int duracionMin = Integer.parseInt(datos[4].trim());
                    String motivo = datos[5].trim();

                    // Validación 1: Turno duplicado por ID
                    if (idsVistos.contains(id)) {
                        turnosDuplicados++;
                        continue;
                    }

                    // Validación 2: Paciente existe
                    if (!pacientes.containsKey(dniPaciente)) {
                        turnosRechazados++;
                        continue;
                    }

                    // Validación 3: Médico existe
                    if (!medicos.containsKey(matriculaMedico)) {
                        turnosRechazados++;
                        continue;
                    }

                    // Validación 4: Fecha futura o presente
                    LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, formatter);
                    if (fechaHora.isBefore(LocalDateTime.now())) {
                        turnosRechazados++;
                        continue;
                    }

                    // Validación 5: Duración > 0
                    if (duracionMin <= 0) {
                        turnosRechazados++;
                        continue;
                    }

                    // Turno válido
                    Turno turno = new Turno(id, dniPaciente, matriculaMedico,
                            fechaHora, duracionMin, motivo);
                    turnos.add(turno);
                    turnosPorId.put(id, turno);
                    turnosPorMedico.get(matriculaMedico).add(turno);
                    idsVistos.add(id);
                    contador++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer " + archivo + ": " + e.getMessage());
        }
        return contador;
    }

    // ==================== GETTERS ====================

    public Map<String, Paciente> getPacientes() {
        return pacientes;
    }

    public Map<String, Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public Map<String, List<Turno>> getTurnosPorMedico() {
        return turnosPorMedico;
    }

    public Turno getTurnoPorId(String id) {
        return turnosPorId.get(id);
    }

    // ==================== MÉTODOS AUXILIARES ====================

    public void mostrarEstadisticas() {
        System.out.println("\n=== ESTADÍSTICAS DEL SISTEMA ===");
        System.out.println("Pacientes registrados: " + pacientes.size());
        System.out.println("Médicos disponibles: " + medicos.size());
        System.out.println("Turnos activos: " + turnos.size());
        System.out.println("Turnos rechazados en carga: " + turnosRechazados);
        System.out.println("Turnos duplicados detectados: " + turnosDuplicados);
    }

    public void listarMedicos() {
        System.out.println("\n=== MÉDICOS DISPONIBLES ===");
        for (Medico medico : medicos.values()) {
            int cantTurnos = turnosPorMedico.get(medico.getMatricula()).size();
            System.out.printf("[%s] %s (%d turnos)%n",
                    medico.getMatricula(), medico, cantTurnos);
        }
    }

    // ==================== EJERCICIO 2: BÚSQUEDA POR DNI ====================

    public void buscarTurnosPorDNI(String dni) {
        System.out.println("\n=== BÚSQUEDA DE TURNOS POR DNI ===");
        System.out.println("DNI: " + dni);

        Paciente paciente = pacientes.get(dni);
        if (paciente == null) {
            System.out.println(" Paciente no encontrado");
            return;
        }

        System.out.println("Paciente: " + paciente.getNombre());

        List<Turno> turnosPaciente = new ArrayList<>();
        for (Turno turno : turnos) {
            if (turno.getDniPaciente().equals(dni)) {
                turnosPaciente.add(turno);
            }
        }

        if (turnosPaciente.isEmpty()) {
            System.out.println("No tiene turnos asignados.");
        } else {
            System.out.println("\nTurnos encontrados (" + turnosPaciente.size() + "):");
            for (Turno turno : turnosPaciente) {
                Medico medico = medicos.get(turno.getMatriculaMedico());
                System.out.printf("  [%s] %s - Dr. %s (%s) - %d min - %s%n",
                        turno.getId(),
                        turno.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        medico.getNombre(),
                        medico.getEspecialidad(),
                        turno.getDuracionMin(),
                        turno.getMotivo());
            }
        }
    }

    // ==================== EJERCICIO 3: TURNOS POR ESPECIALIDAD
    // ====================

    public void listarTurnosPorEspecialidad(String especialidad) {
        System.out.println("\n=== TURNOS POR ESPECIALIDAD ===");
        System.out.println("Especialidad: " + especialidad);

        List<Turno> turnosEspecialidad = new ArrayList<>();

        for (Turno turno : turnos) {
            Medico medico = medicos.get(turno.getMatriculaMedico());
            if (medico.getEspecialidad().equalsIgnoreCase(especialidad)) {
                turnosEspecialidad.add(turno);
            }
        }

        if (turnosEspecialidad.isEmpty()) {
            System.out.println("No hay turnos para esta especialidad.");
        } else {
            System.out.println("\nTurnos encontrados (" + turnosEspecialidad.size() + "):");
            for (Turno turno : turnosEspecialidad) {
                Medico medico = medicos.get(turno.getMatriculaMedico());
                Paciente paciente = pacientes.get(turno.getDniPaciente());
                System.out.printf("  [%s] %s - Dr. %s - Paciente: %s - %s%n",
                        turno.getId(),
                        turno.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        medico.getNombre(),
                        paciente.getNombre(),
                        turno.getMotivo());
            }
        }
    }

    // ==================== EJERCICIO 4: ORDENAR TURNOS POR FECHA
    // ====================

    public void listarTurnosOrdenados() {
        System.out.println("\n=== TURNOS ORDENADOS POR FECHA ===");

        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
            return;
        }

        // Crear copia y ordenar
        List<Turno> turnosOrdenados = new ArrayList<>(turnos);
        turnosOrdenados.sort((t1, t2) -> t1.getFechaHora().compareTo(t2.getFechaHora()));

        System.out.println("\nTotal de turnos: " + turnosOrdenados.size());
        for (Turno turno : turnosOrdenados) {
            Medico medico = medicos.get(turno.getMatriculaMedico());
            Paciente paciente = pacientes.get(turno.getDniPaciente());
            System.out.printf("[%s] %s - Dr. %s (%s) - Pac: %s - %d min%n",
                    turno.getId(),
                    turno.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    medico.getNombre(),
                    medico.getEspecialidad(),
                    paciente.getNombre(),
                    turno.getDuracionMin());
        }
    }

    // ==================== EJERCICIO 5: MÉDICO CON MÁS TURNOS ====================

    public void medicoConMasTurnos() {
        System.out.println("\n=== MÉDICO CON MÁS TURNOS ===");

        if (medicos.isEmpty()) {
            System.out.println("No hay médicos registrados.");
            return;
        }

        String matriculaMasTurnos = null;
        int maxTurnos = -1;

        for (Map.Entry<String, List<Turno>> entry : turnosPorMedico.entrySet()) {
            int cantTurnos = entry.getValue().size();
            if (cantTurnos > maxTurnos) {
                maxTurnos = cantTurnos;
                matriculaMasTurnos = entry.getKey();
            }
        }

        if (matriculaMasTurnos == null || maxTurnos == 0) {
            System.out.println("No hay turnos asignados.");
        } else {
            Medico medico = medicos.get(matriculaMasTurnos);
            System.out.printf("Dr. %s (%s)%n", medico.getNombre(), medico.getEspecialidad());
            System.out.printf("Matrícula: %s%n", medico.getMatricula());
            System.out.printf("Total de turnos: %d%n", maxTurnos);

            System.out.println("\nDetalle de turnos:");
            for (Turno turno : turnosPorMedico.get(matriculaMasTurnos)) {
                Paciente paciente = pacientes.get(turno.getDniPaciente());
                System.out.printf("  - %s: %s (%d min)%n",
                        turno.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                        paciente.getNombre(),
                        turno.getDuracionMin());
            }
        }
    }

    // ==================== EJERCICIO 6: CANCELAR TURNO ====================

    public boolean cancelarTurno(String idTurno) {
        System.out.println("\n=== CANCELAR TURNO ===");
        System.out.println("ID del turno: " + idTurno);

        Turno turno = turnosPorId.get(idTurno);

        if (turno == null) {
            System.out.println(" Turno no encontrado.");
            return false;
        }

        // Mostrar información del turno
        Medico medico = medicos.get(turno.getMatriculaMedico());
        Paciente paciente = pacientes.get(turno.getDniPaciente());

        System.out.println("\nTurno encontrado:");
        System.out.printf("  Paciente: %s (DNI: %s)%n", paciente.getNombre(), paciente.getDni());
        System.out.printf("  Médico: Dr. %s (%s)%n", medico.getNombre(), medico.getEspecialidad());
        System.out.printf("  Fecha: %s%n",
                turno.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        System.out.printf("  Duración: %d minutos%n", turno.getDuracionMin());

        // Eliminar de todas las estructuras
        turnos.remove(turno);
        turnosPorId.remove(idTurno);
        turnosPorMedico.get(turno.getMatriculaMedico()).remove(turno);

        System.out.println("\n Turno cancelado exitosamente.");
        return true;
    }

    // ==================== EJERCICIO 7: TIEMPO TOTAL DE ATENCIÓN POR MÉDICO
    // ====================

    public void tiempoTotalPorMedico(String matricula) {
        System.out.println("\n=== TIEMPO TOTAL DE ATENCIÓN ===");

        Medico medico = medicos.get(matricula);
        if (medico == null) {
            System.out.println(" Médico no encontrado.");
            return;
        }

        System.out.printf("Dr. %s (%s)%n", medico.getNombre(), medico.getEspecialidad());
        System.out.println("Matrícula: " + matricula);

        List<Turno> turnosMedico = turnosPorMedico.get(matricula);

        if (turnosMedico.isEmpty()) {
            System.out.println("\nNo tiene turnos asignados.");
            return;
        }

        int tiempoTotal = 0;
        for (Turno turno : turnosMedico) {
            tiempoTotal += turno.getDuracionMin();
        }

        int horas = tiempoTotal / 60;
        int minutos = tiempoTotal % 60;

        System.out.println("\nEstadísticas:");
        System.out.printf("  Total de turnos: %d%n", turnosMedico.size());
        System.out.printf("  Tiempo total: %d minutos (%d hrs %d min)%n", tiempoTotal, horas, minutos);
        System.out.printf("  Promedio por turno: %.1f minutos%n", (double) tiempoTotal / turnosMedico.size());
    }

    // ==================== EJERCICIO 8: PACIENTES SIN TURNOS ====================

    public void listarPacientesSinTurnos() {
        System.out.println("\n=== PACIENTES SIN TURNOS ASIGNADOS ===");

        Set<String> pacientesConTurnos = new HashSet<>();
        for (Turno turno : turnos) {
            pacientesConTurnos.add(turno.getDniPaciente());
        }

        List<Paciente> pacientesSinTurnos = new ArrayList<>();
        for (Paciente paciente : pacientes.values()) {
            if (!pacientesConTurnos.contains(paciente.getDni())) {
                pacientesSinTurnos.add(paciente);
            }
        }

        if (pacientesSinTurnos.isEmpty()) {
            System.out.println("Todos los pacientes tienen al menos un turno asignado.");
        } else {
            System.out.println("\nPacientes sin turnos (" + pacientesSinTurnos.size() + "):");
            for (Paciente paciente : pacientesSinTurnos) {
                System.out.printf("  - %s (DNI: %s)%n", paciente.getNombre(), paciente.getDni());
            }
        }
    }

    // ==================== EJERCICIO 9: TURNOS EN RANGO DE FECHAS
    // ====================

    public void turnosEnRango(LocalDateTime inicio, LocalDateTime fin) {
        System.out.println("\n=== TURNOS EN RANGO DE FECHAS ===");
        System.out.printf("Desde: %s%n", inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        System.out.printf("Hasta: %s%n", fin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        List<Turno> turnosEnRango = new ArrayList<>();

        for (Turno turno : turnos) {
            LocalDateTime fecha = turno.getFechaHora();
            if (!fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
                turnosEnRango.add(turno);
            }
        }

        if (turnosEnRango.isEmpty()) {
            System.out.println("\nNo hay turnos en este rango de fechas.");
        } else {
            // Ordenar por fecha
            turnosEnRango.sort((t1, t2) -> t1.getFechaHora().compareTo(t2.getFechaHora()));

            System.out.println("\nTurnos encontrados (" + turnosEnRango.size() + "):");
            for (Turno turno : turnosEnRango) {
                Medico medico = medicos.get(turno.getMatriculaMedico());
                Paciente paciente = pacientes.get(turno.getDniPaciente());
                System.out.printf("  [%s] %s - Dr. %s - Pac: %s - %d min%n",
                        turno.getId(),
                        turno.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        medico.getNombre(),
                        paciente.getNombre(),
                        turno.getDuracionMin());
            }
        }
    }

    // ==================== EJERCICIO 10: RESUMEN POR ESPECIALIDAD
    // ====================

    public void resumenPorEspecialidad() {
        System.out.println("\n=== RESUMEN DE TURNOS POR ESPECIALIDAD ===");

        Map<String, Integer> turnosPorEspecialidad = new HashMap<>();
        Map<String, Integer> tiempoPorEspecialidad = new HashMap<>();

        // Contar turnos y tiempo por especialidad
        for (Turno turno : turnos) {
            Medico medico = medicos.get(turno.getMatriculaMedico());
            String especialidad = medico.getEspecialidad();

            turnosPorEspecialidad.put(especialidad,
                    turnosPorEspecialidad.getOrDefault(especialidad, 0) + 1);
            tiempoPorEspecialidad.put(especialidad,
                    tiempoPorEspecialidad.getOrDefault(especialidad, 0) + turno.getDuracionMin());
        }

        if (turnosPorEspecialidad.isEmpty()) {
            System.out.println("No hay turnos registrados.");
            return;
        }

        System.out.println("\nEspecialidad              | Turnos | Tiempo Total | Promedio");
        System.out.println("─".repeat(70));

        // Ordenar por cantidad de turnos (descendente)
        turnosPorEspecialidad.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry -> {
                    String esp = entry.getKey();
                    int cant = entry.getValue();
                    int tiempo = tiempoPorEspecialidad.get(esp);
                    double promedio = (double) tiempo / cant;

                    System.out.printf("%-25s | %6d | %7d min  | %6.1f min%n",
                            esp, cant, tiempo, promedio);
                });

        // Totales
        int totalTurnos = turnosPorEspecialidad.values().stream().mapToInt(Integer::intValue).sum();
        int totalTiempo = tiempoPorEspecialidad.values().stream().mapToInt(Integer::intValue).sum();

        System.out.println("─".repeat(70));
        System.out.printf("TOTAL                     | %6d | %7d min  | %6.1f min%n",
                totalTurnos, totalTiempo, (double) totalTiempo / totalTurnos);
    }

    // ==================== EJERCICIO 2: VER AGENDA DE UN MÉDICO
    // ====================

    public void verAgendaMedico(String matricula) {
        if (!medicos.containsKey(matricula)) {
            System.out.println(" Médico no encontrado");
            return;
        }

        Medico medico = medicos.get(matricula);
        System.out.println("\n[AGENDA DEL DR. " + medico.getNombre().toUpperCase() + " - " +
                medico.getEspecialidad().toUpperCase() + "]");
        System.out.println("-------------------------------------------");

        List<Turno> turnosMedico = turnosPorMedico.get(matricula);

        if (turnosMedico.isEmpty()) {
            System.out.println("No tiene turnos asignados.");
            return;
        }

        // Ordenar por fecha
        turnosMedico.sort((t1, t2) -> t1.getFechaHora().compareTo(t2.getFechaHora()));

        System.out.println("Turnos ordenados por fecha:");
        System.out.println("ID       PACIENTE         FECHA Y HORA      MOTIVO");
        System.out.println("---------------------------------------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        for (Turno turno : turnosMedico) {
            Paciente paciente = pacientes.get(turno.getDniPaciente());
            System.out.printf("%-8s %-16s %-17s %s%n",
                    turno.getId(),
                    paciente.getNombre(),
                    turno.getFechaHora().format(formatter) + " hs",
                    turno.getMotivo());
        }

        System.out.println("---------------------------------------------------------");
        System.out.println("Total de turnos: " + turnosMedico.size());

        // Buscar siguiente disponible
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime siguiente = null;

        for (Turno t : turnosMedico) {
            if (t.getFechaHora().isAfter(ahora)) {
                siguiente = t.getFechaHora();
                break;
            }
        }

        if (siguiente != null) {
            System.out.printf("Siguiente disponible ≥ ahora → %s hs%n",
                    siguiente.format(formatter));
        }

        System.out.println("[Operación O(n log n) - Ordenamiento + búsqueda lineal]");
    }

    // ==================== EJERCICIO 3: BUSCAR PRÓXIMO TURNO DISPONIBLE
    // ====================

    public void buscarProximoTurno(String matricula, int duracionMin) {
        if (!medicos.containsKey(matricula)) {
            System.out.println(" Médico no encontrado");
            return;
        }

        Medico medico = medicos.get(matricula);
        List<Turno> turnosMedico = turnosPorMedico.get(matricula);

        System.out.println("Buscando hueco de " + duracionMin + " minutos para Dr. " + medico.getNombre());

        if (turnosMedico.isEmpty()) {
            LocalDateTime ahora = LocalDateTime.now();
            System.out.println("✓ Primer turno disponible: " +
                    ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            return;
        }

        // Ordenar turnos
        turnosMedico.sort((t1, t2) -> t1.getFechaHora().compareTo(t2.getFechaHora()));

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicioBusqueda = ahora.plusMinutes(5); // Buffer de 5 min

        // Buscar hueco entre turnos
        for (int i = 0; i < turnosMedico.size() - 1; i++) {
            Turno actual = turnosMedico.get(i);
            Turno siguiente = turnosMedico.get(i + 1);

            LocalDateTime finActual = actual.getFechaFin();
            LocalDateTime inicioSiguiente = siguiente.getFechaHora();

            if (finActual.isBefore(inicioBusqueda)) {
                finActual = inicioBusqueda;
            }

            long minLibres = java.time.Duration.between(finActual, inicioSiguiente).toMinutes();

            if (minLibres >= duracionMin && finActual.isAfter(ahora)) {
                System.out.printf("✓ Hueco encontrado: %s (%d minutos disponibles)%n",
                        finActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        minLibres);
                return;
            }
        }

        // Si no hay hueco, ofrecer después del último turno
        Turno ultimo = turnosMedico.get(turnosMedico.size() - 1);
        LocalDateTime despuesUltimo = ultimo.getFechaFin();
        System.out.printf("✓ Disponible después del último turno: %s%n",
                despuesUltimo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    // ==================== EJERCICIO 4: SALA DE ESPERA (COLA CIRCULAR)
    // ====================

    static class SalaEspera {
        private String[] cola;
        private int front;
        private int rear;
        private int size;
        private int capacity;

        public SalaEspera(int capacity) {
            this.capacity = capacity;
            this.cola = new String[capacity];
            this.front = 0;
            this.rear = -1;
            this.size = 0;
        }

        public void llega(String dni) {
            if (size == capacity) {
                // Overflow: eliminar el más antiguo
                System.out.println(" Cola llena - Se elimina el más antiguo: " + cola[front]);
                front = (front + 1) % capacity;
                size--;
            }

            rear = (rear + 1) % capacity;
            cola[rear] = dni;
            size++;
            System.out.println("✓ Llega paciente: " + dni);
        }

        public String atiende() {
            if (size == 0) {
                System.out.println(" Sala vacía");
                return null;
            }

            String dni = cola[front];
            front = (front + 1) % capacity;
            size--;
            System.out.println("✓ Atiende paciente: " + dni);
            return dni;
        }

        public String peek() {
            if (size == 0)
                return null;
            return cola[front];
        }

        public int size() {
            return size;
        }

        public void mostrarEstado() {
            if (size == 0) {
                System.out.println("Estado: [vacía]");
                return;
            }

            System.out.print("Estado: FRONT → [");
            int idx = front;
            for (int i = 0; i < size; i++) {
                System.out.print(cola[idx]);
                if (i < size - 1)
                    System.out.print(", ");
                idx = (idx + 1) % capacity;
            }
            System.out.println("] ← REAR");
            System.out.println("Tamaño actual: " + size + "/" + capacity);
            System.out.println("[Operaciones O(1)]");
        }
    }

    public void simularSalaEspera() {
        System.out.println("Simulación de Sala de Espera (Cola Circular)");
        System.out.println("Capacidad máxima: 5");

        SalaEspera sala = new SalaEspera(5);

        // Simulación
        String[] pacientesLlegando = { "32045982", "32458910", "31890432", "31247856", "32500890", "31111222" };

        System.out.println("\n--- Llegadas ---");
        for (String dni : pacientesLlegando) {
            sala.llega(dni);
        }

        System.out.println("\n--- Estado actual ---");
        sala.mostrarEstado();

        System.out.println("\n--- Atenciones ---");
        sala.atiende();
        sala.atiende();

        System.out.println("\n--- Estado final ---");
        sala.mostrarEstado();
    }

    // ==================== EJERCICIO 5: RECORDATORIOS (MIN HEAP)
    // ====================

    static class Recordatorio {
        String id;
        LocalDateTime fecha;
        String dniPaciente;
        String mensaje;

        public Recordatorio(String id, LocalDateTime fecha, String dniPaciente, String mensaje) {
            this.id = id;
            this.fecha = fecha;
            this.dniPaciente = dniPaciente;
            this.mensaje = mensaje;
        }

        @Override
        public String toString() {
            return String.format("%s | Paciente DNI: %s | %s | %s",
                    id,
                    dniPaciente,
                    mensaje,
                    fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
    }

    static class PlannerRecordatorios {
        private List<Recordatorio> heap;
        private Map<String, Integer> posiciones; // id → índice en heap

        public PlannerRecordatorios() {
            heap = new ArrayList<>();
            posiciones = new HashMap<>();
        }

        private void swap(int i, int j) {
            Recordatorio temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);

            // Actualizar posiciones
            posiciones.put(heap.get(i).id, i);
            posiciones.put(heap.get(j).id, j);
        }

        private void percolateUp(int i) {
            while (i > 0) {
                int parent = (i - 1) / 2;
                if (heap.get(i).fecha.compareTo(heap.get(parent).fecha) >= 0)
                    break;
                swap(i, parent);
                i = parent;
            }
        }

        private void percolateDown(int i) {
            while (true) {
                int smallest = i;
                int left = 2 * i + 1;
                int right = 2 * i + 2;

                if (left < heap.size() && heap.get(left).fecha.compareTo(heap.get(smallest).fecha) < 0) {
                    smallest = left;
                }
                if (right < heap.size() && heap.get(right).fecha.compareTo(heap.get(smallest).fecha) < 0) {
                    smallest = right;
                }

                if (i == smallest)
                    break;
                swap(i, smallest);
                i = smallest;
            }
        }

        public void programar(Recordatorio r) {
            heap.add(r);
            int index = heap.size() - 1;
            posiciones.put(r.id, index);
            percolateUp(index);
            System.out.println("✓ Recordatorio programado: " + r.id + " para " + r.fecha);
        }

        public Recordatorio proximo() {
            if (heap.isEmpty())
                return null;

            Recordatorio r = heap.get(0);
            posiciones.remove(r.id);

            if (heap.size() == 1) {
                heap.remove(0);
            } else {
                heap.set(0, heap.remove(heap.size() - 1));
                posiciones.put(heap.get(0).id, 0);
                percolateDown(0);
            }

            System.out.println("✓ Próximo recordatorio: " + r.id + " - " + r.mensaje);
            return r;
        }

        public void reprogramar(String id, LocalDateTime nuevaFecha) {
            if (!posiciones.containsKey(id)) {
                System.out.println(" Recordatorio no encontrado");
                return;
            }

            int index = posiciones.get(id);
            LocalDateTime viejaFecha = heap.get(index).fecha;
            heap.get(index).fecha = nuevaFecha;

            if (nuevaFecha.isBefore(viejaFecha)) {
                percolateUp(index);
            } else {
                percolateDown(index);
            }

            System.out.println("✓ Recordatorio " + id + " reprogramado a " + nuevaFecha);
        }

        public int size() {
            return heap.size();
        }
    }

    public void programarRecordatorios() {
        System.out.println("Planificador de Recordatorios (MinHeap)");

        PlannerRecordatorios planner = new PlannerRecordatorios();

        // Programar recordatorios
        planner.programar(
                new Recordatorio("R1", LocalDateTime.now().plusDays(1), "32045982", "Control próxima semana"));
        planner.programar(new Recordatorio("R2", LocalDateTime.now().plusHours(2), "31890432", "Recordar estudios"));
        planner.programar(new Recordatorio("R3", LocalDateTime.now().plusDays(3), "32458910", "Retirar resultados"));

        System.out.println("\nRecordatorios en heap: " + planner.size());

        System.out.println("\n--- Procesando recordatorios ---");
        planner.proximo();

        System.out.println("\n--- Reprogramando ---");
        planner.reprogramar("R3", LocalDateTime.now().plusHours(1));

        System.out.println("\nRecordatorios restantes: " + planner.size());
        System.out.println("[Operaciones O(log n)]");
    }

    // ==================== EJERCICIO 6: ÍNDICE HASH DE PACIENTES
    // ====================

    public void consultarIndicePacientes() {
        System.out.println("Índice rápido de Pacientes (HashMap interno de Java)");
        System.out.println("Tamaño actual: " + pacientes.size());

        System.out.println("\nPrimeros 10 pacientes:");
        int count = 0;
        for (Map.Entry<String, Paciente> entry : pacientes.entrySet()) {
            if (count++ >= 10)
                break;
            System.out.println("  DNI: " + entry.getKey() + " → " + entry.getValue().getNombre());
        }

        System.out.println("\n[Operaciones O(1) promedio con HashMap]");
        System.out.println("[En producción: implementar Hash con chaining y rehash]");
    }

    // ==================== EJERCICIO 7: CONSOLIDAR AGENDAS ====================

    public void consolidarAgendas() {
        System.out.println("Consolidador de Agendas (Merge de listas ordenadas)");

        if (medicos.size() < 2) {
            System.out.println("Se necesitan al menos 2 médicos para consolidar");
            return;
        }

        // Tomar dos médicos
        List<String> matriculas = new ArrayList<>(medicos.keySet());
        String mat1 = matriculas.get(0);
        String mat2 = matriculas.get(1);

        List<Turno> agenda1 = new ArrayList<>(turnosPorMedico.get(mat1));
        List<Turno> agenda2 = new ArrayList<>(turnosPorMedico.get(mat2));

        // Ordenar ambas
        agenda1.sort((t1, t2) -> t1.getFechaHora().compareTo(t2.getFechaHora()));
        agenda2.sort((t1, t2) -> t1.getFechaHora().compareTo(t2.getFechaHora()));

        System.out.println("Consolidando agendas:");
        System.out.println("  Médico 1: " + medicos.get(mat1).getNombre() + " (" + agenda1.size() + " turnos)");
        System.out.println("  Médico 2: " + medicos.get(mat2).getNombre() + " (" + agenda2.size() + " turnos)");

        // Merge
        List<Turno> consolidada = new ArrayList<>();
        int i = 0, j = 0;

        while (i < agenda1.size() && j < agenda2.size()) {
            if (agenda1.get(i).getFechaHora().isBefore(agenda2.get(j).getFechaHora())) {
                consolidada.add(agenda1.get(i++));
            } else {
                consolidada.add(agenda2.get(j++));
            }
        }

        while (i < agenda1.size())
            consolidada.add(agenda1.get(i++));
        while (j < agenda2.size())
            consolidada.add(agenda2.get(j++));

        System.out.println("\n✓ Agenda consolidada: " + consolidada.size() + " turnos");
        System.out.println("[Merge realizado en O(n+m)]");
    }

    // ==================== EJERCICIO 8: REPORTES CON ORDENAMIENTO
    // ====================

    // Ordenamiento por Inserción (estable) - por hora
    private void ordenamientoInsercion(List<Turno> lista) {
        for (int i = 1; i < lista.size(); i++) {
            Turno key = lista.get(i);
            int j = i - 1;

            while (j >= 0 && lista.get(j).getFechaHora().isAfter(key.getFechaHora())) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, key);
        }
    }

    // Shellsort - por duración
    private void shellsort(List<Turno> lista) {
        int n = lista.size();

        // Secuencia de gaps (Knuth)
        int gap = 1;
        while (gap < n / 3) {
            gap = 3 * gap + 1;
        }

        while (gap >= 1) {
            for (int i = gap; i < n; i++) {
                Turno temp = lista.get(i);
                int j = i;

                while (j >= gap && lista.get(j - gap).getDuracionMin() > temp.getDuracionMin()) {
                    lista.set(j, lista.get(j - gap));
                    j -= gap;
                }
                lista.set(j, temp);
            }
            gap /= 3;
        }
    }

    // Quicksort (Lomuto) - por apellido de paciente
    private void quicksort(List<Turno> lista, int low, int high) {
        if (low < high) {
            int pi = partition(lista, low, high);
            quicksort(lista, low, pi - 1);
            quicksort(lista, pi + 1, high);
        }
    }

    private int partition(List<Turno> lista, int low, int high) {
        Turno pivot = lista.get(high);
        String pivotNombre = pacientes.get(pivot.getDniPaciente()).getNombre();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            String nombreJ = pacientes.get(lista.get(j).getDniPaciente()).getNombre();
            if (nombreJ.compareTo(pivotNombre) <= 0) {
                i++;
                Turno temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }

        Turno temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(high));
        lista.set(high, temp);

        return i + 1;
    }

    public void reportesOrdenamiento() {
        System.out.println("=== REPORTES CON MÚLTIPLES ORDENAMIENTOS ===");
        System.out.println("Total de turnos: " + turnos.size());

        // 1. Ordenamiento por hora (Inserción - estable)
        System.out.println("\n─── 1. ORDENAMIENTO POR HORA (Inserción - Estable) ───");
        List<Turno> porHora = new ArrayList<>(turnos);

        long inicio = System.nanoTime();
        ordenamientoInsercion(porHora);
        long fin = System.nanoTime();

        System.out.println("Tiempo: " + (fin - inicio) / 1_000_000.0 + " ms");
        System.out.println("\nPrimeros 5 turnos:");
        for (int i = 0; i < Math.min(5, porHora.size()); i++) {
            Turno t = porHora.get(i);
            Paciente p = pacientes.get(t.getDniPaciente());
            System.out.printf("  %s - %s - %s%n",
                    t.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                    p.getNombre(),
                    t.getMotivo());
        }

        // 2. Ordenamiento por duración (Shellsort)
        System.out.println("\n─── 2. ORDENAMIENTO POR DURACIÓN (Shellsort) ───");
        List<Turno> porDuracion = new ArrayList<>(turnos);

        inicio = System.nanoTime();
        shellsort(porDuracion);
        fin = System.nanoTime();

        System.out.println("Tiempo: " + (fin - inicio) / 1_000_000.0 + " ms");
        System.out.println("\nTurnos más cortos:");
        for (int i = 0; i < Math.min(5, porDuracion.size()); i++) {
            Turno t = porDuracion.get(i);
            Medico m = medicos.get(t.getMatriculaMedico());
            System.out.printf("  %d min - Dr. %s - %s%n",
                    t.getDuracionMin(),
                    m.getNombre(),
                    t.getMotivo());
        }

        System.out.println("\nTurnos más largos:");
        for (int i = Math.max(0, porDuracion.size() - 5); i < porDuracion.size(); i++) {
            Turno t = porDuracion.get(i);
            Medico m = medicos.get(t.getMatriculaMedico());
            System.out.printf("  %d min - Dr. %s - %s%n",
                    t.getDuracionMin(),
                    m.getNombre(),
                    t.getMotivo());
        }

        // 3. Ordenamiento por apellido de paciente (Quicksort)
        System.out.println("\n─── 3. ORDENAMIENTO POR PACIENTE (Quicksort - Lomuto) ───");
        List<Turno> porPaciente = new ArrayList<>(turnos);

        inicio = System.nanoTime();
        quicksort(porPaciente, 0, porPaciente.size() - 1);
        fin = System.nanoTime();

        System.out.println("Tiempo: " + (fin - inicio) / 1_000_000.0 + " ms");
        System.out.println("\nPrimeros 5 pacientes (orden alfabético):");
        for (int i = 0; i < Math.min(5, porPaciente.size()); i++) {
            Turno t = porPaciente.get(i);
            Paciente p = pacientes.get(t.getDniPaciente());
            Medico m = medicos.get(t.getMatriculaMedico());
            System.out.printf("  %s - Dr. %s - %s%n",
                    p.getNombre(),
                    m.getNombre(),
                    t.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
        }

        System.out.println("\n[Algoritmos implementados manualmente]");
        System.out.println("  • Inserción: O(n²) - Estable");
        System.out.println("  • Shellsort: O(n^1.5) aprox - No estable");
        System.out.println("  • Quicksort: O(n log n) promedio - No estable");
    }

    // ==================== EJERCICIO 9: AUDITORÍA UNDO/REDO ====================

    // Clase para representar acciones
    static class Accion {
        enum TipoAccion {
            AGENDAR, CANCELAR, REPROGRAMAR
        }

        TipoAccion tipo;
        Turno turno;
        LocalDateTime fechaAnterior; // Para reprogramar

        public Accion(TipoAccion tipo, Turno turno) {
            this.tipo = tipo;
            this.turno = turno;
        }

        public Accion(TipoAccion tipo, Turno turno, LocalDateTime fechaAnterior) {
            this.tipo = tipo;
            this.turno = turno;
            this.fechaAnterior = fechaAnterior;
        }

        @Override
        public String toString() {
            return switch (tipo) {
                case AGENDAR -> "Agendar(" + turno.getId() + ")";
                case CANCELAR -> "Cancelar(" + turno.getId() + ")";
                case REPROGRAMAR -> "Reprogramar(" + turno.getId() + ")";
                default -> "Acción desconocida";
            };
        }
    }

    static class AgendaConHistorial {
        private final List<Accion> pilaUndo;
        private final List<Accion> pilaRedo;
        private final SistemaTurnos sistema;

        public AgendaConHistorial(SistemaTurnos sistema) {
            this.pilaUndo = new ArrayList<>();
            this.pilaRedo = new ArrayList<>();
            this.sistema = sistema;
        }

        public boolean agendar(Turno turno) {
            // Validar que no haya conflictos
            String matricula = turno.getMatriculaMedico();
            List<Turno> turnosMedico = sistema.turnosPorMedico.get(matricula);

            for (Turno t : turnosMedico) {
                if (t.seSolapaCon(turno)) {
                    System.out.println(" Conflicto: el turno se solapa con " + t.getId());
                    return false;
                }
            }

            // Agregar turno
            sistema.turnos.add(turno);
            sistema.turnosPorId.put(turno.getId(), turno);
            turnosMedico.add(turno);

            // Registrar acción
            pilaUndo.add(new Accion(Accion.TipoAccion.AGENDAR, turno));
            pilaRedo.clear(); // Limpiar redo al hacer nueva acción

            System.out.println("✓ Turno " + turno.getId() + " agendado exitosamente");
            return true;
        }

        public boolean cancelar(String idTurno) {
            Turno turno = sistema.turnosPorId.get(idTurno);
            if (turno == null) {
                System.out.println(" Turno no encontrado");
                return false;
            }

            // Eliminar turno
            sistema.turnos.remove(turno);
            sistema.turnosPorId.remove(idTurno);
            sistema.turnosPorMedico.get(turno.getMatriculaMedico()).remove(turno);

            // Registrar acción
            pilaUndo.add(new Accion(Accion.TipoAccion.CANCELAR, turno));
            pilaRedo.clear();

            System.out.println("✓ Turno " + idTurno + " cancelado");
            return true;
        }

        public boolean reprogramar(String idTurno, LocalDateTime nuevaFecha) {
            Turno turno = sistema.turnosPorId.get(idTurno);
            if (turno == null) {
                System.out.println(" Turno no encontrado");
                return false;
            }

            LocalDateTime fechaAnterior = turno.getFechaHora();
            turno.setFechaHora(nuevaFecha);

            // Registrar acción
            pilaUndo.add(new Accion(Accion.TipoAccion.REPROGRAMAR, turno, fechaAnterior));
            pilaRedo.clear();

            System.out.println("✓ Turno " + idTurno + " reprogramado a " +
                    nuevaFecha.format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
            return true;
        }

        public boolean undo() {
            if (pilaUndo.isEmpty()) {
                System.out.println(" No hay acciones para deshacer");
                return false;
            }

            Accion accion = pilaUndo.remove(pilaUndo.size() - 1);
            System.out.println("[UNDO] ← " + accion);

            switch (accion.tipo) {
                case AGENDAR -> {
                    // Deshacer agendar = cancelar
                    sistema.turnos.remove(accion.turno);
                    sistema.turnosPorId.remove(accion.turno.getId());
                    sistema.turnosPorMedico.get(accion.turno.getMatriculaMedico()).remove(accion.turno);
                }
                case CANCELAR -> {
                    // Deshacer cancelar = re-agendar
                    sistema.turnos.add(accion.turno);
                    sistema.turnosPorId.put(accion.turno.getId(), accion.turno);
                    sistema.turnosPorMedico.get(accion.turno.getMatriculaMedico()).add(accion.turno);
                }
                case REPROGRAMAR -> {
                    // Deshacer reprogramar = volver a fecha anterior
                    accion.turno.setFechaHora(accion.fechaAnterior);
                }
            }

            pilaRedo.add(accion);
            System.out.println("Agenda vuelve al estado anterior.");
            return true;
        }

        public boolean redo() {
            if (pilaRedo.isEmpty()) {
                System.out.println(" No hay acciones para rehacer");
                return false;
            }

            Accion accion = pilaRedo.remove(pilaRedo.size() - 1);
            System.out.println("[REDO] → " + accion);

            switch (accion.tipo) {
                case AGENDAR -> {
                    // Rehacer agendar
                    sistema.turnos.add(accion.turno);
                    sistema.turnosPorId.put(accion.turno.getId(), accion.turno);
                    sistema.turnosPorMedico.get(accion.turno.getMatriculaMedico()).add(accion.turno);
                }
                case CANCELAR -> {
                    // Rehacer cancelar
                    sistema.turnos.remove(accion.turno);
                    sistema.turnosPorId.remove(accion.turno.getId());
                    sistema.turnosPorMedico.get(accion.turno.getMatriculaMedico()).remove(accion.turno);
                }
                case REPROGRAMAR -> {
                    // Rehacer reprogramar
                    LocalDateTime fechaActual = accion.turno.getFechaHora();
                    accion.turno.setFechaHora(accion.fechaAnterior);
                    accion.fechaAnterior = fechaActual;
                }
            }

            pilaUndo.add(accion);
            return true;
        }

        public void mostrarEstado() {
            if (pilaUndo.isEmpty()) {
                System.out.println("Estado: [vacía]");
                return;
            }

            System.out.print("Estado actual de pila:\n[Tope Undo] → ");
            for (int i = pilaUndo.size() - 1; i >= 0; i--) {
                System.out.print(pilaUndo.get(i));
                if (i > 0)
                    System.out.print(" → ");
            }
            System.out.println();

            if (!pilaRedo.isEmpty()) {
                System.out.print("[Pila Redo] → ");
                for (int i = pilaRedo.size() - 1; i >= 0; i--) {
                    System.out.print(pilaRedo.get(i));
                    if (i > 0)
                        System.out.print(" → ");
                }
                System.out.println();
            }
        }
    }

    public void auditoriaUndoRedo() {
        System.out.println("=== AUDITORÍA Y UNDO/REDO (Pilas) ===");

        AgendaConHistorial agenda = new AgendaConHistorial(this);

        // Simulación de acciones
        System.out.println("\n─── Realizando acciones ───");

        // Agendar turno
        Turno t1 = new Turno("T-NEW1", "30123456", "M001",
                LocalDateTime.now().plusDays(5).withHour(14).withMinute(0),
                30, "Control nuevo");
        agenda.agendar(t1);

        // Agendar otro turno
        Turno t2 = new Turno("T-NEW2", "30234567", "M002",
                LocalDateTime.now().plusDays(6).withHour(10).withMinute(0),
                45, "Consulta");
        agenda.agendar(t2);

        // Cancelar un turno existente
        if (!turnos.isEmpty()) {
            String idACancelar = turnos.get(0).getId();
            agenda.cancelar(idACancelar);
        }

        // Reprogramar
        if (!turnos.isEmpty() && turnos.size() > 1) {
            String idAReprogramar = turnos.get(1).getId();
            LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(7).withHour(15).withMinute(30);
            agenda.reprogramar(idAReprogramar, nuevaFecha);
        }

        System.out.println("\n─── Estado de las pilas ───");
        agenda.mostrarEstado();

        // Deshacer acciones
        System.out.println("\n─── Deshaciendo última acción ───");
        agenda.undo();

        System.out.println("\n─── Estado después del UNDO ───");
        agenda.mostrarEstado();

        // Rehacer
        System.out.println("\n─── Rehaciendo acción ───");
        agenda.redo();

        System.out.println("\n─── Estado final ───");
        agenda.mostrarEstado();

        System.out.println("\n[Sistema completo de Undo/Redo implementado]");
        System.out.println("  • Dos pilas (undo y redo)");
        System.out.println("  • Soporta agendar, cancelar, reprogramar");
        System.out.println("  • Multi-nivel");
        System.out.println("  • Impide redo después de acción nueva");
    }

    // ==================== EJERCICIO 10: PLANIFICADOR DE QUIRÓFANO
    // ====================

    // Clase interna: Quirófano
    class Quirofano implements Comparable<Quirofano> {
        String id;
        LocalDateTime disponibleDesde;
        int cirugiasRealizadas;

        public Quirofano(String id, LocalDateTime disponibleDesde) {
            this.id = id;
            this.disponibleDesde = disponibleDesde;
            this.cirugiasRealizadas = 0;
        }

        @Override
        public int compareTo(Quirofano otro) {
            return this.disponibleDesde.compareTo(otro.disponibleDesde);
        }

        @Override
        public String toString() {
            return String.format("%s (Disponible: %s, Cirugías: %d)",
                    id, disponibleDesde.format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                    cirugiasRealizadas);
        }
    }

    // Clase interna: Cirugía
    class Cirugia implements Comparable<Cirugia> {
        String id;
        String idMedico;
        String nombrePaciente;
        int duracionMinutos;
        LocalDateTime deadline;
        int prioridad; // 1=Alta, 2=Media, 3=Baja

        public Cirugia(String id, String idMedico, String nombrePaciente,
                int duracionMinutos, LocalDateTime deadline, int prioridad) {
            this.id = id;
            this.idMedico = idMedico;
            this.nombrePaciente = nombrePaciente;
            this.duracionMinutos = duracionMinutos;
            this.deadline = deadline;
            this.prioridad = prioridad;
        }

        @Override
        public int compareTo(Cirugia otra) {
            // Primero por prioridad (menor número = mayor prioridad)
            int cmpPrioridad = Integer.compare(this.prioridad, otra.prioridad);
            if (cmpPrioridad != 0)
                return cmpPrioridad;
            // Luego por deadline
            return this.deadline.compareTo(otra.deadline);
        }

        @Override
        public String toString() {
            String[] niveles = { "", "Alta", "Media", "Baja" };
            return String.format("Cir-%s | %s | Dr.%s | %d min | Deadline: %s | Prioridad: %s",
                    id, nombrePaciente, idMedico, duracionMinutos,
                    deadline.format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                    niveles[prioridad]);
        }
    }

    // MinHeap manual para Quirófanos
    class MinHeapQuirofanos {
        private final ArrayList<Quirofano> heap;

        public MinHeapQuirofanos() {
            this.heap = new ArrayList<>();
        }

        public void insertar(Quirofano q) {
            heap.add(q);
            heapifyUp(heap.size() - 1);
        }

        public Quirofano extraerMin() {
            if (heap.isEmpty())
                return null;

            Quirofano min = heap.get(0);
            Quirofano ultimo = heap.remove(heap.size() - 1);

            if (!heap.isEmpty()) {
                heap.set(0, ultimo);
                heapifyDown(0);
            }

            return min;
        }

        public Quirofano verMin() {
            return heap.isEmpty() ? null : heap.get(0);
        }

        public boolean estaVacio() {
            return heap.isEmpty();
        }

        public int tamanio() {
            return heap.size();
        }

        private void heapifyUp(int idx) {
            while (idx > 0) {
                int padre = (idx - 1) / 2;
                if (heap.get(idx).compareTo(heap.get(padre)) >= 0)
                    break;

                // Swap
                Quirofano temp = heap.get(idx);
                heap.set(idx, heap.get(padre));
                heap.set(padre, temp);

                idx = padre;
            }
        }

        private void heapifyDown(int idx) {
            int n = heap.size();
            while (true) {
                int menor = idx;
                int izq = 2 * idx + 1;
                int der = 2 * idx + 2;

                if (izq < n && heap.get(izq).compareTo(heap.get(menor)) < 0) {
                    menor = izq;
                }
                if (der < n && heap.get(der).compareTo(heap.get(menor)) < 0) {
                    menor = der;
                }

                if (menor == idx)
                    break;

                // Swap
                Quirofano temp = heap.get(idx);
                heap.set(idx, heap.get(menor));
                heap.set(menor, temp);

                idx = menor;
            }
        }

        public ArrayList<Quirofano> obtenerTodos() {
            return new ArrayList<>(heap);
        }
    }

    // MinHeap manual para Cirugías (por prioridad y deadline)
    class MinHeapCirugias {
        private final ArrayList<Cirugia> heap;

        public MinHeapCirugias() {
            this.heap = new ArrayList<>();
        }

        public void insertar(Cirugia c) {
            heap.add(c);
            heapifyUp(heap.size() - 1);
        }

        public Cirugia extraerMin() {
            if (heap.isEmpty())
                return null;

            Cirugia min = heap.get(0);
            Cirugia ultimo = heap.remove(heap.size() - 1);

            if (!heap.isEmpty()) {
                heap.set(0, ultimo);
                heapifyDown(0);
            }

            return min;
        }

        public boolean estaVacio() {
            return heap.isEmpty();
        }

        public int tamanio() {
            return heap.size();
        }

        private void heapifyUp(int idx) {
            while (idx > 0) {
                int padre = (idx - 1) / 2;
                if (heap.get(idx).compareTo(heap.get(padre)) >= 0)
                    break;

                Cirugia temp = heap.get(idx);
                heap.set(idx, heap.get(padre));
                heap.set(padre, temp);

                idx = padre;
            }
        }

        private void heapifyDown(int idx) {
            int n = heap.size();
            while (true) {
                int menor = idx;
                int izq = 2 * idx + 1;
                int der = 2 * idx + 2;

                if (izq < n && heap.get(izq).compareTo(heap.get(menor)) < 0) {
                    menor = izq;
                }
                if (der < n && heap.get(der).compareTo(heap.get(menor)) < 0) {
                    menor = der;
                }

                if (menor == idx)
                    break;

                Cirugia temp = heap.get(idx);
                heap.set(idx, heap.get(menor));
                heap.set(menor, temp);

                idx = menor;
            }
        }
    }

    public void planificadorQuirofano() {
        System.out.println("=== PLANIFICADOR DE QUIRÓFANO (MinHeap + Top-K) ===");

        // Inicializar quirófanos (MinHeap por disponibilidad)
        MinHeapQuirofanos heapQuirofanos = new MinHeapQuirofanos();
        LocalDateTime ahora = LocalDateTime.now();

        heapQuirofanos.insertar(new Quirofano("Q1", ahora));
        heapQuirofanos.insertar(new Quirofano("Q2", ahora.plusHours(1)));
        heapQuirofanos.insertar(new Quirofano("Q3", ahora.plusHours(2)));

        // Crear cola de cirugías pendientes (MinHeap por prioridad/deadline)
        MinHeapCirugias colaCirugias = new MinHeapCirugias();

        // Generar cirugías de ejemplo
        colaCirugias.insertar(new Cirugia("C001", "M001", "García Juan", 120, ahora.plusHours(6), 1));
        colaCirugias.insertar(new Cirugia("C002", "M002", "López Ana", 90, ahora.plusHours(8), 2));
        colaCirugias.insertar(new Cirugia("C003", "M001", "Martínez Pedro", 150, ahora.plusHours(4), 1));
        colaCirugias.insertar(new Cirugia("C004", "M003", "Rodríguez María", 60, ahora.plusHours(12), 3));
        colaCirugias.insertar(new Cirugia("C005", "M002", "Fernández Luis", 180, ahora.plusHours(5), 1));
        colaCirugias.insertar(new Cirugia("C006", "M004", "González Clara", 75, ahora.plusHours(10), 2));
        colaCirugias.insertar(new Cirugia("C007", "M001", "Sánchez Pablo", 100, ahora.plusHours(7), 2));
        colaCirugias.insertar(new Cirugia("C008", "M003", "Díaz Rosa", 45, ahora.plusHours(15), 3));

        System.out.println("\n─── Cola de Cirugías Pendientes ───");
        System.out.println("Total: " + colaCirugias.tamanio() + " cirugías");
        System.out.println("(Ordenadas por: Prioridad > Deadline)");

        // Asignaciones realizadas
        ArrayList<String> asignaciones = new ArrayList<>();
        HashMap<String, Integer> cirugiasXMedico = new HashMap<>();

        System.out.println("\n─── Proceso de Asignación ───");
        int numAsignacion = 1;

        // Asignar cirugías
        while (!colaCirugias.estaVacio() && !heapQuirofanos.estaVacio()) {
            // Extraer cirugía más prioritaria
            Cirugia cirugia = colaCirugias.extraerMin();

            // Extraer quirófano disponible más pronto
            Quirofano quirofano = heapQuirofanos.extraerMin();

            // Verificar si se puede hacer antes del deadline
            LocalDateTime inicioReal = quirofano.disponibleDesde;
            LocalDateTime finReal = inicioReal.plusMinutes(cirugia.duracionMinutos);

            boolean cumpleDeadline = finReal.isBefore(cirugia.deadline) || finReal.equals(cirugia.deadline);

            String resultado = String.format("%d) %s → %s | Inicio: %s | Fin: %s %s",
                    numAsignacion++,
                    cirugia.id,
                    quirofano.id,
                    inicioReal.format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                    finReal.format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                    cumpleDeadline ? "✓" : "⚠ RETRASADA");

            System.out.println(resultado);
            asignaciones.add(resultado);

            // Actualizar quirófano
            quirofano.disponibleDesde = finReal.plusMinutes(30); // 30 min limpieza
            quirofano.cirugiasRealizadas++;

            // Re-insertar quirófano al heap
            heapQuirofanos.insertar(quirofano);

            // Contar cirugías por médico
            cirugiasXMedico.put(cirugia.idMedico,
                    cirugiasXMedico.getOrDefault(cirugia.idMedico, 0) + 1);
        }

        // Cirugías no asignadas
        if (!colaCirugias.estaVacio()) {
            System.out.println("\n⚠ Cirugías sin asignar: " + colaCirugias.tamanio());
        }

        // Estado final de quirófanos
        System.out.println("\n─── Estado Final de Quirófanos ───");
        ArrayList<Quirofano> quirofanosFinales = heapQuirofanos.obtenerTodos();
        for (Quirofano q : quirofanosFinales) {
            System.out.println("  " + q);
        }

        // Top-K médicos más ocupados (K=3)
        System.out.println("\n─── TOP-3 Médicos Más Ocupados ───");

        // Convertir a lista y ordenar descendente
        ArrayList<Map.Entry<String, Integer>> listaMedicos = new ArrayList<>(cirugiasXMedico.entrySet());

        // Ordenamiento manual descendente por cantidad de cirugías
        for (int i = 0; i < listaMedicos.size() - 1; i++) {
            for (int j = i + 1; j < listaMedicos.size(); j++) {
                if (listaMedicos.get(j).getValue() > listaMedicos.get(i).getValue()) {
                    Map.Entry<String, Integer> temp = listaMedicos.get(i);
                    listaMedicos.set(i, listaMedicos.get(j));
                    listaMedicos.set(j, temp);
                }
            }
        }

        // Mostrar Top-3
        int K = Math.min(3, listaMedicos.size());
        for (int i = 0; i < K; i++) {
            Map.Entry<String, Integer> entry = listaMedicos.get(i);
            String nombreMedico = medicos.containsKey(entry.getKey())
                    ? medicos.get(entry.getKey()).getNombre()
                    : entry.getKey();
            System.out.printf("%d) Dr. %s: %d cirugías\n",
                    i + 1, nombreMedico, entry.getValue());
        }

        // Resumen de complejidades
        System.out.println("\n─── Complejidades Implementadas ───");
        System.out.println("• Asignar cirugía: O(log Q + log C)");
        System.out.println("  - Extraer quirófano: O(log Q)");
        System.out.println("  - Extraer cirugía: O(log C)");
        System.out.println("  - Re-insertar quirófano: O(log Q)");
        System.out.println("• Top-K médicos: O(M log M) donde M = médicos");

        // Mostrar resumen de asignaciones realizadas (uso de la colección asignaciones)
        if (!asignaciones.isEmpty()) {
            System.out.println("\n─── Resumen de Asignaciones Realizadas ───");
            for (String a : asignaciones) {
                System.out.println("  " + a);
            }
        }

        System.out.println("\n[Sistema completo implementado]");
        System.out.println("  • MinHeap de quirófanos (por disponibilidad)");
        System.out.println("  • MinHeap de cirugías (por prioridad/deadline)");
        System.out.println("  • Verificación de deadlines");
        System.out.println("  • Top-K médicos más ocupados");
    }
}
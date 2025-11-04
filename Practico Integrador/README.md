PRÁCTICO INTEGRADOR

Instrucciones generales
● Evitar colecciones que resuelvan el problema por sí solas: no TreeMap, no
HashMap, no PriorityQueue (salvo que el ejercicio lo habilite explícitamente).
Modelos sugeridos (pueden ajustarlos):
class Paciente {
String dni, nombre;
}
class Medico {
String matricula, nombre, especialidad;
}
class Turno {
String id; String dniPaciente;
String matriculaMedico;
LocalDateTime fechaHora;
int duracionMin;
String motivo;
}

1) Carga inicial y validaciones de dominio
Contexto. Importar pacientes, médicos y turnos “crudos” desde CSV.
Objetivo. Construir estructuras internas y validar reglas básicas.
Requisitos:
● Parsear 3 CSV (pacientes.csv, medicos.csv, turnos.csv).
● Validar:
○ paciente/medico existen cuando se cargan un turno.
○ Fechas futuras o presentes.
○ Duración > 0.
● Rechazar duplicados de Turno.id.
● Estructuras internas listas para los próximos ejercicios 2–10.

2) Agenda por médico con inserción/borrado y
“siguiente disponible”
Problema: Alta/baja de turnos por médico + consulta del próximo turno ≥ t.
Requisitos:
● Por cada Medico, mantener turnos ordenados por fechaHora.
● Complejidades objetivo: insert O(log n), remove O(log n),
siguiente(LocalDateTime t) O(log n).
● Prohibir doble booking (dos turnos que se superponen en una misma agenda).
Pista de estructura: Árbol balanceado por fechaHora (p.ej., AVL).
Métodos sugeridos:
interface AgendaMedico {
boolean agendar(Turno t); // false si hay conflicto
boolean cancelar(String idTurno);
Optional<Turno> siguiente(LocalDateTime t);
}
Tests. Inserciones desbalanceantes, cancelación de hoja/intermedio/raíz, siguiente al
borde de día.
3) Búsqueda de hueco libre de X minutos
Problema: Dado un médico y una duración d, hallar el primer hueco libre ≥ t0.
Requisitos:
● primerHueco(LocalDateTime t0, int durMin) →
Optional<LocalDateTime>.
● Si no hay hueco en el día, continuar al día siguiente (mismo horario laboral dado).
● Objetivo: O(log n + k) donde k es la cantidad de turnos saltados, no O(n) en tamaño
total.
4) Sala de espera con cola circular y “overflow control”
Problema: Sala de espera física (capacidad N). Nuevas llegadas pisan a la más antigua si
está llena.
Requisitos:
● Operaciones O(1): llega(dni), atiende(), peek(), size().

● Índices circulares (front, rear) y casos borde (vacía/llena).

Código base sugerido:
class SalaEspera {
SalaEspera(int capacidad);
void llega(String dni);
String atiende();
String peek();
int size();
}
Tests. Varias vueltas de índice, overflow con secuencia larga, vacía/llena alternadas.
5) Recordatorios y llamados: planificador por prioridad
temporal
Problema: Disparar recordatorios/llamadas según próxima fecha programada.
Requisitos:
● Estructura para push(recordatorio) y pop() del más próximo.
● Reprogramar un recordatorio (debe actualizar su posición).
● Complejidades objetivo: O(log n) en push/pop/reprogramar.
Código base sugerido:
class Recordatorio {
String id;
LocalDateTime fecha;
String dniPaciente;
String mensaje;
}
interface Planner {
void programar(Recordatorio r);
Recordatorio proximo();
void reprogramar(String id, LocalDateTime nuevaFecha);
int size();
}
Tests. Múltiples reprogramaciones, elementos con misma fecha, heapify desde lista.

6) Índice rápido de pacientes (Hash con chaining +
rehash)
Problema: Búsquedas/altas/bajas por dni en O(1) promedio.
Requisitos:
● Hash encadenado (listas) con rehash cuando loadFactor > 0.75.
● put, get, remove, containsKey, size, keys.
● Hash de String bien distribuido (explicar elección en README).

Código base sugerido:
interface MapaPacientes {
void put(String dni, Paciente p);
Paciente get(String dni);
boolean remove(String dni);
boolean containsKey(String dni);
int size();
Iterable<String> keys();
}
Tests. Fuerza colisiones, rehash al límite, eliminación cabeza/medio/cola en bucket.
7) Consolidación de agendas (merge y deduplicación)
Problema: Unificar dos fuentes de turnos ya ordenadas por fecha, por ejemplo
agendaLocal y agendaNube.
Requisitos:
● merge(A, B) → C en O(|A|+|B|).
● Si hay mismo id o (mismo médico y choque exacto de horario), mantener un
solo registro y loggear conflicto.
● Retornar la lista unificada ordenada.
8) Reportes operativos con múltiples ordenamientos
Problema: Generar vistas distintas del día:
● Por hora (estable).
● Por duración (gap sequence estándar).
● Por apellido de paciente (pivote final).
Requisitos:

● Implementar Inserción (estable), Shellsort, Quicksort (Lomuto).
● Medir tiempos con 1k/10k/50k turnos (archivo sintético) y comparar.
9) Auditoría y Undo/Redo de cambios en agenda
Problema: Soportar agendar, cancelar, reprogramar con deshacer/rehacer.
Requisitos:
● Dos pilas (acciones y rehacer), conservar invariantes de la agenda (sin
solapamientos).
● undo() y redo() multi-nivel; impedir redo después de una acción nueva.
API.
interface AgendaConHistorial extends AgendaMedico {
boolean reprogramar(String idTurno, LocalDateTime nuevaFecha);
boolean undo();
boolean redo();
}
10) Planificador de quirófano: asignación y top-K
cuellos de botella
Problema. Dado un flujo de solicitudes de cirugía (médico, duración, deadline), mantener:
● Asignación al primer quirófano libre cumpliendo deadline.
● En tiempo real, los K médicos con más minutos bloqueados la próxima semana.
Requisitos:
● Usar estructura que permita elegir siguiente recurso libre (p.ej., min-heap por
finOcupado de cada quirófano).
● Mantener un min-heap de tamaño K para top-K y actualizar al procesar cada
cirugía.
● Complejidad objetivo por evento: O(log Q + log K) con Q = quirófanos.
API.
class SolicitudCirugia { String id; String matricula; int durMin;
LocalDateTime deadline; }
interface PlanificadorQuirofano {
void procesar(SolicitudCirugia s);
List<String> topKMedicosBloqueados(int K);
}
Criterios de corrección

● Cumplimiento de complejidades pedidas.
● Correctitud funcional (sin solapamientos, merges correctos, estabilidad donde
aplique).
● Diseño de estructuras (nombres, invariantes, documentación breve).
● README con decisiones (hash, balanceo, manejo de empates, horarios laborales).
Modelo ejemplo del menú
===========================================
SISTEMA DE GESTIÓN DE TURNOS MÉDICOS
===========================================
Cargando datos iniciales...
> Leyendo pacientes.csv ... [OK] (30 registros)
> Leyendo medicos.csv ...... [OK] (5 registros)
> Leyendo turnos.csv ....... [OK] (120 registros)
> Validando datos ...
- 2 turnos rechazados (fecha pasada)
- 1 turno duplicado por ID = T-045
> Estructuras internas inicializadas correctamente.
-------------------------------------------
MENÚ PRINCIPAL
-------------------------------------------
1) Ver agenda de un médico
2) Buscar próximo turno disponible
3) Simular sala de espera
4) Programar recordatorios
5) Consultar índice de pacientes (Hash)
6) Consolidador de agendas
7) Reportes de ordenamiento
8) Auditoría Undo/Redo
9) Planificador de quirófano
0) Salir
-------------------------------------------
Seleccione una opción: 1
-------------------------------------------
[AGENDA DEL DR. PÉREZ - CARDIOLOGÍA]
-------------------------------------------
Turnos ordenados por fecha (AVL Tree):
ID PACIENTE FECHA Y HORA MOTIVO
---------------------------------------------------------
T-001 32045982 22/10 09:00 hs Control
T-015 32458910 22/10 09:30 hs ECG
T-022 31247856 22/10 10:00 hs Consulta general

T-037 31890432 22/10 10:30 hs Seguimiento post-op
---------------------------------------------------------
Siguiente disponible ≥ 22/10 11:00 hs → 22/10 11:15 hs
[Operación O(log n) - Árbol AVL balanceado]
-------------------------------------------
Seleccione una opción: 3
-------------------------------------------
Simulación de Sala de Espera (Cola Circular)
Capacidad máxima: 5
> Llega paciente 32045982
> Llega paciente 32458910
> Llega paciente 31890432
> Llega paciente 31247856
> Llega paciente 32500890
[Cola llena]
> Llega paciente 31111222 → Desborda, se elimina el más antiguo (32045982)
Estado actual:
FRONT → [32458910, 31890432, 31247856, 32500890, 31111222] ← REAR
Tamaño actual: 5
[Operaciones O(1)]
-------------------------------------------
Seleccione una opción: 5
-------------------------------------------
Índice rápido de Pacientes (Hash con Chaining)
Tamaño tabla = 10 | Load Factor = 0.7
[Bucket 0] → (31247856, “Pérez Juan”)
[Bucket 1] → (32045982, “Gómez Ana”) → (33112233, “Torres Luis”)
[Bucket 2] → vacío
[Bucket 3] → (31111222, “Acosta Carla”)
[Bucket 4] → (32500890, “López María”)
...
Rehash pendiente al superar load factor 0.75
[Operaciones O(1) promedio]
-------------------------------------------
Seleccione una opción: 8
-------------------------------------------
AUDITORÍA Y UNDO/REDO (Pilas)
> Agendar turno T-050
> Agendar turno T-051

> Cancelar turno T-015
> Reprogramar turno T-022 → nueva fecha 25/10 09:00 hs
Estado actual de pila:
[Tope Undo] → Reprogramar(T-022) → Cancelar(T-015) → Agendar(T-051) →
Agendar(T-050)
> Deshacer última acción...
[UNDO] ← Reprogramar(T-022)
Agenda vuelve al estado anterior.
> Rehacer...
[REDO] → Reprogramar(T-022)
-------------------------------------------
Seleccione una opción: 9
-------------------------------------------
PLANIFICADOR DE QUIRÓFANO (MinHeap + Top-K)
Quirófanos disponibles: Q1, Q2, Q3
Procesando solicitud:
> Cirugía: Dr. Gómez (Duración 180 min, Deadline 23/10 15:00 hs)
→ Asignado a Q2 (libre a las 12:30 hs)
[Evento procesado en O(log Q)]
Top 3 médicos más ocupados:
1) Dr. Pérez - 12 hs
2) Dr. Gómez - 10 hs
3) Dr. Torres - 6 hs
[Actualizado en tiempo real - Heap min de tamaño K]
-------------------------------------------
Fin de ejecución.
-------------------------------------------
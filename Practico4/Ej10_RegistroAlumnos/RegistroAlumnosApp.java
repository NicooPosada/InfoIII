/*
Ejercicio 10 – Aplicación práctica (registro de alumnos)
Crea una lista enlazada que almacene alumnos con nombre y legajo.
● Métodos: agregarAlumno(nombre, legajo), buscarAlumno(legajo),
eliminarAlumno(legajo).
● Simula un registro de tres alumnos y prueba las operaciones.
 */

package Practico4.Ej10_RegistroAlumnos;

// Clase Alumno
class Alumno {
    String nombre;
    int legajo;
    Alumno siguiente;

    public Alumno(String nombre, int legajo) {
        this.nombre = nombre;
        this.legajo = legajo;
        this.siguiente = null;
    }
}

// Clase RegistroAlumnos (lista enlazada)
class RegistroAlumnos {
    Alumno cabeza;

    public RegistroAlumnos() {
        cabeza = null;
    }

    // Agregar alumno al final
    public void agregarAlumno(String nombre, int legajo) {
        Alumno nuevo = new Alumno(nombre, legajo);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Alumno actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    // Buscar alumno por legajo
    public Alumno buscarAlumno(int legajo) {
        Alumno actual = cabeza;
        while (actual != null) {
            if (actual.legajo == legajo) {
                return actual;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    // Eliminar alumno por legajo
    public void eliminarAlumno(int legajo) {
        if (cabeza == null) return;

        // Si el alumno a eliminar está en la cabeza
        if (cabeza.legajo == legajo) {
            cabeza = cabeza.siguiente;
            return;
        }

        Alumno actual = cabeza;
        while (actual.siguiente != null && actual.siguiente.legajo != legajo) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
        }
    }

    // Imprimir lista de alumnos
    public void imprimirAlumnos() {
        Alumno actual = cabeza;
        System.out.println("Lista de alumnos:");
        while (actual != null) {
            System.out.println("Nombre: " + actual.nombre + ", Legajo: " + actual.legajo);
            actual = actual.siguiente;
        }
    }
}

// Clase principal
public class RegistroAlumnosApp {
    public static void main(String[] args) {
        RegistroAlumnos registro = new RegistroAlumnos();

        // Agregar alumnos
        registro.agregarAlumno("Ana", 1001);
        registro.agregarAlumno("Luis", 1002);
        registro.agregarAlumno("Marta", 1003);

        System.out.println("Lista inicial de alumnos:");
        registro.imprimirAlumnos();

        // Buscar alumno
        int legajoBuscado = 1002;
        Alumno encontrado = registro.buscarAlumno(legajoBuscado);
        if (encontrado != null) {
            System.out.println("\nAlumno encontrado: " + encontrado.nombre + ", Legajo: " + encontrado.legajo);
        } else {
            System.out.println("\nAlumno con legajo " + legajoBuscado + " no encontrado.");
        }

        // Eliminar alumno
        registro.eliminarAlumno(1001);
        System.out.println("\nLista después de eliminar al alumno con legajo 1001:");
        registro.imprimirAlumnos();
    }
}

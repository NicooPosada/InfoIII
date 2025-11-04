public class Paciente {
    private final String dni;
    private final String nombre;
    
    public Paciente(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }
    
    public String getDni() {
        return dni;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return nombre + " (DNI: " + dni + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paciente other = (Paciente) obj;
        return dni.equals(other.dni);
    }
    
    @Override
    public int hashCode() {
        return dni.hashCode();
    }
}

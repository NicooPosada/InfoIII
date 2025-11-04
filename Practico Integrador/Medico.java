public class Medico {
    private final String matricula;
    private final String nombre;
    private final String especialidad;
    
    public Medico(String matricula, String nombre, String especialidad) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }
    
    public String getMatricula() {
        return matricula;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    @Override
    public String toString() {
        return "Dr. " + nombre + " - " + especialidad + " (Mat: " + matricula + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Medico other = (Medico) obj;
        return matricula.equals(other.matricula);
    }
    
    @Override
    public int hashCode() {
        return matricula.hashCode();
    }
}

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Turno {
    private final String id;
    private final String dniPaciente;
    private final String matriculaMedico;
    private LocalDateTime fechaHora;
    private final int duracionMin;
    private final String motivo;
    
    public Turno(String id, String dniPaciente, String matriculaMedico, 
                 LocalDateTime fechaHora, int duracionMin, String motivo) {
        this.id = id;
        this.dniPaciente = dniPaciente;
        this.matriculaMedico = matriculaMedico;
        this.fechaHora = fechaHora;
        this.duracionMin = duracionMin;
        this.motivo = motivo;
    }
    
    public String getId() {
        return id;
    }
    
    public String getDniPaciente() {
        return dniPaciente;
    }
    
    public String getMatriculaMedico() {
        return matriculaMedico;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public int getDuracionMin() {
        return duracionMin;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public LocalDateTime getFechaFin() {
        return fechaHora.plusMinutes(duracionMin);
    }
    
    // Verificar si dos turnos se superponen
    public boolean seSolapaCon(Turno otro) {
        if (!this.matriculaMedico.equals(otro.matriculaMedico)) {
            return false;
        }
        
        LocalDateTime inicio1 = this.fechaHora;
        LocalDateTime fin1 = this.getFechaFin();
        LocalDateTime inicio2 = otro.fechaHora;
        LocalDateTime fin2 = otro.getFechaFin();
        
        return !(fin1.isBefore(inicio2) || fin1.isEqual(inicio2) || 
                 inicio1.isAfter(fin2) || inicio1.isEqual(fin2));
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        return String.format("%-8s %-10s %s %dmin - %s", 
                           id, dniPaciente, fechaHora.format(formatter), 
                           duracionMin, motivo);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Turno other = (Turno) obj;
        return id.equals(other.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

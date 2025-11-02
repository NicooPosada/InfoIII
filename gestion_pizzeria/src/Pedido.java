public class Pedido {
    private final String nombreCliente;
    private double precioTotal;
    private int tiempoPreparacion; // minutos

    public Pedido(String nombreCliente, double precioTotal, int tiempoPreparacion) {
        this.nombreCliente = nombreCliente;
        this.precioTotal = precioTotal;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "Cliente='" + nombreCliente + '\'' +
                ", Precio=" + precioTotal +
                ", Tiempo=" + tiempoPreparacion + " min}";
    }
}

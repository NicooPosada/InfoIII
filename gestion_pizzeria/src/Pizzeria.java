import java.util.ArrayList;
import java.util.List;

public class Pizzeria {
    private final List<Pedido> pedidos = new ArrayList<>();

    public void agregarPedido(Pedido p) {
        pedidos.add(p);
    }

    public void eliminarPedido(String nombreCliente) {
        pedidos.removeIf(p -> p.getNombreCliente().equalsIgnoreCase(nombreCliente));
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void mostrarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("\nNo hay pedidos registrados.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }
}

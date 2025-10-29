package gestion_pizzeria.src;

import java.util.ArrayList;
import java.util.List;

public class Pizzeria {
    private List<Pedido> pedidos;

    public Pizzeria() {
        pedidos = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void eliminarPedido(String nombreCliente) {
        pedidos.removeIf(p -> p.getNombreCliente().equalsIgnoreCase(nombreCliente));
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void mostrarPedidos() {
        pedidos.forEach(System.out::println);
    }
}

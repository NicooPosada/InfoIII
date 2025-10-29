package gestion_pizzeria.src;

import java.util.List;

public class TiempoOrdenamiento {

    private Ordenador ordenador = new Ordenador();

    public long medirInsercion(List<Pedido> pedidos) {
        long inicio = System.nanoTime();
        ordenador.insercionPorTiempo(pedidos);
        long fin = System.nanoTime();
        return (fin - inicio);
    }

    public long medirShellsort(List<Pedido> pedidos) {
        long inicio = System.nanoTime();
        ordenador.shellsortPorPrecio(pedidos);
        long fin = System.nanoTime();
        return (fin - inicio);
    }

    public long medirQuicksort(List<Pedido> pedidos) {
        long inicio = System.nanoTime();
        ordenador.quicksortPorNombre(pedidos, 0, pedidos.size() - 1);
        long fin = System.nanoTime();
        return (fin - inicio);
    }
}

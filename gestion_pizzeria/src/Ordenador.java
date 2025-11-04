import java.util.List;

public class Ordenador {

    // 1 Ordenamiento por tiempo de preparación - Inserción
    public void ordenPorTiempoInsercion(List<Pedido> pedidos) {
        for (int i = 1; i < pedidos.size(); i++) {
            Pedido actual = pedidos.get(i);
            int j = i - 1;
            while (j >= 0 && pedidos.get(j).getTiempoPreparacion() > actual.getTiempoPreparacion()) {
                pedidos.set(j + 1, pedidos.get(j));
                j--;
            }
            pedidos.set(j + 1, actual);
        }
    }

    // 2 Ordenamiento por precio - Shellsort
    public void ordenPorPrecioShell(List<Pedido> pedidos) {
        int n = pedidos.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Pedido temp = pedidos.get(i);
                int j;
                for (j = i; j >= gap && pedidos.get(j - gap).getPrecioTotal() > temp.getPrecioTotal(); j -= gap) {
                    pedidos.set(j, pedidos.get(j - gap));
                }
                pedidos.set(j, temp);
            }
        }
    }

    // 3 Ordenamiento por nombre - Quicksort
    public void ordenPorNombreQuick(List<Pedido> pedidos, int inicio, int fin) {
        if (inicio < fin) {
            int indiceParticion = particion(pedidos, inicio, fin);
            ordenPorNombreQuick(pedidos, inicio, indiceParticion - 1);
            ordenPorNombreQuick(pedidos, indiceParticion + 1, fin);
        }
    }

    private int particion(List<Pedido> pedidos, int inicio, int fin) {
        Pedido pivote = pedidos.get(fin);
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (pedidos.get(j).getNombreCliente().compareToIgnoreCase(pivote.getNombreCliente()) < 0) {
                i++;
                Pedido temp = pedidos.get(i);
                pedidos.set(i, pedidos.get(j));
                pedidos.set(j, temp);
            }
        }
        Pedido temp = pedidos.get(i + 1);
        pedidos.set(i + 1, pedidos.get(fin));
        pedidos.set(fin, temp);
        return i + 1;
    }
}

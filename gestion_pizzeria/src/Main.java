import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Pizzeria pizzeria = new Pizzeria();
            Ordenador ordenador = new Ordenador();

            int opcion;
            do {
                System.out.println("\n=== GESTIÓN DE PEDIDOS - PIZZERÍA ===");
                System.out.println("1. Agregar pedido");
                System.out.println("2. Mostrar pedidos");
                System.out.println("3. Eliminar pedido");
                System.out.println("4. Ordenar por tiempo (Inserción)");
                System.out.println("5. Ordenar por precio (Shellsort)");
                System.out.println("6. Ordenar por nombre (Quicksort)");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1 -> {
                        System.out.print("Nombre del cliente: ");
                        String nombre = sc.nextLine();
                        System.out.print("Precio total: ");
                        double precio = sc.nextDouble();
                        System.out.print("Tiempo de preparación (min): ");
                        int tiempo = sc.nextInt();
                        sc.nextLine();
                        pizzeria.agregarPedido(new Pedido(nombre, precio, tiempo));
                        System.out.println("Pedido agregado.");
                    }
                    case 2 -> pizzeria.mostrarPedidos();
                    case 3 -> {
                        System.out.print("Ingrese el nombre del cliente a eliminar: ");
                        String nombreEliminar = sc.nextLine();
                        pizzeria.eliminarPedido(nombreEliminar);
                        System.out.println("Pedido eliminado si existía.");
                    }
                    case 4 -> {
                        long tiempo4 = TiempoOrdenamiento
                                .medir(() -> ordenador.ordenPorTiempoInsercion(pizzeria.getPedidos()));
                        TiempoOrdenamiento.mostrarTiempo("Ordenamiento por tiempo", tiempo4);
                        pizzeria.mostrarPedidos();
                    }
                    case 5 -> {
                        long tiempo5 = TiempoOrdenamiento
                                .medir(() -> ordenador.ordenPorPrecioShell(pizzeria.getPedidos()));
                        TiempoOrdenamiento.mostrarTiempo("Ordenamiento por precio", tiempo5);
                        pizzeria.mostrarPedidos();
                    }
                    case 6 -> {
                        long tiempo6 = TiempoOrdenamiento.medir(() -> ordenador
                                .ordenPorNombreQuick(pizzeria.getPedidos(), 0, pizzeria.getPedidos().size() - 1));
                        TiempoOrdenamiento.mostrarTiempo("Ordenamiento por nombre", tiempo6);
                        pizzeria.mostrarPedidos();
                    }
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción inválida.");
                }
            } while (opcion != 0);
        }
    }
}

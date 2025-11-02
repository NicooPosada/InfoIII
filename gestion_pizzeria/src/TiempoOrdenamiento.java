public class TiempoOrdenamiento {

    public static long medir(Runnable algoritmo) {
        long inicio = System.nanoTime();
        algoritmo.run();
        long fin = System.nanoTime();
        return (fin - inicio) / 1_000_000; // milisegundos
    }

    public static void mostrarTiempo(String nombreAlgoritmo, long tiempo) {
        System.out.println(nombreAlgoritmo + " completado en " + tiempo + " ms");
    }
}

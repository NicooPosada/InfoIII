/*
 * 10. Secuencias "estresantes" y pruebas unitarias
a) Genere 3 secuencias de 20 números (creciente, decreciente, pseudoaleatoria
con repetidos) e inserte en un AVL (ignore repetidos si su diseño no los admite).
b) Escriba tests que verifiquen tras cada inserción: esAVL == true, alturas
correctas y orden in-order creciente.
c) Informe cuántas rotaciones totales se aplicaron en cada secuencia.
 */

package Practico5.Ej10;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Clase que representa un nodo AVL
class NodoAVL10 {
    int valor;
    NodoAVL10 izquierdo;
    NodoAVL10 derecho;
    int altura;

    public NodoAVL10(int valor) {
        this.valor = valor;
        this.altura = 1;
    }
}

// Clase principal del árbol AVL con verificadores y contador de rotaciones
class ArbolAVL10 {
    NodoAVL10 raiz;
    private int totalRotaciones = 0;
    private int rotacionesSimples = 0;
    private int rotacionesDobles = 0;

    // Obtener la altura de un nodo
    private int altura(NodoAVL10 nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    // Calcular el factor de balance
    private int factorBalance(NodoAVL10 nodo) {
        return (nodo == null) ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    // Actualizar la altura de un nodo
    private void actualizarAltura(NodoAVL10 nodo) {
        if (nodo != null) {
            nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
        }
    }

    // Rotación simple derecha
    private NodoAVL10 rotacionDerecha(NodoAVL10 n) {
        totalRotaciones++;
        rotacionesSimples++;

        NodoAVL10 x = n.izquierdo;
        NodoAVL10 B = x.derecho;

        x.derecho = n;
        n.izquierdo = B;

        actualizarAltura(n);
        actualizarAltura(x);

        return x;
    }

    // Rotación simple izquierda
    private NodoAVL10 rotacionIzquierda(NodoAVL10 n) {
        totalRotaciones++;
        rotacionesSimples++;

        NodoAVL10 y = n.derecho;
        NodoAVL10 B = y.izquierdo;

        y.izquierdo = n;
        n.derecho = B;

        actualizarAltura(n);
        actualizarAltura(y);

        return y;
    }

    // Inserción con balanceo
    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private NodoAVL10 insertarRecursivo(NodoAVL10 nodo, int valor) {
        // Inserción BST normal
        if (nodo == null) {
            return new NodoAVL10(valor);
        }

        if (valor < nodo.valor) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = insertarRecursivo(nodo.derecho, valor);
        } else {
            return nodo; // Ignorar duplicados
        }

        // Actualizar altura
        actualizarAltura(nodo);

        // Obtener factor de balance
        int balance = factorBalance(nodo);

        // Caso LL (Left-Left)
        if (balance > 1 && valor < nodo.izquierdo.valor) {
            return rotacionDerecha(nodo);
        }

        // Caso RR (Right-Right)
        if (balance < -1 && valor > nodo.derecho.valor) {
            return rotacionIzquierda(nodo);
        }

        // Caso LR (Left-Right) - Rotación doble
        if (balance > 1 && valor > nodo.izquierdo.valor) {
            rotacionesDobles++;
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        // Caso RL (Right-Left) - Rotación doble
        if (balance < -1 && valor < nodo.derecho.valor) {
            rotacionesDobles++;
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    // ============== VERIFICADORES ==============

    /**
     * Verifica si el árbol es un AVL válido
     */
    public boolean esAVL() {
        return verificarAVL(raiz).esValido;
    }

    private ResultadoVerificacion verificarAVL(NodoAVL10 nodo) {
        if (nodo == null) {
            return new ResultadoVerificacion(true, 0);
        }

        // Verificar subárbol izquierdo
        ResultadoVerificacion izq = verificarAVL(nodo.izquierdo);
        if (!izq.esValido) {
            return new ResultadoVerificacion(false, 0);
        }

        // Verificar subárbol derecho
        ResultadoVerificacion der = verificarAVL(nodo.derecho);
        if (!der.esValido) {
            return new ResultadoVerificacion(false, 0);
        }

        // Verificar factor de balance
        int balance = izq.altura - der.altura;
        if (Math.abs(balance) > 1) {
            return new ResultadoVerificacion(false, 0);
        }

        // Verificar que la altura almacenada es correcta
        int alturaEsperada = 1 + Math.max(izq.altura, der.altura);
        if (nodo.altura != alturaEsperada) {
            return new ResultadoVerificacion(false, 0);
        }

        return new ResultadoVerificacion(true, alturaEsperada);
    }

    /**
     * Verifica que el recorrido in-order es creciente (propiedad BST)
     */
    public boolean esOrdenCreciente() {
        List<Integer> valores = new ArrayList<>();
        inordenParaLista(raiz, valores);

        for (int i = 1; i < valores.size(); i++) {
            if (valores.get(i) <= valores.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private void inordenParaLista(NodoAVL10 nodo, List<Integer> lista) {
        if (nodo != null) {
            inordenParaLista(nodo.izquierdo, lista);
            lista.add(nodo.valor);
            inordenParaLista(nodo.derecho, lista);
        }
    }

    /**
     * Verifica que todas las alturas son correctas
     */
    public boolean alturasCorrectas() {
        return verificarAlturas(raiz) != -1;
    }

    private int verificarAlturas(NodoAVL10 nodo) {
        if (nodo == null) {
            return 0;
        }

        int alturaIzq = verificarAlturas(nodo.izquierdo);
        if (alturaIzq == -1)
            return -1;

        int alturaDer = verificarAlturas(nodo.derecho);
        if (alturaDer == -1)
            return -1;

        int alturaCalculada = 1 + Math.max(alturaIzq, alturaDer);

        if (nodo.altura != alturaCalculada) {
            return -1; // Error
        }

        return alturaCalculada;
    }

    // ============== UTILIDADES ==============

    public void resetearContadores() {
        totalRotaciones = 0;
        rotacionesSimples = 0;
        rotacionesDobles = 0;
    }

    public int getTotalRotaciones() {
        return totalRotaciones;
    }

    public int getRotacionesSimples() {
        return rotacionesSimples;
    }

    public int getRotacionesDobles() {
        return rotacionesDobles;
    }

    public int getAltura() {
        return altura(raiz);
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(NodoAVL10 nodo) {
        if (nodo == null)
            return 0;
        return 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho);
    }

    public void mostrarArbol() {
        mostrarArbol(raiz, "", true);
    }

    private void mostrarArbol(NodoAVL10 nodo, String prefijo, boolean esUltimo) {
        if (nodo != null) {
            System.out.println(prefijo + (esUltimo ? "└── " : "├── ") +
                    nodo.valor + " (h=" + nodo.altura + ", FB=" + factorBalance(nodo) + ")");

            if (nodo.izquierdo != null || nodo.derecho != null) {
                mostrarArbol(nodo.izquierdo, prefijo + (esUltimo ? "    " : "│   "), false);
                mostrarArbol(nodo.derecho, prefijo + (esUltimo ? "    " : "│   "), true);
            }
        }
    }

    public List<Integer> obtenerInorden() {
        List<Integer> valores = new ArrayList<>();
        inordenParaLista(raiz, valores);
        return valores;
    }
}

// Clase auxiliar para verificación
class ResultadoVerificacion {
    boolean esValido;
    int altura;

    public ResultadoVerificacion(boolean esValido, int altura) {
        this.esValido = esValido;
        this.altura = altura;
    }
}

public class Ej10 {
    public static void main(String[] args) {
        System.out.println("\nPRUEBAS EXHAUSTIVAS: SECUENCIAS ESTRESANTES PARA AVL\n");

        // Generar las 3 secuencias
        int[] secuenciaCreciente = generarSecuenciaCreciente(20);
        int[] secuenciaDecreciente = generarSecuenciaDecreciente(20);
        int[] secuenciaAleatoria = generarSecuenciaAleatoria(20);

        System.out.println("SECUENCIAS GENERADAS:");
        System.out.print("1. Creciente:    ");
        imprimirArreglo(secuenciaCreciente);
        System.out.print("2. Decreciente:  ");
        imprimirArreglo(secuenciaDecreciente);
        System.out.print("3. Aleatoria:    ");
        imprimirArreglo(secuenciaAleatoria);

        // Probar cada secuencia
        System.out.println("\nPRUEBA 1: SECUENCIA CRECIENTE (peor caso para ABB sin balance)\n");
        probarSecuencia(secuenciaCreciente, "CRECIENTE");

        System.out.println("\nPRUEBA 2: SECUENCIA DECRECIENTE\n");
        probarSecuencia(secuenciaDecreciente, "DECRECIENTE");

        System.out.println("\nPRUEBA 3: SECUENCIA ALEATORIA CON REPETIDOS\n");
        probarSecuencia(secuenciaAleatoria, "ALEATORIA");

        // Resumen comparativo
        System.out.println("\nRESUMEN COMPARATIVO\n");

        System.out.println("┌──────────────┬─────────┬──────────┬───────────┬──────────────┐");
        System.out.println("│  Secuencia   │  Nodos  │  Altura  │ Rotaciones│  Rot. Dobles │");
        System.out.println("├──────────────┼─────────┼──────────┼───────────┼──────────────┤");

        String[][] resultados = new String[3][5];
        resultados[0] = obtenerEstadisticas(secuenciaCreciente, "Creciente");
        resultados[1] = obtenerEstadisticas(secuenciaDecreciente, "Decreciente");
        resultados[2] = obtenerEstadisticas(secuenciaAleatoria, "Aleatoria");

        for (String[] fila : resultados) {
            System.out.printf("│ %-12s │ %7s │ %8s │ %9s │ %12s │%n",
                    fila[0], fila[1], fila[2], fila[3], fila[4]);
        }
        System.out.println("└──────────────┴─────────┴──────────┴───────────┴──────────────┘");

        System.out.println("\n\nOBSERVACIONES:");
        System.out.println("1. SECUENCIA CRECIENTE/DECRECIENTE:");
        System.out.println("   - Peor caso para ABB sin balance (degeneraría en lista)");
        System.out.println("   - AVL lo maneja eficientemente con rotaciones");
        System.out.println("   - Requiere más rotaciones (cada inserción puede necesitar una)");
        System.out.println("\n2. SECUENCIA ALEATORIA:");
        System.out.println("   - Generalmente requiere menos rotaciones");
        System.out.println("   - Árbol tiende a balancearse naturalmente");
        System.out.println("   - Altura similar o menor que secuencias ordenadas");
        System.out.println("\n3. ROTACIONES DOBLES:");
        System.out.println("   - Ocurren en configuraciones LR o RL (zigzag)");
        System.out.println("   - Más comunes en secuencias aleatorias");
        System.out.println("   - Cada rotación doble cuenta como 2 rotaciones simples");
    }

    /**
     * Prueba una secuencia completa con verificación después de cada inserción
     */
    private static void probarSecuencia(int[] secuencia, String nombre) {
        ArbolAVL10 avl = new ArbolAVL10();
        avl.resetearContadores();

        System.out.println("\nInsertando " + secuencia.length + " elementos...\n");

        boolean todasPasaron = true;
        int insertados = 0;

        for (int i = 0; i < secuencia.length; i++) {
            int valor = secuencia[i];
            int nodosAntes = avl.contarNodos();

            avl.insertar(valor);

            int nodosDespues = avl.contarNodos();
            boolean fueInsertado = nodosDespues > nodosAntes;

            if (fueInsertado) {
                insertados++;
            }

            // Ejecutar todos los tests
            boolean esAVL = avl.esAVL();
            boolean alturasOK = avl.alturasCorrectas();
            boolean ordenOK = avl.esOrdenCreciente();

            boolean pasaron = esAVL && alturasOK && ordenOK;

            String status = pasaron ? "✓" : "✗";
            String accion = fueInsertado ? "INSERTADO" : "DUPLICADO";

            System.out.printf("%2d. Insertar %3d: %s [%-10s] esAVL=%s alturas=%s orden=%s (h=%d, n=%d)%n",
                    i + 1, valor, status, accion,
                    esAVL ? "✓" : "✗",
                    alturasOK ? "✓" : "✗",
                    ordenOK ? "✓" : "✗",
                    avl.getAltura(),
                    avl.contarNodos());

            if (!pasaron) {
                todasPasaron = false;
                System.out.println("ERROR DETECTADO!");
            }
        }

        // Resultados finales
        System.out.println("\n" + "─".repeat(70));
        System.out.println("RESULTADOS DE LA SECUENCIA " + nombre + ":");
        System.out.println("─".repeat(70));
        System.out.println("✓ Elementos insertados:     " + insertados + "/" + secuencia.length);
        System.out.println("✓ Duplicados ignorados:     " + (secuencia.length - insertados));
        System.out.println("✓ Nodos en el árbol:        " + avl.contarNodos());
        System.out.println("✓ Altura final:             " + avl.getAltura());
        System.out.println("✓ Rotaciones totales:       " + avl.getTotalRotaciones());
        System.out.println("  - Rotaciones simples:     " + avl.getRotacionesSimples());
        System.out.println("  - Rotaciones dobles:      " + avl.getRotacionesDobles());
        System.out.println("✓ Todas las pruebas:        " + (todasPasaron ? "PASARON ✓" : "FALLARON ✗"));

        System.out.println("\nRecorrido in-order: " + avl.obtenerInorden());

        System.out.println("\nEstructura del árbol final:");
        avl.mostrarArbol();
    }

    /**
     * Obtiene estadísticas de una secuencia
     */
    private static String[] obtenerEstadisticas(int[] secuencia, String nombre) {
        ArbolAVL10 avl = new ArbolAVL10();
        avl.resetearContadores();

        for (int valor : secuencia) {
            avl.insertar(valor);
        }

        return new String[] {
                nombre,
                String.valueOf(avl.contarNodos()),
                String.valueOf(avl.getAltura()),
                String.valueOf(avl.getTotalRotaciones()),
                String.valueOf(avl.getRotacionesDobles())
        };
    }

    // ============== GENERADORES DE SECUENCIAS ==============

    private static int[] generarSecuenciaCreciente(int n) {
        int[] secuencia = new int[n];
        for (int i = 0; i < n; i++) {
            secuencia[i] = i + 1;
        }
        return secuencia;
    }

    private static int[] generarSecuenciaDecreciente(int n) {
        int[] secuencia = new int[n];
        for (int i = 0; i < n; i++) {
            secuencia[i] = n - i;
        }
        return secuencia;
    }

    private static int[] generarSecuenciaAleatoria(int n) {
        Random rand = new Random(42); // Semilla fija para reproducibilidad
        int[] secuencia = new int[n];

        for (int i = 0; i < n; i++) {
            // Generar números en rango [1, 15] para asegurar repetidos
            secuencia[i] = rand.nextInt(15) + 1;
        }

        return secuencia;
    }

    private static void imprimirArreglo(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }
}
package Practico5.Ej7;

class NodoAVL {
    int valor;
    int altura;
    NodoAVL izq, der;

    public NodoAVL(int valor) {
        this.valor = valor;
        this.altura = 0;
        this.izq = null;
        this.der = null;
    }

    public int getAltura() {
        return altura;
    }

    public int getValor() {
        return valor;
    }

    public NodoAVL getIzq() {
        return izq;
    }

    public NodoAVL getDer() {
        return der;
    }
}

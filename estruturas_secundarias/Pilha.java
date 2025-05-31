package estruturas_secundarias;

public class Pilha<T> {

    class Nodo {

        public T elemento;
        public Nodo proximo;
        public Nodo anterior;

        public Nodo(T elemento) {
            this.elemento = elemento;
            this.proximo = null;
            this.anterior = null;
        }
    }

    private Nodo inicio;
    private Nodo topo; // fim
    private int nElementos;

    public Pilha() {
        this.inicio = null;
        this.topo = null;
        this.nElementos = 0;
    }

    public boolean estaVazia() {
        return this.nElementos == 0;
    }

    public int tamanho() {
        return this.nElementos;
    }

    public void imprime() {
        Nodo cursor = this.topo;
        for(int i=0;i<this.nElementos;i++) {
            System.out.println("|\t" + cursor.elemento + "\t|");
            cursor = cursor.anterior;
        }
        System.out.println("----------------------");
    }

    public void empilha(T elemento) {

        Nodo novo = new Nodo(elemento);

        if(this.estaVazia()) {
            this.inicio = novo;
        } else {
            this.topo.proximo = novo;
            novo.anterior = this.topo;
        }

        this.topo = novo;

        this.nElementos++;
    }

    public T desempilha() {

        if(this.estaVazia()) {
            System.out.println("Lista vazia. Não é possível remover.");
            return null;
        }

        Nodo nodoRemovido = this.topo;

        if(this.nElementos == 1) {

            this.inicio = null;
            this.topo = null;
        } else {

            this.topo = nodoRemovido.anterior;

            nodoRemovido.anterior.proximo = null;
            nodoRemovido.anterior = null;
        }

        this.nElementos--;

        return nodoRemovido.elemento;
    }

    public T espia() {

        if(this.estaVazia()) {
            System.out.println("Lista vazia! Não é possível espiar.");
            return null;
        }

        return this.topo.elemento;
    }

    public boolean contem(T elemento) {

        Nodo cursor = this.inicio;
        for(int i=0;i<this.nElementos;i++) {
            if(cursor.elemento.equals(elemento)) {
                return true;
            }
            cursor = cursor.proximo;
        }

        return false;
    }

}

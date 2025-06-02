package estruturas_principais;

import estruturas_secundarias.Fila;

public class ArvoreBinariaBusca<T extends Comparable<T>> {

    class Nodo {

        public T elemento;
        public Nodo esquerdo;
        public Nodo direito;

        public Nodo(T elemento) {
            this.elemento = elemento;
            this.esquerdo = null;
            this.direito = null;
        }
    }

    public Nodo raiz;
    public int nElementos;

    public ArvoreBinariaBusca() {
        this.raiz = null;
        this.nElementos = 0;
    }

    public int tamanho() {
        return this.nElementos;
    }

    public boolean estaVazia() {
        return this.raiz == null;
    }

    public void imprimeEmLargura() {

        Fila<Nodo> fila = new Fila<Nodo>();

        fila.enfileira(this.raiz);
        while (!fila.estaVazia()) {

            Nodo cursor = fila.desenfileira();

            System.out.print(cursor.elemento + " ");

            if (cursor.esquerdo != null) {
                fila.enfileira(cursor.esquerdo);
            }

            if (cursor.direito != null) {
                fila.enfileira(cursor.direito);
            }
        }

        System.out.println();

    }

    public void imprimePreOrdem() {
        this.preOrdem(this.raiz);
        System.out.println();
    }

    public void imprimePosOrdem() {
        this.posOrdem(this.raiz);
        System.out.println();
    }

    public void imprimeEmOrdem() {
        this.emOrdem(this.raiz);
        System.out.println();
    }

    public void preOrdem(Nodo nodo) {

        if (nodo == null)
            return;

        System.out.print(nodo.elemento + " ");
        this.preOrdem(nodo.esquerdo);
        this.preOrdem(nodo.direito);
    }

    public void posOrdem(Nodo nodo) {

        if (nodo == null)
            return;

        this.posOrdem(nodo.esquerdo);
        this.posOrdem(nodo.direito);
        System.out.print(nodo.elemento + " ");
    }

    public void emOrdem(Nodo nodo) {

        if (nodo == null)
            return;

        this.emOrdem(nodo.esquerdo);
        System.out.print(nodo.elemento + " ");
        this.emOrdem(nodo.direito);
    }

    // retorna uma lista ordenada que vai ter sess elementos escritos no arquivo de saida
    public ListaDuplamenteEncadeada<T> listaEmOrdem() {
        ListaDuplamenteEncadeada<T> lista = new ListaDuplamenteEncadeada<>();
        this.listaEmOrdem(this.raiz, lista);
        return lista;
    }

    private void listaEmOrdem(Nodo nodo, ListaDuplamenteEncadeada<T> lista) {
        if (nodo == null) return;

        this.listaEmOrdem(nodo.esquerdo, lista);
        lista.insereFinal(nodo.elemento);
        this.listaEmOrdem(nodo.direito, lista);
    }

    public void insere(T elemento) {
        this.insere(elemento, this.raiz);
    }

    public void insere(T elemento, Nodo nodo) {

        Nodo novo = new Nodo(elemento);

        if (nodo == null) {
            this.raiz = novo;
            this.nElementos++;
            return;
        }

        if (elemento.compareTo(nodo.elemento) < 0) {
            if (nodo.esquerdo == null) {
                nodo.esquerdo = novo;
                this.nElementos++;
                return;
            } else {
                this.insere(elemento, nodo.esquerdo);
            }
        }

        if (elemento.compareTo(nodo.elemento) > 0) {
            if (nodo.direito == null) {
                nodo.direito = novo;
                this.nElementos++;
                return;
            } else {
                this.insere(elemento, nodo.direito);
            }
        }
    }

    private Nodo maiorElemento(Nodo nodo) {
        while (nodo.direito != null) {
            nodo = nodo.direito;
        }
        return nodo;
    }

    private Nodo menorElemento(Nodo nodo) {
        while (nodo.esquerdo != null) {
            nodo = nodo.esquerdo;
        }
        return nodo;
    }

    public boolean remove(T elemento) {
        return this.remove(elemento, this.raiz) != null;
    }

    private Nodo remove(T elemento, Nodo nodo) {

        if (nodo == null) {
            System.out.println("Valor não encontrado");
            return null;
        }

        if (elemento.compareTo(nodo.elemento) < 0) {
            nodo.esquerdo = this.remove(elemento, nodo.esquerdo);
        } else if (elemento.compareTo(nodo.elemento) > 0) {
            nodo.direito = this.remove(elemento, nodo.direito);
        } else {

//	    	if(nodo.esquerdo == null && nodo.direito == null) {
//	    		return null;
//	    	}

            if (nodo.esquerdo == null) {
                this.nElementos--;
                return nodo.direito;
            } else if (nodo.direito == null) {
                this.nElementos--;
                return nodo.esquerdo;
            } else {
                Nodo substituto = this.menorElemento(nodo.direito);
                nodo.elemento = substituto.elemento;
                this.remove(substituto.elemento, nodo.direito);
            }
        }

        return nodo;
    }

    public boolean busca(T elemento) {
        return this.busca(elemento, this.raiz);

    }

    public boolean busca(T elemento, Nodo nodo) {

        if (nodo == null) {
            return false;
        }

        if (elemento.compareTo(nodo.elemento) < 0) {
            return this.busca(elemento, nodo.esquerdo);
        } else if (elemento.compareTo(nodo.elemento) > 0) {
            return this.busca(elemento, nodo.direito);
        } else {
            return true;
        }
    }

    public T acessaElemento(T elemento) {
        return this.acessaElemento(elemento, this.raiz);
    }

    private T acessaElemento(T elemento, Nodo nodo) {
        if (nodo == null) {
            return null; // Elemento não encontrado
        }

        int comparacao = elemento.compareTo(nodo.elemento);

        if (comparacao < 0) {
            return this.acessaElemento(elemento, nodo.esquerdo); // Busca na subárvore esquerda
        } else if (comparacao > 0) {
            return this.acessaElemento(elemento, nodo.direito); // Busca na subárvore direita
        } else {
            return nodo.elemento; // Elemento encontrado, retorna o valor do nó
        }
    }

    private int altura(Nodo nodo) {

        if (nodo == null) {
            return -1;
        }

        int alturaEsquerda = this.altura(nodo.esquerdo) + 1;
        int alturaDireita = this.altura(nodo.direito) + 1;

        int altura = alturaEsquerda > alturaDireita ? alturaEsquerda : alturaDireita;

        return altura;

    }

    public int altura() {
        return this.altura(this.raiz);
    }
}

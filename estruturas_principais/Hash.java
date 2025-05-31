package estruturas_principais;

import estruturas_principais.ListaDuplamenteEncadeada;
import estruturas_principais.PalavraChave;

public class Hash {

    public ArvoreBinariaBusca<PalavraChave>[] vetor;
    public int nElementos;

    public Hash(int capacidade) {
        // cast generics
        this.vetor = (ArvoreBinariaBusca<PalavraChave>[]) new ArvoreBinariaBusca[capacidade];
        for (int i = 0; i < vetor.length; i++) {
            this.vetor[i] = new ArvoreBinariaBusca<PalavraChave>();
        }
        this.nElementos = 0;
    }

    public int tamanho() {
        return this.nElementos;
    }

    public void imprime() {
        for (int i = 0; i < vetor.length; i++) {
            vetor[i].imprimeEmOrdem();
        }
    }

    private int funcaoHashDiv(PalavraChave elemento) {
        String palavra = elemento.getPalavra();
        int letraAscii = (int) palavra.charAt(0);
        return letraAscii - 97;
    }

    public void insere(PalavraChave elemento) {
        int endereco = funcaoHashDiv(elemento);
        this.vetor[endereco].insere(elemento);
        this.nElementos++;
    }

    public boolean remove(PalavraChave elemento) {
        int endereco = funcaoHashDiv(elemento);
        boolean removeu = this.vetor[endereco].remove(elemento);

        if(removeu) this.nElementos--;

        return removeu;
    }

    public boolean contem(PalavraChave elemento) {
        int endereco = funcaoHashDiv(elemento);
        return this.vetor[endereco].busca(elemento);
    }
}

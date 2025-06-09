package estruturas_principais;

import estruturas_principais.ListaDuplamenteEncadeada;
import estruturas_principais.PalavraChave;

public class Hash {

    public ArvoreBinariaBusca<PalavraChave>[] vetor;
    public int nElementos;

    public Hash(int capacidade) {
        this.vetor = (ArvoreBinariaBusca<PalavraChave>[]) new ArvoreBinariaBusca[capacidade];
        for (int i = 0; i < vetor.length; i++) {
            this.vetor[i] = new ArvoreBinariaBusca<PalavraChave>();
        }
        this.nElementos = 0;
    }

    public int tamanho() {
        return this.nElementos;
    }

    public PalavraChave busca(PalavraChave elemento) {
        int chave = funcaoHashDiv(elemento);
        if (chave < 0 || chave >= vetor.length) {
            return null; // Caso a função de hash retorne um índice inválido (defensivo)
        }
        return this.vetor[chave].acessaElemento(elemento);
    }

    private int funcaoHashDiv(PalavraChave elemento) {
        String palavra = elemento.getPalavra();

        // Com a lógica de limpeza em `App.limpaPalavraParaIndice`,
        // palavras que chegam aqui deverão sempre começar com uma letra minúscula.
        // Esta é uma camada de segurança.
        if (palavra.isEmpty() || palavra.charAt(0) < 'a' || palavra.charAt(0) > 'z') {
            // Se, por alguma razão, uma palavra inválida chegar aqui, retorna 0
            // para evitar ArrayIndexOutOfBoundsException.
            return 0;
        }

        int letraAscii = (int) palavra.charAt(0);
        return letraAscii - 97; // 'a' em ASCII é 97
    }

    public void insere(PalavraChave elemento) {
        int endereco = funcaoHashDiv(elemento);
        this.vetor[endereco].insere(elemento);
        this.nElementos++;
    }

    public ArvoreBinariaBusca<PalavraChave> acesse(int pos) {
        if (pos < 0 || pos >= this.vetor.length) {
            System.out.println("Posicao invalida");
            return null;
        }
        return this.vetor[pos];
    }

    public boolean remove(PalavraChave elemento) {
        int endereco = funcaoHashDiv(elemento);
        if (endereco < 0 || endereco >= vetor.length) {
            return false;
        }
        boolean removeu = this.vetor[endereco].remove(elemento);

        if(removeu) this.nElementos--;

        return removeu;
    }

    public boolean contem(PalavraChave elemento) {
        int endereco = funcaoHashDiv(elemento);
        if (endereco < 0 || endereco >= vetor.length) {
            return false;
        }
        return this.vetor[endereco].busca(elemento);
    }
}

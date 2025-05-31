package estruturas_principais;

public class PalavraChave {
    String palavra; // palavra em questao
    ListaDuplamenteEncadeada<Integer> ocorrencias; // ocorrencias dessa palavra ao longo do arquivo

    public PalavraChave(String palavra, ListaDuplamenteEncadeada<Integer> ocorrencias) {
        this.palavra = palavra;
        this.ocorrencias = ocorrencias;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public ListaDuplamenteEncadeada<Integer> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(ListaDuplamenteEncadeada<Integer> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }
}

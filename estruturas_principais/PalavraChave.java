package estruturas_principais;

public class PalavraChave implements Comparable<PalavraChave> {
    private String palavra; // palavra em questao
    private ListaDuplamenteEncadeada<Integer> ocorrencias; // ocorrencias dessa palavra ao longo do arquivo

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

    public void adicionarOcorrencia(Integer linhaOcorrencia) {
        if (!this.ocorrencias.contem(linhaOcorrencia)) { // so para garantir que nao repita a linha de ocorrencia
            this.ocorrencias.insereFinal(linhaOcorrencia);
        }
    }

    @Override
    public int compareTo(PalavraChave o) {
        return this.palavra.compareTo(o.getPalavra());
    }
}

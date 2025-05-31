//public class Hash {
//
//    public ListaDuplamenteEncadeada<String>[] vetor;
//    public int nElementos;
//
//    public Hash(int capacidade) {
//        // cast generics
//        this.vetor = (ListaDuplamenteEncadeada<String>[]) new ListaDuplamenteEncadeada[capacidade];
//        for (int i = 0; i < vetor.length; i++) {
//            this.vetor[i] = new ListaDuplamenteEncadeada<String>();
//        }
//        this.nElementos = 0;
//    }
//
//    public int tamanho() {
//        return this.nElementos;
//    }
//
//    public void imprime() {
//        System.out.println("Chave\tValor");
//        for (int i = 0; i < vetor.length; i++) {
//            System.out.print(i + " -->\t");
//            vetor[i].imprime();
//        }
//    }
//
//    private int funcaoHashDiv(Integer elemento) {
//        return elemento % this.vetor.length;
//    }
//
//    public void insere(Integer elemento) {
//        int endereco = funcaoHashDiv(elemento);
//        this.vetor[endereco].insereFinal(elemento);
//        this.nElementos++;
//    }
//
//    public boolean remove(int elemento) {
//        int endereco = funcaoHashDiv(elemento);
//        boolean removeu = this.vetor[endereco].removeElemento(elemento);
//
//        if(removeu) this.nElementos--;
//
//        return removeu;
//    }
//
//    public boolean contem(int elemento) {
//        int endereco = funcaoHashDiv(elemento);
//        return this.vetor[endereco].contem(elemento);
//    }
//}

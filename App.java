import estruturas_principais.ArvoreBinariaBusca;
import estruturas_principais.Hash;
import estruturas_principais.ListaDuplamenteEncadeada;
import estruturas_principais.PalavraChave;

import java.io.*;
import java.text.Normalizer;
import java.util.Scanner;

public class App {
    public void run() {
        try {
            // (OK) Ler um arquivo do tipo TXT contendo o texto a
            //      ser esquadrinhado à procura de palavras que
            //      pertençam ao índice remissivo;
            Scanner entrada = new Scanner(new File("entrada_saida/entrada.txt"));
            Hash indiceRemissivo = getPalavrasChaves();
            int numeroLinha = 1;

            while(entrada.hasNextLine()) {
                String linha = entrada.nextLine().trim();
                String[] palavras = linha.split(",\\s*|\\s+");
                // separa por ", " ou qualquer quantidade de espaços

                for (String palavra : palavras) {
                    // normaliza a palavra
                    String palavraNormalizada = normaliza(palavra);
                    PalavraChave palavraChaveNormalizada = new PalavraChave(palavraNormalizada);
                    PalavraChave palavraParaAtualizar = indiceRemissivo.busca(palavraChaveNormalizada);

                    if (palavraParaAtualizar != null) {

                        palavraParaAtualizar.adicionarOcorrencia(numeroLinha);

                    }
                }

                // incrementar o número da linha
                numeroLinha++;
            }
            entrada.close();
            escreveIndiceRemissivo(indiceRemissivo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // metodo que armazena as palavras chaves na ABB dentro da HASH
    // retorna a HASH em questão
    // (OK) Ler um arquivo do tipo TXT (texto) contendo
    //      um número arbitrário de palavras-chave que
    //      deverão constituir o índice remissivo;
    private Hash getPalavrasChaves() {
//        ListaDuplamenteEncadeada<PalavraChave> palavrasChave = new ListaDuplamenteEncadeada<PalavraChave>();
        Hash indiceRemissivo = new Hash(26);
        try {
            Scanner scanner = new Scanner(new File("entrada_saida/palavras_chave"));
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine().trim(); // recebe a linha tirando espaços vazios no inicio e fim
                String[] palavras = linha.split(", "); // armazena as palavras chave

                for (String palavra : palavras) {
                    String palavraNormalizada = normaliza(palavra);

                    if (!palavraNormalizada.isEmpty()) {
                        indiceRemissivo.insere(new PalavraChave(palavraNormalizada));
                    }

                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return indiceRemissivo;
    }

    private void escreveIndiceRemissivo(Hash palavrasChave) {
        String caminhoArquivo = "entrada_saida/saida.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {

            // Percorre todas as posições do vetor de árvores
            for (int i = 0; i < palavrasChave.vetor.length; i++) {
                ArvoreBinariaBusca<PalavraChave> abb = palavrasChave.vetor[i];

                // Obtém a lista ordenada de palavras
                ListaDuplamenteEncadeada<PalavraChave> listaPalavras = abb.listaEmOrdem();

                // Escreve cada palavra e suas ocorrências no arquivo
                for (int j = 0; j < listaPalavras.tamanho(); j++) {
                    PalavraChave pc = listaPalavras.acesse(j);
                    if (pc != null) {
                        bw.write(pc.getPalavra() + ": " + pc.getOcorrencias());
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String normaliza(String palavra) {
        if (palavra == null) return null;

        // 1. Converte para minúsculas
        String resultado = palavra.toLowerCase();

        // 2. Remove acentos
        resultado = Normalizer.normalize(resultado, Normalizer.Form.NFD);
        resultado = resultado.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // 3. Remove tudo que NÃO for letra minúscula ou traço
        resultado = resultado.replaceAll("[^a-z-]", "");

        return resultado;
    }
}

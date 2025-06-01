import estruturas_principais.ListaDuplamenteEncadeada;
import estruturas_principais.PalavraChave;

import java.io.*;
import java.text.Normalizer;
import java.util.Scanner;

public class App {
    public void run() {
        try {
            Scanner entrada = new Scanner(new File("entrada_saida/entrada.txt"));
            ListaDuplamenteEncadeada<PalavraChave> palavrasChave = getPalavrasChaves();
            int numeroLinha = 1;

            while(entrada.hasNextLine()) {
                String linha = entrada.nextLine().trim();
                String[] palavras = linha.split(",\\s*|\\s+");
                // separa por ", " ou qualquer quantidade de espaços

                for (String palavra : palavras) {
                    // normaliza a palavra
                    String palavraNormalizada = normaliza(palavra);

                    if (palavrasChave.contem(palavraNormalizada)) {

                        // percorre a lista até achar a PalavraChave correspondente
                        for (int i = 0; i < palavrasChave.tamanho(); i++) {
                            PalavraChave pc = palavrasChave.acesse(i);
                            if (pc.getPalavra().equals(palavraNormalizada)) {
                                // se a palavra chave for igual a palavra da linha, adiciona o numero da linha na ocorrencia
                                pc.adicionarOcorrencia(numeroLinha);
                                break; // já achou, pode sair do loop
                            }
                        }
                    }
                }

                // incrementar o número da linha
                numeroLinha++;
            }
            entrada.close();
            escreveIndiceRemissivo(palavrasChave);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ListaDuplamenteEncadeada<PalavraChave> getPalavrasChaves() {
        ListaDuplamenteEncadeada<PalavraChave> palavrasChave = new ListaDuplamenteEncadeada<PalavraChave>();
        try {
            Scanner scanner = new Scanner(new File("entrada_saida/palavras_chave"));
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine().trim(); // recebe a linha tirando espaços vazios no inicio e fim
                String[] palavras = linha.split(", "); // armazena as palavras chave

                for (String palavra : palavras) {
                    String palavraNormalizada = normaliza(palavra);

                    if (!palavraNormalizada.isEmpty()) {
                        palavrasChave.insereFinal(new PalavraChave(palavraNormalizada));
                    }

                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return palavrasChave;
    }

    private void write(String linha) {
        String caminhoArquivo = "entrada_saida/saida.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            bw.write(linha);
            bw.newLine();  // para pular linha
            System.out.println("Arquivo escrito com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void escreveIndiceRemissivo(ListaDuplamenteEncadeada<PalavraChave> palavrasChave) {
        String caminhoArquivo = "entrada_saida/saida.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {

            for (int i = 0; i < palavrasChave.tamanho(); i++) {
                PalavraChave pc = palavrasChave.acesse(i);
                bw.write(pc.getPalavra() + " " + pc.getOcorrencias());
                bw.newLine();
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

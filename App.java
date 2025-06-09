import estruturas_principais.ArvoreBinariaBusca;
import estruturas_principais.Hash;
import estruturas_principais.PalavraChave;

import java.io.*;
import java.text.Normalizer;
import java.util.Scanner;

public class App {
    public void run() {
        try {
            Scanner entrada = new Scanner(new File("entrada_saida/entrada.txt"));
            Hash indiceRemissivo = getPalavrasChaves();

            int numeroLinha = 1;

            while(entrada.hasNextLine()) {
                String linha = entrada.nextLine().trim();

                // Normaliza a linha inteira
                String linhaNormalizadaPreliminar = normalizaLinhaCompleta(linha);

                // divide a linha em palavras usando um ou mais espaços como delimitador.
                // aq as palavras já não tem nenhum caractere fora os que são permitidos
                String[] palavrasBrutas = linhaNormalizadaPreliminar.split("\\s+");

                for (String palavraBruta : palavrasBrutas) {
                    // aq limpa individualmente cada palavra, para evitar casos como: palavra--chave, palavra--, --palavra
                    String palavraProcessada = limpaPalavraParaIndice(palavraBruta);

                    // pula a palavra se for ""
                    if (palavraProcessada.isEmpty()) {
                        continue;
                    }

                    PalavraChave palavraChaveParaBusca = new PalavraChave(palavraProcessada);

                    // busca a palavra na Hash
                    PalavraChave palavraParaAtualizar = indiceRemissivo.busca(palavraChaveParaBusca);

                    if (palavraParaAtualizar != null) {
                        palavraParaAtualizar.adicionarOcorrencia(numeroLinha);
                    }
                }
                numeroLinha++;
            }
            entrada.close();
            escreveIndiceRemissivo(indiceRemissivo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String normalizaLinhaCompleta(String linha) {
        if (linha == null) return "";

        String resultado = linha.toLowerCase(); // Converte para minúsculas
        resultado = Normalizer.normalize(resultado, Normalizer.Form.NFD); // Quebra os acentos (ã -> ~a)
        resultado = resultado.replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); // Remover acentos e diacríticos da string.
        resultado = resultado.replaceAll("[^a-z0-9\\s-]", ""); // so aceita letras, numeros e hifen
        resultado = resultado.replaceAll("\\s+", " ").trim(); // substitui espaços em branco em excesso (2 ou + espaços seguidos) por um único espaço ("   " --> " ")

        return resultado;
    }

    private String limpaPalavraParaIndice(String palavra) {
        if (palavra == null || palavra.isEmpty()) {
            return "";
        }
        String resultado = palavra.replaceAll("^[^a-z]+", ""); // remove qualquer caractere que está no inicio da palavra e não é uma letra minuscula

        if (resultado.isEmpty()) { // Se a palavra ficou vazia após remover o início, não é válida.
            return "";
        }

        StringBuilder palavraFinal = new StringBuilder(); // so pra tirar a reclamacao do intellij quando eu fazia String palavra += "alguma coisa"
        boolean lastCharWasHyphen = false; // para evitar hífens consecutivos (ex: "a--b")

        for (int i = 0; i < resultado.length(); i++) {
            char c = resultado.charAt(i);

            if (c >= 'a' && c <= 'z') { // Caractere é uma letra
                palavraFinal.append(c);
                lastCharWasHyphen = false;
            } else if (c == '-') { // caractere é um hífen
                // hífen só é permitido se a palavra já tem pelo menos um caractere (não no início)
                // e o último caractere adicionado não foi outro hífe
                if (palavraFinal.length() > 0 && !lastCharWasHyphen) {
                    palavraFinal.append(c);
                    lastCharWasHyphen = true;
                }
            }
            // outros caracteres (que não são a-z, 0-9, hífen) já foram removidos em normalizaLinhaCompleta.
        }

        String palavraConstruidaStr = palavraFinal.toString();

        // hífen não pode estar no fim
        // "palavra-" -> "palavra"
        palavraConstruidaStr = palavraConstruidaStr.replaceAll("-+$", "");

        // garantir que a palavra não ficou vazia ou que meu primeiro caractere não seja uma letra.
        if (palavraConstruidaStr.isEmpty() || palavraConstruidaStr.charAt(0) < 'a' || palavraConstruidaStr.charAt(0) > 'z') {
            return "";
        }

        return palavraConstruidaStr.trim(); // Retorna a palavra final limpa
    }

    private Hash getPalavrasChaves() {
        Hash indiceRemissivo = new Hash(26);
        try {
            Scanner scanner = new Scanner(new File("entrada_saida/palavras_chave.txt")); // Correção da extensão
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine().trim();
                // normaliza e divide a linha das palavras-chave da mesma forma que o texto principal
                String linhaNormalizadaPreliminar = normalizaLinhaCompleta(linha);
                String[] palavrasBrutas = linhaNormalizadaPreliminar.split("\\s+");

                for (String palavraBruta : palavrasBrutas) {
                    // limpa cada palavra-chave individualmente
                    String palavraProcessada = limpaPalavraParaIndice(palavraBruta);

                    if (!palavraProcessada.isEmpty()) {
                        indiceRemissivo.insere(new PalavraChave(palavraProcessada));
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
            for (int i = 0; i < palavrasChave.vetor.length; i++) {
                ArvoreBinariaBusca<PalavraChave> abb = palavrasChave.vetor[i];
                abb.imprimeEmOrdem(bw); // Método que percorre a ABB e escreve no BufferedWriter
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

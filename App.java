import java.io.*;
import java.util.Scanner;

public class App {
    public void run() {
        try {
            Scanner scanner = new Scanner(new File("entrada_saida/entrada.txt"));
            while(scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                write(linha);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
}

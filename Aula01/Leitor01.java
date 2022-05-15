import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// OpenCsv

public class Leitor01 {

    public static void main(String [] args) {
        new Leitor01().ler();
    }

    private void ler() {
        List<List<String>> regs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("livros.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String [] lins = linha.split(";");
                regs.add(Arrays.asList(lins));
                System.out.println(lins[0] + " - " + lins[1] + " - " + lins[2]);
            }
        } catch (IOException e) {
            System.out.println("Erro de Leitura: " + e.getMessage());
        }
    }

}

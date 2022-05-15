import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// OpenCsv

public class Leitor02 {

    public static void main(String [] args) {
        new Leitor02().ler();
    }

    private void ler() {
        List<List<String>> regs = new ArrayList<>();
        String linha;
        try (Scanner sc = new Scanner(new File("livros.csv"));) {
            while (sc.hasNextLine()) {
                linha = sc.nextLine();
                String [] lins = linha.split(";");
                regs.add(Arrays.asList(lins));
                System.out.println(lins[0] + " - " + lins[1] + " - " + lins[2]);

                // List<String> vals = new ArrayList<String>();
                // try (Scanner lin = new Scanner(sc.nextLine())) {
                //     lin.useDelimiter(";");
                //     while (lin.hasNext()) {
                //         vals.add(lin.next());
                //     }
                // }
                // regs.add(vals);
                // System.out.println(vals.get(0) + " - " + vals.get(1) + " - " + vals.get(2));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro de Leitura: " + e.getMessage());
        }
    }

}

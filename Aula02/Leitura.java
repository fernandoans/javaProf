// Texto em https://www.gutenberg.org/files/11/old/alice30.txt

import java.util.*;
import java.text.*;
import java.nio.file.*;
import java.io.IOException;

public class Leitura {
    private List<String> palavras;

    public static void main(String [] args) throws IOException {
        new Leitura().executar();
    }

    public void executar() throws IOException {
        var conteudo = Files.readString(Path.of("alice.txt"));
        System.out.println(new SimpleDateFormat("mm:ss.SSS").format(new Date()));
        palavras = List.of(conteudo.split("\\PL+"));
        System.out.println(palavras.size());
        System.out.println(new SimpleDateFormat("mm:ss.SSS").format(new Date()));
        System.out.println(calcTotal1());
        System.out.println(new SimpleDateFormat("mm:ss.SSS").format(new Date()));
        System.out.println(calcTotal2());
        System.out.println(new SimpleDateFormat("mm:ss.SSS").format(new Date()));
        System.out.println(calcTotal3());
        System.out.println(new SimpleDateFormat("mm:ss.SSS").format(new Date()));
    }

    public long calcTotal1() {
        long conta = 0;
        for (String p: palavras) {
            if (p.length() > 12) conta++;
        }
        return conta;
    } 

    public long calcTotal2() {
        return palavras.stream().filter(p -> p.length() > 12).count();
    }   

    public long calcTotal3() {
        return palavras.parallelStream().filter(p -> p.length() > 12).count();
    }   
}
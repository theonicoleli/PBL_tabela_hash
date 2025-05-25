import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String filename = "female_names.txt";
        int capacity = 32;

        // 1. Ler nomes
        ArrayList<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    names.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        }

        // 2. Criar duas tabelas concretas
        ChainedHashTable table1 = new ChainedHashTable(capacity, new SimpleSumHash());
        ChainedHashTable table2 = new ChainedHashTable(capacity, new PolynomialHash());

        // 3. Inserção e medição de tempo
        long start1 = System.nanoTime();
        for (String name : names) table1.insert(name);
        long end1 = System.nanoTime();

        long start2 = System.nanoTime();
        for (String name : names) table2.insert(name);
        long end2 = System.nanoTime();

        // 4. Busca e medição de tempo (busca de todos os nomes)
        long searchStart1 = System.nanoTime();
        for (String name : names) table1.search(name);
        long searchEnd1 = System.nanoTime();

        long searchStart2 = System.nanoTime();
        for (String name : names) table2.search(name);
        long searchEnd2 = System.nanoTime();

        // 5. Relatório no console
        System.out.println("==== Relatório de Eficiência ====\n");

        System.out.println("Tabela 1 (SimpleSumHash):");
        System.out.printf(" - Colisões: %d%n", table1.getCollisionCount());
        System.out.printf(" - Tempo inserção: %.3f ms%n", (end1 - start1) / 1e6);
        System.out.printf(" - Tempo busca: %.3f ms%n", (searchEnd1 - searchStart1) / 1e6);
        System.out.println(" - Distribuição por bucket:");
        printDistribution(table1.getBucketSizes());

        System.out.println("\nTabela 2 (PolynomialHash):");
        System.out.printf(" - Colisões: %d%n", table2.getCollisionCount());
        System.out.printf(" - Tempo inserção: %.3f ms%n", (end2 - start2) / 1e6);
        System.out.printf(" - Tempo busca: %.3f ms%n", (searchEnd2 - searchStart2) / 1e6);
        System.out.println(" - Distribuição por bucket:");
        printDistribution(table2.getBucketSizes());
    }

    private static void printDistribution(int[] sizes) {
        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("   Bucket %2d: %d chaves%n", i, sizes[i]);
        }
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filename = "female_names.txt";
        int initialCapacity = 32;

        List<String> names = new ArrayList<>();
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

        ChainedHashTable table1 = new ChainedHashTable(initialCapacity, new SimpleSumHash());
        ChainedHashTable table2 = new ChainedHashTable(initialCapacity, new PolynomialHash());

        long start1 = System.nanoTime();
        names.forEach(name -> table1.insert(name));
        long end1 = System.nanoTime();

        long start2 = System.nanoTime();
        names.forEach(name -> table2.insert(name));
        long end2 = System.nanoTime();

        long searchStart1 = System.nanoTime();
        names.forEach(name -> table1.search(name));
        long searchEnd1 = System.nanoTime();

        long searchStart2 = System.nanoTime();
        names.forEach(name -> table2.search(name));
        long searchEnd2 = System.nanoTime();

        System.out.println("==== Relatório de Eficiência ====");
        System.out.println();

        // Tabela 1
        System.out.println("Tabela 1 (SimpleSumHash):");
        System.out.printf(" - Colisões: %d%n", table1.getCollisionCount());
        System.out.printf(" - Tempo inserção: %.3f ms%n", (end1 - start1) / 1e6);
        System.out.printf(" - Tempo busca: %.3f ms%n", (searchEnd1 - searchStart1) / 1e6);
        System.out.println(" - Sumário da distribuição:");
        printDistributionSummary(table1.getBucketSizes());
        System.out.println(" - Distribuição por bucket:");
        printDistribution(table1.getBucketSizes());

        // Tabela 2
        System.out.println();
        System.out.println("Tabela 2 (PolynomialHash):");
        System.out.printf(" - Colisões: %d%n", table2.getCollisionCount());
        System.out.printf(" - Tempo inserção: %.3f ms%n", (end2 - start2) / 1e6);
        System.out.printf(" - Tempo busca: %.3f ms%n", (searchEnd2 - searchStart2) / 1e6);
        System.out.println(" - Sumário da distribuição:");
        printDistributionSummary(table2.getBucketSizes());
        System.out.println(" - Distribuição por bucket:");
        printDistribution(table2.getBucketSizes());
    }

    private static void printDistribution(int[] sizes) {
        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("   Bucket %2d: %d chaves%n", i, sizes[i]);
        }
    }

    private static void printDistributionSummary(int[] sizes) {
        if (sizes.length == 0) {
            System.out.println("   Nenhum bucket.");
            return;
        }

        int totalKeys = 0;
        int minKeys = Integer.MAX_VALUE;
        int maxKeys = Integer.MIN_VALUE;
        int emptyBuckets = 0;

        for (int sz : sizes) {
            totalKeys += sz;
            if (sz < minKeys) minKeys = sz;
            if (sz > maxKeys) maxKeys = sz;
            if (sz == 0) emptyBuckets++;
        }

        double average = (double) totalKeys / sizes.length;
        double sumSquares = 0;
        for (int sz : sizes) {
            sumSquares += (sz - average) * (sz - average);
        }
        double stdDev = Math.sqrt(sumSquares / sizes.length);

        System.out.printf("   Total de buckets: %d%n", sizes.length);
        System.out.printf("   Total de chaves inseridas: %d%n", totalKeys);
        System.out.printf("   Chaves por bucket (Média): %.2f%n", average);
        System.out.printf("   Chaves por bucket (Mínimo): %d%n", minKeys);
        System.out.printf("   Chaves por bucket (Máximo): %d%n", maxKeys);
        System.out.printf("   Buckets vazios: %d (%.2f%%)%n", emptyBuckets, (double) emptyBuckets / sizes.length * 100);
        System.out.printf("   Desvio Padrão: %.2f%n", stdDev);
        System.out.println("   (Menor desvio indica distribuição mais uniforme)");
    }
}

public class PolynomialHash implements HashFunction {
    @Override
    public int hash(String key, int tableSize) {
        long hash = 0;
        long p = 31;
        long power = 1;
        for (char c : key.toCharArray()) {
            hash = (hash + (c - 'a' + 1) * power) % tableSize;
            power = (power * p) % tableSize;
        }
        return (int)(Math.abs(hash) % tableSize);
    }
}

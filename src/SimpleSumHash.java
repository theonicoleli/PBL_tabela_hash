public class SimpleSumHash implements HashFunction {
    @Override
    public int hash(String key, int tableSize) {
        int sum = 0;
        for (char c : key.toCharArray()) {
            sum += c;
        }
        return Math.abs(sum) % tableSize;
    }
}

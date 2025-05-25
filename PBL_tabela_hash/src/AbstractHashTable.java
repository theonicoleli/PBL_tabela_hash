import java.util.LinkedList;
import java.util.List;

public abstract class AbstractHashTable {
    protected List<String>[] buckets;
    protected HashFunction hashFunction;
    protected int collisions = 0;

    @SuppressWarnings("unchecked")
    public AbstractHashTable(int capacity, HashFunction hf) {
        this.buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
        this.hashFunction = hf;
    }

    public void insert(String key) {
        int idx = hashFunction.hash(key, buckets.length);
        List<String> bucket = buckets[idx];
        if (!bucket.isEmpty()) {
            collisions++;
        }
        bucket.add(key);
    }

    public boolean search(String key) {
        int idx = hashFunction.hash(key, buckets.length);
        return buckets[idx].contains(key);
    }

    public int getCollisionCount() {
        return collisions;
    }

    public int[] getBucketSizes() {
        int[] sizes = new int[buckets.length];
        for (int i = 0; i < buckets.length; i++) {
            sizes[i] = buckets[i].size();
        }
        return sizes;
    }
}

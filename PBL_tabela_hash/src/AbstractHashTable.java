public abstract class AbstractHashTable {
    protected List<String>[] buckets;
    protected HashFunction hashFunction;
    protected int collisions = 0;
    protected int size = 0;
    protected static final double MAX_LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public AbstractHashTable(int capacity, HashFunction hf) {
        this.buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
        this.hashFunction = hf;
    }

    public void insert(String key) {
        if ((double)(size + 1) / buckets.length > MAX_LOAD_FACTOR) {
            resize();
        }

        int idx = hashFunction.hash(key, buckets.length);
        List<String> bucket = buckets[idx];
        if (!bucket.isEmpty()) {
            collisions++;
        }
        bucket.add(key);
        size++;
    }

    public boolean remove(String key) {
        int idx = hashFunction.hash(key, buckets.length);
        boolean removed = buckets[idx].remove(key);
        if (removed) size--;
        return removed;
    }

    public boolean search(String key) {
        int idx = hashFunction.hash(key, buckets.length);
        return buckets[idx].contains(key);
    }

    public ArrayList<String> getBiggestBucket() {
        int maxIndex = 0;
        int maxSize = 0;
        for (int i = 0; i < buckets.length; i++) {
            int s = buckets[i].size();
            if (s > maxSize) {
                maxSize = s;
                maxIndex = i;
            }
        }
        ArrayList<String> names = new ArrayList<>();
        buckets[maxIndex].forEach(name -> names.add(name));
        return names;
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

    public double getLoadFactor() {
        return (double) size / buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = buckets.length * 2;
        List<String>[] newBuckets = new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new LinkedList<>();
        }
        for (List<String> bucket : buckets) {
<<<<<<< HEAD
            bucket.forEach(key -> {
=======
            for (String key : bucket) {
>>>>>>> e430f58e1ce62d5c29f0a60b4a09c42b112a286c
                int newIndex = hashFunction.hash(key, (int) newCapacity);
                newBuckets[newIndex].add(key);
            });
        }

        this.buckets = newBuckets;
    }
}
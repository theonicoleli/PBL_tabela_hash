public interface List<T> {
    void add(T element);
    boolean remove(T element);
    boolean contains(T element);
    int size();
    boolean isEmpty();
    void forEach(Consumer<T> action);
}
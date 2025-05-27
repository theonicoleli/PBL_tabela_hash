public class ArrayList<T> implements List<T> {
    private static final int INITIAL_CAPACITY = 8;
    private Object[] elements;
    private int size;

    public ArrayList() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                int numMoved = size - i - 1;
                if (numMoved > 0) {
                    System.arraycopy(elements, i + 1, elements, i, numMoved);
                }
                elements[--size] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void forEach(Consumer<T> action) {
        for (int i = 0; i < size; i++) {
            action.accept((T) elements[i]);
        }
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length * 2;
            Object[] newArr = new Object[newCapacity];
            System.arraycopy(elements, 0, newArr, 0, size);
            elements = newArr;
        }
    }
}

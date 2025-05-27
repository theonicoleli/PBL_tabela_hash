public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value) { this.value = value; }
    }

    public LinkedList() {
        head = tail = null;
        size = 0;
    }

    @Override
    public void add(T element) {
        Node<T> node = new Node<>(element);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public boolean remove(T element) {
        Node<T> prev = null;
        Node<T> curr = head;
        while (curr != null) {
            if (curr.value.equals(element)) {
                if (prev == null) {
                    head = curr.next;
                } else {
                    prev.next = curr.next;
                }
                if (curr == tail) {
                    tail = prev;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    @Override
    public boolean contains(T element) {
        Node<T> curr = head;
        while (curr != null) {
            if (curr.value.equals(element)) {
                return true;
            }
            curr = curr.next;
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

    @Override
    public void forEach(Consumer<T> action) {
        Node<T> curr = head;
        while (curr != null) {
            action.accept(curr.value);
            curr = curr.next;
        }
    }
}

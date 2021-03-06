package deque;

import java.util.Iterator;

public class LinkedListDeque<T> {
    public class Node {
        private Node prev;
        public T value;
        public Node next;
        public Node(Node p, T v, Node n){
            prev = p;
            value = v;
            next = n;
        }
    }
    private Node sentF;
    private Node sentB;
    private int size;

    public LinkedListDeque() {
        sentF = new Node(null, null, null);
        sentB = new Node(sentF, null, null);
        sentF.next = sentB;
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque other) {
        sentF = new Node(null, null, null);
        sentB = new Node(sentF, null, null);
        sentF.next = sentB;
        size = other.size;

        Node p = other.sentF.next;
        for (int i = 0; i < size; i++) {
            Node prev = sentB.prev;
            sentB.prev = new Node(prev, p.value, sentB);
            prev.next = sentB.prev;
            p = p.next;

            //the other approach without the p => casting with (T)
            //addLast((T)other.get(i));
        }
    }

    public void addFirst(T item) {
        Node next = sentF.next;
        sentF.next = new Node(sentF, item, next);
        next.prev = sentF.next;
        size++;
    }

    public void addLast(T item) {
        Node prev = sentB.prev;
        sentB.prev = new Node(prev, item, sentB);
        prev.next = sentB.prev;
        size++;
    }

    public boolean isEmpty(){
        return sentF.next == sentB;
    }

    public int size(){
        return size;
    }

    public void printDeque() {
        Node cur = sentF.next;
        while (cur != sentB) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node item = sentF.next;
        sentF.next = item.next;
        item.next.prev = sentF;
        size--;
        return item.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node item = sentB.prev;
        sentB.prev = item.prev;
        item.prev.next = sentB;
        size--;
        return item.value;
    }

    public T get(int index) {
        if (index > size-1) {
            return null;
        }
        Node cur = sentF.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.value;
    }

    public T getRecursive(int index) {
        if (index < 0 || index > size-1) {
            return null;
        }
        LinkedListDeque copy = new LinkedListDeque(this);
        return (T) copy.getRecursiveHelper(index);
    }

    private T getRecursiveHelper(int index) {
        if (index == 0) {
            return sentF.next.value;
        } else {
            removeFirst();
            return getRecursiveHelper(index - 1);
        }
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node p;

        LinkedListDequeIterator() {
            p = sentF.next;
        }

        public boolean hasNext() {
            return p == sentF;
        }

        public T next() {
            T item = p.value;
            p = p.next;
            return item;
        }
    }
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }
        LinkedListDeque<?> lld = (LinkedListDeque<?>) o;
        if (lld.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (lld.get(i) != get(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        LinkedListDeque LLD = new LinkedListDeque();
        var nf = LLD.removeFirst();
        var nl = LLD.removeLast();
        System.out.println(LLD.isEmpty());
        System.out.println(LLD.size());
        LLD.addFirst(1);
        LLD.printDeque();
        LLD.addLast(2);
        LLD.printDeque();
        LLD.addLast(3);
        LLD.printDeque();
        LLD.addFirst(0);
        LLD.printDeque();
        var zero = LLD.get(0);
        var two = LLD.get(2);
        var three = LLD.get(3);

        System.out.println(LLD.isEmpty());
        System.out.println(LLD.size());

    }
}

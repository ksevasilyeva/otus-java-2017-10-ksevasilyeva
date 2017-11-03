package ru.otus.java.hw3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements List<T> {

    private static final int INIT_CAPACITY = 10;

    private int size;

    private T[] elementArray;

    public MyArrayList() {
        this.elementArray = (T[]) new Object[INIT_CAPACITY];
    }

    public MyArrayList(int capacity) {
        this.elementArray = (T[]) new Object[capacity];
    }

    public MyArrayList(T[] elements) {
        this.elementArray = elements;
        this.size = elementArray.length;
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
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayIterator(elementArray);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementArray, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new RuntimeException();
    }

    @Override
    public boolean add(T t) {
        ensureCapacity(size() + 1);
        elementArray[size++] = t;
        return true;
    }

    @Override
    public void add(int index, T element) {
        ensureCapacity(size() + 1);  // Increments modCount!!
        System.arraycopy(elementArray, index, elementArray, index + 1, size - index);
        elementArray[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (this.size > 0) {
            return addAll(this.size, c);
        }
        return addAll(0, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        ensureCapacity(size() + c.size());

        T[] tempArray =  (T[]) new Object[size + c.size()];

        System.arraycopy(elementArray, 0, tempArray, 0, index); // copying the left part of existing array
        System.arraycopy(c.toArray(), 0, tempArray, index, c.size()); //copying the c part to temp array
        System.arraycopy(elementArray, index, tempArray, (index + c.size()), size - index); //copying the right part

        elementArray = tempArray;
        size = elementArray.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException();
    }

    @Override
    public void clear() {
        throw new RuntimeException();
    }

    @Override
    public T get(int index) {
        return (T) elementArray[index];
    }

    @Override
    public T set(int index, T element) {
        T oldVal = (T) elementArray[index];
        elementArray[index] = element;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        throw new RuntimeException();
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elementArray[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new RuntimeException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyArrayIterator(elementArray);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new MyArrayIterator(elementArray, index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new RuntimeException();
    }

    private void ensureCapacity(int targetSize) {
        if (targetSize - elementArray.length > 0) {
            elementArray = Arrays.copyOf(elementArray, targetSize);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(elementArray);
    }

    private class MyArrayIterator implements Iterator<T>, ListIterator<T> {

        int current = -1;  // the current element we are looking at
        List<T> list;

        MyArrayIterator(T[] list) {
            this.list = Arrays.asList(list);
        }

        MyArrayIterator(T[] list, int index) {
            this.list = Arrays.asList(list);
            this.current = index;
        }

        @Override
        public boolean hasNext() {
            return  current < list.size() - 1;
        }

        @Override
        public T next() {
            if (hasNext()) {
                current++;
                return list.get(current);
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            return current > 0 && list.size() > 0;
        }

        @Override
        public T previous() {
            if (hasPrevious()) {
                current--;
                return list.get(current);
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            if (hasNext()) {
                return (current + 1);
            }
            else return -1;
        }

        @Override
        public int previousIndex() {
            if (hasPrevious()) {
                return (current - 1);
            }
            else return -1;
        }

        @Override
        public void remove() {
            list.remove(current);
        }

        @Override
        public void set(T t) {
            list.set(current, t);
        }

        @Override
        public void add(T t) {
            list.add(t);
        }
    }
}

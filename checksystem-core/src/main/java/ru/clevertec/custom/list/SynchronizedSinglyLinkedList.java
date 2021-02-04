package ru.clevertec.custom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntFunction;

public class SynchronizedSinglyLinkedList<T> implements List<T> {

    private final ReentrantLock mutex = new ReentrantLock();
    private final SinglyLinkedList<T> singlyLinkedList = new SinglyLinkedList<>();

    public SynchronizedSinglyLinkedList() {
    }

    public SynchronizedSinglyLinkedList(Collection<? extends T> collection) {
        addAll(collection);
    }

    @Override
    public boolean add(T value) {
        mutex.lock();
        try {
            return singlyLinkedList.add(value);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void add(int index, T value) {
        mutex.lock();
        singlyLinkedList.add(index, value);
        mutex.unlock();
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        mutex.lock();
        try {
            return singlyLinkedList.addAll(collection);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        mutex.lock();
        try {
            return singlyLinkedList.addAll(index, collection);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public T remove(int index) {
        mutex.lock();
        try {
            return singlyLinkedList.remove(index);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        mutex.lock();
        try {
            return singlyLinkedList.remove(o);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        mutex.lock();
        try {
            return singlyLinkedList.removeAll(collection);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public T set(int index, T value) {
        mutex.lock();
        try {
            return singlyLinkedList.set(index, value);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public boolean contains(Object o) {
        mutex.lock();
        try {
            return singlyLinkedList.contains(o);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        mutex.lock();
        try {
            return singlyLinkedList.containsAll(collection);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public T get(int index) {
        mutex.lock();
        try {
            return singlyLinkedList.get(index);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        mutex.lock();
        try {
            return singlyLinkedList.retainAll(collection);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void clear() {
        mutex.lock();
        singlyLinkedList.clear();
        mutex.unlock();
    }

    @Override
    public int size() {
        mutex.lock();
        try {
            return singlyLinkedList.size();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        mutex.lock();
        try {
            return singlyLinkedList.isEmpty();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public int indexOf(Object o) {
        mutex.lock();
        try {
            return singlyLinkedList.indexOf(o);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        mutex.lock();
        try {
            return singlyLinkedList.lastIndexOf(o);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        mutex.lock();
        try {
            return singlyLinkedList.subList(fromIndex, toIndex);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public Object[] toArray() {
        mutex.lock();
        try {
            return singlyLinkedList.toArray();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public <T1> T1[] toArray(T1[] array) {
        mutex.lock();
        try {
            return singlyLinkedList.toArray(array);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        mutex.lock();
        try {
            return singlyLinkedList.toArray(generator);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public Iterator<T> iterator() {
        mutex.lock();
        try {
            return singlyLinkedList.iterator();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        mutex.lock();
        try {
            return singlyLinkedList.listIterator();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        mutex.lock();
        try {
            return singlyLinkedList.listIterator(index);
        } finally {
            mutex.unlock();
        }
    }
}

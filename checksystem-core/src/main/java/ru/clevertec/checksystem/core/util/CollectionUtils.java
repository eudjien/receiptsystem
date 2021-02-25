package ru.clevertec.checksystem.core.util;

import ru.clevertec.custom.list.SinglyLinkedList;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class CollectionUtils {

    public static <T> void putAll(List<T> destination, Collection<T> sources, Comparator<? super T> comparator) {
        sources.forEach(source -> put(destination, source, comparator));
    }

    public static <T> void putAll(List<T> destination, T[] sources, Comparator<? super T> comparator) {
        for (var source : sources)
            put(destination, source, comparator);
    }

    public static <T> void put(List<T> destination, T source, Comparator<? super T> comparator) {
        var index = Collections.binarySearch(destination, source, comparator);
        if (index > -1)
            destination.set(index, source);
        else
            destination.add(source);
    }

    public static <T> Collection<T> asCollection(Iterable<T> iterable) {
        if (iterable instanceof Collection)
            return (Collection<T>) iterable;
        var list = new SinglyLinkedList<T>();
        iterable.forEach(list::add);
        return list;
    }

    public static <T> List<T> asList(Iterable<T> iterable) {
        var list = new SinglyLinkedList<T>();
        iterable.forEach(list::add);
        return list;
    }

    public static <T> boolean isNullOrEmpty(Iterable<T> iterable) {
        return iterable == null || !iterable.iterator().hasNext();
    }
}

package ru.clevertec.checksystem.core.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class CollectionUtils {

    public static <T> void putAll(List<T> destination, Collection<T> sources, Comparator<? super T> comparator) {
        for (var source : sources) {
            put(destination, source, comparator);
        }
    }

    public static <T> void putAll(List<T> destination, T[] sources, Comparator<? super T> comparator) {
        for (var source : sources) {
            put(destination, source, comparator);
        }
    }

    public static <T> void put(List<T> destination, T source, Comparator<? super T> comparator) {

        var index = Collections.binarySearch(destination, source, comparator);

        if (index > -1) {
            destination.set(index, source);
        } else {
            destination.add(source);
        }
    }
}

package ru.clevertec.checksystem.core.event;

import ru.clevertec.checksystem.core.common.event.IEventEmitter;
import ru.clevertec.checksystem.core.common.event.IEventListener;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EventEmitter<T> implements IEventEmitter<T> {

    private final Map<String, List<IEventListener<T>>> listeners = new HashMap<>();

    @Override
    public void emit(String eventType, T value) {
        if (listeners.containsKey(eventType)) {
            var eventListeners = listeners.get(eventType);
            for (var listener : eventListeners) {
                listener.next(value);
            }
        }
    }

    @Override
    public Subscription<T> subscribe(String eventType, IEventListener<T> eventListener) {
        putListener(getOrCreateEventListenerList(eventType), eventListener);
        return new Subscription<>(listeners, eventType, eventListener);
    }

    @Override
    public void unsubscribe() {
        listeners.clear();
    }

    private void putListener(List<IEventListener<T>> eventListenerList, IEventListener<T> eventListener) {

        var index = eventListenerList.indexOf(eventListener);

        if (index > -1) {
            eventListenerList.set(index, eventListener);
        } else {
            eventListenerList.add(eventListener);
        }
    }

    private List<IEventListener<T>> getOrCreateEventListenerList(String eventType) {

        List<IEventListener<T>> observerList;

        if (listeners.containsKey(eventType)) {
            observerList = listeners.get(eventType);
        } else {
            observerList = new SinglyLinkedList<>();
            listeners.put(eventType, observerList);
        }

        return observerList;
    }
}

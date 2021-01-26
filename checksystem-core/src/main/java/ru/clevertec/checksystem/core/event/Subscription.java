package ru.clevertec.checksystem.core.event;

import ru.clevertec.checksystem.core.common.IUnsubscribable;
import ru.clevertec.checksystem.core.common.event.IEventListener;

import java.util.List;
import java.util.Map;

public class Subscription<T> implements IUnsubscribable {

    private final Map<String, List<IEventListener<T>>> listeners;
    private final String eventType;
    private final IEventListener<T> eventListener;

    public Subscription(Map<String, List<IEventListener<T>>> listeners,
                        String eventType, IEventListener<T> eventListener) {
        this.listeners = listeners;
        this.eventType = eventType;
        this.eventListener = eventListener;
    }

    @Override
    public void unsubscribe() {
        if (listeners.containsKey(eventType)) {
            var eventListeners = listeners.get(eventType);
            eventListeners.remove(eventListener);
        }
    }
}

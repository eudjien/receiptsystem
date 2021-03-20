package ru.clevertec.checksystem.core.event;

import java.util.List;
import java.util.Map;

public class Subscription<T> implements ISubscription {

    private final Map<String, List<IEventListener<T>>> listeners;
    private final String eventType;
    private final IEventListener<T> eventListener;

    private boolean unsubscribed;

    public Subscription(Map<String, List<IEventListener<T>>> listeners,
                        String eventType, IEventListener<T> eventListener) {
        this.listeners = listeners;
        this.eventType = eventType;
        this.eventListener = eventListener;
    }

    @Override
    public void unsubscribe() {
        if (!unsubscribed) {
            if (listeners.containsKey(eventType)) {
                var eventListeners = listeners.get(eventType);
                eventListeners.remove(eventListener);
            }
            unsubscribed = true;
        }
    }

    @Override
    public void close() {
        if (!unsubscribed) {
            unsubscribe();
        }
    }
}

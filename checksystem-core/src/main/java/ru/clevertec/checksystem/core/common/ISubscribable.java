package ru.clevertec.checksystem.core.common;

import ru.clevertec.checksystem.core.event.IEventListener;
import ru.clevertec.checksystem.core.event.Subscription;

public interface ISubscribable<T> {
    Subscription<T> subscribe(String eventType, IEventListener<T> eventListener);
}

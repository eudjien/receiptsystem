package ru.clevertec.checksystem.core.event;

import org.springframework.context.ApplicationContext;
import ru.clevertec.checksystem.core.common.IEmittable;
import ru.clevertec.checksystem.core.common.ISubscribable;
import ru.clevertec.checksystem.core.common.IUnsubscribable;

public interface IEventEmitter<T> extends IEmittable<T>, ISubscribable<T>, IUnsubscribable {
    ApplicationContext getApplicationContext();
}

package ru.clevertec.checksystem.core.common.event;

import ru.clevertec.checksystem.core.common.IEmittable;
import ru.clevertec.checksystem.core.common.ISubscribable;
import ru.clevertec.checksystem.core.common.IUnsubscribable;

public interface IEventEmitter<T> extends IEmittable<T>, ISubscribable<T>, IUnsubscribable {
}

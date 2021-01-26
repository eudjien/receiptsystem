package ru.clevertec.checksystem.core.annotation.subscribe;

import ru.clevertec.checksystem.core.common.event.IEventListener;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Repeatable(Subscribes.class)
public @interface Subscribe {

    String eventType();

    Class<? extends IEventListener<?>> listenerClass();
}

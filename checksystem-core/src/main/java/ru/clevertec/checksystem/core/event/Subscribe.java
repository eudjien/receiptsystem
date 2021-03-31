package ru.clevertec.checksystem.core.event;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Subscribes.class)
public @interface Subscribe {

    String eventType();

    Class<? extends IEventListener<?>> listenerClass();
}

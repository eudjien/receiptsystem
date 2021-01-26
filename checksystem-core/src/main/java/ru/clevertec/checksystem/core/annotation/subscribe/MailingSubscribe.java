package ru.clevertec.checksystem.core.annotation.subscribe;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Repeatable(MailingSubscribes.class)
public @interface MailingSubscribe {

    String eventType();
}

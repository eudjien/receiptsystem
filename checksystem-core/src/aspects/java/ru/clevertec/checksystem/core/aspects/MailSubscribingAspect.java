package ru.clevertec.checksystem.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.checksystem.core.annotation.subscribe.MailingSubscribe;
import ru.clevertec.checksystem.core.common.event.IEventEmitter;
import ru.clevertec.checksystem.core.common.event.IEventListener;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.MailSenderService;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings({"EmptyMethod", "unchecked"})
@Aspect
public class MailSubscribingAspect {

    @Before(
            value = "withMailSubscribeAnnotation() && inProject() && onConstructorInitialization()")
    public void beforeConstruct(JoinPoint jp)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {

        if (jp.getTarget() instanceof IEventEmitter<?>) {

            var targetObject = (IEventEmitter<Object>) jp.getTarget();

            var mailSubscribeAnnotations =
                    targetObject.getClass().getDeclaredAnnotationsByType(MailingSubscribe.class);

            var mailService = (IEventListener<Object>) ServiceFactory.instance(MailSenderService.class);

            for (var mailSubscribeAnnotation : mailSubscribeAnnotations) {
                targetObject.subscribe(mailSubscribeAnnotation.eventType(), mailService);
            }
        }
    }

    @Pointcut("execution(public *.new(..))")
    private void onConstructorInitialization() {
    }

    @Pointcut("within(ru.clevertec.checksystem..*)")
    private void inProject() {
    }

    @Pointcut("(@within(ru.clevertec.checksystem.core.annotation.subscribe.MailingSubscribe) " +
            "|| @within(ru.clevertec.checksystem.core.annotation.subscribe.MailingSubscribes))")
    private void withMailSubscribeAnnotation() {
    }
}

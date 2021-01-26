package ru.clevertec.checksystem.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.checksystem.core.annotation.subscribe.Subscribe;
import ru.clevertec.checksystem.core.common.event.IEventEmitter;
import ru.clevertec.checksystem.core.common.event.IEventListener;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("EmptyMethod")
@Aspect
public class SubscribingAspect {

    @Before(
            value = "withMailSubscribeAnnotation() && inProject() && onConstructorInitialization()")
    public void beforeConstruct(JoinPoint jp)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {

        if (jp.getTarget() instanceof IEventEmitter<?>) {

            var targetObject = (IEventEmitter<?>) jp.getTarget();

            var mailSubscribeAnnotations =
                    targetObject.getClass().getDeclaredAnnotationsByType(Subscribe.class);

            for (var mailSubscribeAnnotation : mailSubscribeAnnotations) {

                var eventListener = mailSubscribeAnnotation.listenerClass()
                        .getDeclaredConstructor().newInstance();

                var method = targetObject.getClass().getMethod("subscribe",
                        String.class, IEventListener.class);

                method.invoke(targetObject, mailSubscribeAnnotation.eventType(), eventListener);
            }
        }
    }

    @Pointcut("execution(public *.new(..))")
    private void onConstructorInitialization() {
    }

    @Pointcut("within(ru.clevertec.checksystem..*)")
    private void inProject() {
    }

    @Pointcut("(@within(ru.clevertec.checksystem.core.annotation.subscribe.Subscribe) " +
            "|| @within(ru.clevertec.checksystem.core.annotation.subscribe.Subscribes))")
    private void withMailSubscribeAnnotation() {
    }
}

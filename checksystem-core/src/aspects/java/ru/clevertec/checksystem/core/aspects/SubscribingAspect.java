package ru.clevertec.checksystem.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.annotation.subscribe.Subscribe;
import ru.clevertec.checksystem.core.common.event.IEventEmitter;
import ru.clevertec.checksystem.core.common.event.IEventListener;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("EmptyMethod")
@Aspect
@Component
public class SubscribingAspect {

    @Before(
            value = "withSubscribeAnnotation() && inProject() && onEventEmitterConstructorExecution()")
    public void beforeConstruct(JoinPoint jp) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {

        var eventEmitter = (IEventEmitter<?>) jp.getTarget();

        var subscribeAnnotations = eventEmitter.getClass()
                .getDeclaredAnnotationsByType(Subscribe.class);

        for (var subscribeAnnotation : subscribeAnnotations) {

            var eventListener = subscribeAnnotation.listenerClass()
                    .getDeclaredConstructor().newInstance();

            var method = eventEmitter.getClass().getMethod("subscribe",
                    String.class, IEventListener.class);

            method.invoke(eventEmitter, subscribeAnnotation.eventType(), eventListener);
        }
    }

    @Pointcut("execution(public ru.clevertec.checksystem.core.common.event.IEventEmitter+.new(..))")
    private void onEventEmitterConstructorExecution() {
    }

    @Pointcut("within(ru.clevertec.checksystem..*)")
    private void inProject() {
    }

    @Pointcut("(@within(ru.clevertec.checksystem.core.annotation.subscribe.Subscribe) " +
            "|| @within(ru.clevertec.checksystem.core.annotation.subscribe.Subscribes))")
    private void withSubscribeAnnotation() {
    }
}

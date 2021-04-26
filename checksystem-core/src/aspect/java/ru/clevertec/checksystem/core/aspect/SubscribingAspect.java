package ru.clevertec.checksystem.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.checksystem.core.event.IEventEmitter;
import ru.clevertec.checksystem.core.event.IEventListener;
import ru.clevertec.checksystem.core.event.Subscribe;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("EmptyMethod")
@Aspect
public class SubscribingAspect {

    @After(
            value = "withinProject() && withSubscribeAnnotation() && onEventEmitterConstructorExecution()")
    public void afterConstructorExecution(JoinPoint jp)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        var eventEmitter = (IEventEmitter<?>) jp.getTarget();
        var subscribeAnnotations = eventEmitter.getClass().getDeclaredAnnotationsByType(Subscribe.class);
        var applicationContext = eventEmitter.getApplicationContext();

        for (var subscribeAnnotation : subscribeAnnotations) {

            var eventListener = applicationContext.getBean(subscribeAnnotation.listenerClass());

            var method = eventEmitter.getClass().getMethod("subscribe",
                    String.class, IEventListener.class);

            method.invoke(eventEmitter, subscribeAnnotation.eventType(), eventListener);
        }
    }

    @Pointcut("within(ru.clevertec.checksystem..*)")
    private void withinProject() {
    }

    @Pointcut("execution(public ru.clevertec.checksystem.core.event.IEventEmitter+.new(..))")
    private void onEventEmitterConstructorExecution() {
    }

    @Pointcut("(@within(ru.clevertec.checksystem.core.event.Subscribe) " +
            "|| @within(ru.clevertec.checksystem.core.event.Subscribes))")
    private void withSubscribeAnnotation() {
    }
}

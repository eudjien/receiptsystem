package ru.clevertec.checksystem.core.factory;

import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.methodlogger.IMethodLogger;
import ru.clevertec.checksystem.core.log.methodlogger.MethodLogger;
import ru.clevertec.checksystem.core.service.CheckService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class ServiceFactory {

    private static final HashMap<Class<?>, Object> services = new HashMap<>();

    public static <E> E instance(Class<?> serviceClass)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance(serviceClass, false);
    }

    @SuppressWarnings("unchecked")
    public static <E> E instance(Class<?> serviceClass, boolean isProxied)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if (!serviceClass.isAnnotationPresent(CheckService.class)) {
            throw new IllegalArgumentException("serviceClass is not a Service class");
        }

        if (services.containsKey(serviceClass)) {
            return (E) services.get(serviceClass);
        }

        var serviceInstance = isProxied
                ? (E) createProxiedService(serviceClass)
                : (E) serviceClass.getDeclaredConstructor().newInstance();

        services.put(serviceClass, serviceInstance);

        return serviceInstance;
    }

    private static Object createProxiedService(Class<?> serviceClass)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return Proxy.newProxyInstance(serviceClass.getClassLoader(),
                serviceClass.getInterfaces(),
                new ServiceInvocationHandler(serviceClass.getDeclaredConstructor().newInstance()));
    }

    private static class ServiceInvocationHandler implements InvocationHandler {

        private final Object service;
        private static IMethodLogger serviceLogger;

        public ServiceInvocationHandler(Object service) {
            serviceLogger = MethodLogger.instance(service.getClass());
            this.service = service;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws InvocationTargetException, IllegalAccessException {

            serviceLogger.log(LogLevel.INFO, "%rt %mn(%at) - (ARGS: %ad)", method, args);

            var result = method.invoke(service, args);

            serviceLogger.log(LogLevel.INFO, "%rt %mn(%at) - (RETURN: %rd)", method, null, result);

            return result;
        }
    }
}

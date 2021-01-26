package ru.clevertec.checksystem.core.factory.service;

import ru.clevertec.checksystem.core.common.service.IService;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.methodlogger.IMethodLogger;
import ru.clevertec.checksystem.core.log.methodlogger.MethodLogger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public abstract class ProxiedServiceFactory {

    private static final Map<Class<?>, Object> services = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <E extends IService> E instance(Class<? extends IService> serviceClass)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {

        if (services.containsKey(serviceClass)) {
            return (E) services.get(serviceClass);
        }

        var newService = (E) createProxiedService(serviceClass);
        services.put(serviceClass, newService);

        return newService;
    }

    private static Object createProxiedService(Class<? extends IService> serviceClass)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
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

            var invokeResult = method.invoke(service, args);

            serviceLogger.log(LogLevel.INFO, "%rt %mn(%at) - (RETURN: %rd)", method, null, invokeResult);

            return invokeResult;
        }
    }
}

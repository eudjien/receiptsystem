package ru.clevertec.checksystem.core.factory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.log.IMethodLogger;
import ru.clevertec.checksystem.core.common.service.IService;
import ru.clevertec.checksystem.core.log.LogLevel;
import ru.clevertec.checksystem.core.log.methodlogger.MethodLogger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public final class ProxiedServiceFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public ProxiedServiceFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public <E extends IService> E instance(Class<? extends IService> serviceClass) {
        return (E) createProxiedService(serviceClass);
    }

    private Object createProxiedService(Class<? extends IService> serviceClass) {
        return Proxy.newProxyInstance(serviceClass.getClassLoader(),
                serviceClass.getInterfaces(),
                new ServiceInvocationHandler(applicationContext.getBean(serviceClass)));
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

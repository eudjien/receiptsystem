package ru.clevertec.checksystem.core.factory.service;

import ru.clevertec.checksystem.core.common.service.IService;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class SimpleServiceFactory {

    private static final Map<Class<?>, Object> services = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <E extends IService> E instance(Class<? extends IService> serviceClass)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        return (E) getOrCreate(serviceClass);
    }

    private static Object getOrCreate(Class<? extends IService> serviceClass)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {

        if (services.containsKey(serviceClass)) {
            return services.get(serviceClass);
        }

        var newService = serviceClass.getDeclaredConstructor().newInstance();
        services.put(serviceClass, newService);

        return newService;
    }
}

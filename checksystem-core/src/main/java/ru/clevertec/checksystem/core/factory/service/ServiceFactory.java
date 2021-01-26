package ru.clevertec.checksystem.core.factory.service;

import ru.clevertec.checksystem.core.ProxyIdentifier;
import ru.clevertec.checksystem.core.common.service.IService;

import java.lang.reflect.InvocationTargetException;

public class ServiceFactory {

    public static <E extends IService> E instance(Class<? extends IService> serviceClass)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {

        return ProxyIdentifier.isProxied()
                ? ProxiedServiceFactory.instance(serviceClass)
                : SimpleServiceFactory.instance(serviceClass);
    }
}

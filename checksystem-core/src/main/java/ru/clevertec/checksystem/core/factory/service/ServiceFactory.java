package ru.clevertec.checksystem.core.factory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.ProxyIdentifier;
import ru.clevertec.checksystem.core.common.service.IService;

@Component
public final class ServiceFactory {

    private final SimpleServiceFactory simpleServiceFactory;
    private final ProxiedServiceFactory proxiedServiceFactory;

    @Autowired
    public ServiceFactory(
            SimpleServiceFactory simpleServiceFactory,
            ProxiedServiceFactory proxiedServiceFactory) {
        this.simpleServiceFactory = simpleServiceFactory;
        this.proxiedServiceFactory = proxiedServiceFactory;
    }

    public <E extends IService> E instance(Class<? extends IService> serviceClass) {
        return ProxyIdentifier.isProxied()
                ? simpleServiceFactory.instance(serviceClass)
                : proxiedServiceFactory.instance(serviceClass);
    }
}

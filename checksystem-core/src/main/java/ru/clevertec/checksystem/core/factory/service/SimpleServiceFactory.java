package ru.clevertec.checksystem.core.factory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.service.IService;

@Component
public final class SimpleServiceFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private SimpleServiceFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public <E extends IService> E instance(Class<? extends IService> serviceClass) {
        return (E) this.applicationContext.getBean(serviceClass);
    }
}

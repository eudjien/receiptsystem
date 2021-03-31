package ru.clevertec.checksystem.core.configuration.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;

public abstract class ObjectMapperCondition implements Condition {

    private final static String PROPERTY_KEY = "application.object-mapper.hibernate-aware";

    protected boolean isHibernateAware(ConditionContext context) {
        return Boolean.parseBoolean(context.getEnvironment().getProperty(PROPERTY_KEY));
    }
}

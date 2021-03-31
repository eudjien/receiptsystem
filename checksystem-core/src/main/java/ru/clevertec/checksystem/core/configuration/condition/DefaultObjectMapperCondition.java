package ru.clevertec.checksystem.core.configuration.condition;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DefaultObjectMapperCondition extends ObjectMapperCondition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !isHibernateAware(context);
    }
}

package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.read.ICheckGenerateReader;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.io.read.JsonCheckGenerateReader;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import static ru.clevertec.checksystem.core.Constants.Format;

@Component
public final class CheckGenerateReaderFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private CheckGenerateReaderFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ICheckGenerateReader instance(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        //noinspection SwitchStatementWithTooFewBranches
        return switch (format) {
            case Format.JSON -> applicationContext.getBean(JsonCheckGenerateReader.class);
            default -> throw new ArgumentNotSupportedException("format");
        };
    }
}

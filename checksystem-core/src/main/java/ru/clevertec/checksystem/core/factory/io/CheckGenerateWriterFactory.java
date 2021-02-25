package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.common.io.write.ICheckGenerateWriter;
import ru.clevertec.checksystem.core.exception.ArgumentUnsupportedException;
import ru.clevertec.checksystem.core.io.write.JsonCheckGenerateWriter;
import ru.clevertec.checksystem.core.util.ThrowUtils;

@Component
public final class CheckGenerateWriterFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private CheckGenerateWriterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ICheckGenerateWriter create(String format) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        //noinspection SwitchStatementWithTooFewBranches
        return switch (format) {
            case Constants.Format.IO.JSON -> applicationContext.getBean(JsonCheckGenerateWriter.class);
            default -> throw new ArgumentUnsupportedException("format");
        };
    }
}

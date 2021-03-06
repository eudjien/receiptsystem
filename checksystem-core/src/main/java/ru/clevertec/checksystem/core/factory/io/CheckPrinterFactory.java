package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.io.print.CheckPrinter;
import ru.clevertec.checksystem.core.io.print.layout.HtmlCheckLayout;
import ru.clevertec.checksystem.core.io.print.layout.PdfCheckLayout;
import ru.clevertec.checksystem.core.io.print.layout.TextCheckLayout;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.util.Collection;

import static ru.clevertec.checksystem.core.Constants.Format;

@Component
public final class CheckPrinterFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private CheckPrinterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public CheckPrinter instance(String format) {
        return instance(format, null);
    }

    public CheckPrinter instance(String format, Collection<Check> checks) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        var checkPrinter = applicationContext.getBean(CheckPrinter.class);
        checkPrinter.setChecks(checks);

        switch (format) {
            case Format.TEXT -> checkPrinter.setLayout(new TextCheckLayout());
            case Format.PDF -> checkPrinter.setLayout(new PdfCheckLayout());
            case Format.HTML -> checkPrinter.setLayout(new HtmlCheckLayout());
            default -> throw new ArgumentNotSupportedException("format");
        }

        return checkPrinter;
    }
}

package ru.clevertec.checksystem.core.factory.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.Constants;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.exception.ArgumentUnsupportedException;
import ru.clevertec.checksystem.core.io.print.CheckPrinter;
import ru.clevertec.checksystem.core.io.print.layout.HtmlCheckLayout;
import ru.clevertec.checksystem.core.io.print.layout.PdfCheckLayout;
import ru.clevertec.checksystem.core.io.print.layout.TextCheckLayout;
import ru.clevertec.checksystem.core.util.ThrowUtils;

import java.util.Collection;

@Component
public final class CheckPrinterFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    private CheckPrinterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public CheckPrinter create(String format) {
        return create(format, null);
    }

    public CheckPrinter create(String format, Collection<Check> checks) {

        ThrowUtils.Argument.nullOrBlank("format", format);

        var checkPrinter = applicationContext.getBean(CheckPrinter.class);
        checkPrinter.setChecks(checks);

        switch (format) {
            case Constants.Format.Print.TEXT -> checkPrinter.setLayout(new TextCheckLayout());
            case Constants.Format.Print.PDF -> checkPrinter.setLayout(new PdfCheckLayout());
            case Constants.Format.Print.HTML -> checkPrinter.setLayout(new HtmlCheckLayout());
            default -> throw new ArgumentUnsupportedException("format");
        }

        return checkPrinter;
    }
}

package ru.clevertec.checksystem.core.io.print;

import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.check.ICheckAggregable;
import ru.clevertec.checksystem.core.common.io.print.ICheckLayout;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.util.FileUtils;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

@Component
public class CheckPrinter extends AbstractPrinter<Check> implements ICheckAggregable {

    private final Collection<Check> checks = new SinglyLinkedList<>();

    public CheckPrinter() {
    }

    public CheckPrinter(ICheckLayout checkLayout) {
        super(checkLayout);
    }

    public CheckPrinter(Collection<Check> checks, ICheckLayout checkLayout) {
        super(checkLayout);
        setChecks(checks);
    }

    @Override
    public Collection<Check> getChecks() {
        return checks;
    }

    @Override
    public void setChecks(Collection<Check> checks) {
        this.checks.clear();
        if (checks != null)
            this.checks.addAll(checks);
    }

    @Override
    public void addCheck(Check check) {
        checks.add(check);
    }

    @Override
    public void addChecks(Collection<Check> checks) {
        this.checks.addAll(checks);
    }

    @Override
    public void removeCheck(Check check) {
        this.checks.remove(check);
    }

    @Override
    public void removeChecks(Collection<Check> checks) {
        this.checks.removeAll(checks);
    }

    @Override
    public void clearChecks() {
        this.checks.clear();
    }

    @Override
    public byte[] print() throws IOException {
        return getLayout().getAllLayoutData(getChecks());
    }

    @Override
    public void print(OutputStream outputStream) throws IOException {
        outputStream.write(getLayout().getAllLayoutData(getChecks()));
    }

    @Override
    public void print(File destinationFile) throws IOException {
        FileUtils.writeBytesToFile(getLayout().getAllLayoutData(getChecks()), destinationFile);
    }
}

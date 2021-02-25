package ru.clevertec.checksystem.core.io.print;

import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.io.print.strategy.CheckPrintStrategy;
import ru.clevertec.checksystem.core.util.FileUtils;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public class CheckPrinter extends Printer<Check> {

    private final Collection<Check> checkCollection = new SinglyLinkedList<>();

    public CheckPrinter() {
    }

    public CheckPrinter(Collection<Check> checkCollection) {
        setChecks(checkCollection);
    }

    public CheckPrinter(CheckPrintStrategy strategy) {
        super(strategy);
    }

    public CheckPrinter(Collection<Check> checkCollection, CheckPrintStrategy strategy) {
        super(strategy);
        setChecks(checkCollection);
    }

    public Collection<Check> getChecks() {
        return checkCollection;
    }

    public void setChecks(Collection<Check> checkCollection) {
        this.checkCollection.clear();
        if (checkCollection != null) {
            this.checkCollection.addAll(checkCollection);
        }
    }

    @Override
    public Collection<PrintResult> print() throws IOException {

        var printedCheckList = new SinglyLinkedList<PrintResult>();

        for (var check : getChecks()) {
            printedCheckList.add(new PrintResult(check.getId(), getStrategy().getData(check)));
        }

        return printedCheckList;
    }

    public byte[] printRaw() throws IOException {
        return getStrategy().getCombinedData(getChecks());
    }

    @Override
    public void printRaw(OutputStream outputStream) throws IOException {
        outputStream.write(getStrategy().getCombinedData(getChecks()));
    }

    @Override
    public void printRaw(File destinationFile) throws IOException {
        FileUtils.writeBytesToFile(getStrategy().getCombinedData(getChecks()), destinationFile);
    }
}

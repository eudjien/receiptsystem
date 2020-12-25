package ru.clevertec.checksystem.core.io.printer;

import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.io.printer.strategy.CheckPrintStrategy;

import java.util.ArrayList;
import java.util.List;

public class CheckPrinter extends Printer<Check> {

    private final List<Check> checks = new ArrayList<>();

    public CheckPrinter() {
    }

    public CheckPrinter(List<Check> checks) {
        setChecks(checks);
    }

    public CheckPrinter(CheckPrintStrategy strategy) {
        super(strategy);
    }

    public CheckPrinter(List<Check> checks, CheckPrintStrategy strategy) {
        super(strategy);
        setChecks(checks);
    }

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks.clear();
        if (checks != null) {
            this.checks.addAll(checks);
        }
    }

    @Override
    public List<PrintResult> print() throws Exception {

        var printedCheckList = new ArrayList<PrintResult>();

        for (var check : getChecks()) {
            printedCheckList.add(new PrintResult(check.getId(), getStrategy().getData(check)));
        }

        return printedCheckList;
    }

    public byte[] printRaw() throws Exception {
        return getStrategy().getCombinedData(getChecks().toArray(Check[]::new));
    }
}

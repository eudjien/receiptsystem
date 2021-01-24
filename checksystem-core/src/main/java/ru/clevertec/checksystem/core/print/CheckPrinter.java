package ru.clevertec.checksystem.core.print;

import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.print.strategy.CheckPrintStrategy;
import ru.clevertec.normalino.list.NormalinoList;

import java.util.List;

public class CheckPrinter extends Printer<Check> {

    private final NormalinoList<Check> checks = new NormalinoList<>();

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

    public NormalinoList<Check> getChecks() {
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

        var printedCheckList = new NormalinoList<PrintResult>();

        for (var check : getChecks()) {
            printedCheckList.add(new PrintResult(check.getId(), getStrategy().getData(check)));
        }

        return printedCheckList;
    }

    public byte[] printRaw() throws Exception {
        return getStrategy().getCombinedData(getChecks().toArray(Check[]::new));
    }
}

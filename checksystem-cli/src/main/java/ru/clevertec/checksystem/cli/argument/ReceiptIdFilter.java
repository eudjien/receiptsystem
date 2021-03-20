package ru.clevertec.checksystem.cli.argument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReceiptIdFilter {

    private final ArgumentFinder finder;

    @Autowired
    public ReceiptIdFilter(ArgumentFinder finder) {
        this.finder = finder;
    }

    public Collection<Receipt> applyFilterIfExist(Collection<Receipt> receipts) {

        String value = finder.firstStringOrDefault(Constants.Keys.INPUT_FILTER_ID);

        if (value != null) {

            var ids = getIdentifiers();

            return receipts.stream()
                    .filter(receipt -> ids.contains(receipt.getId()))
                    .collect(Collectors.toList());
        }

        return receipts;
    }

    public List<Long> getIdentifiers() {
        String argumentValue = finder.firstStringOrDefault(Constants.Keys.INPUT_FILTER_ID);
        var receiptIds = new ArrayList<Long>();
        if (argumentValue != null) {
            var values = argumentValue.split(",");
            for (var s : values) {
                try {
                    receiptIds.add(Long.parseLong(s));
                } catch (Exception ignored) {
                }
            }
        }
        return receiptIds;
    }
}

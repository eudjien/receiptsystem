package ru.clevertec.checksystem.cli.task.input;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentFinder;
import ru.clevertec.checksystem.cli.argument.ReceiptIdFilter;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.io.format.StructureFormat;
import ru.clevertec.checksystem.core.service.IIoReceiptService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class DeserializeFromFile implements Callable<Void> {

    private final ArgumentFinder argumentFinder;
    private final ReceiptIdFilter receiptIdFilter;
    private final IIoReceiptService receiptService;
    private final Collection<Receipt> destinationReceipts;

    public DeserializeFromFile(
            ArgumentFinder argumentFinder,
            ReceiptIdFilter receiptIdFilter,
            IIoReceiptService receiptService,
            Collection<Receipt> destinationReceipts) {
        this.argumentFinder = argumentFinder;
        this.receiptIdFilter = receiptIdFilter;
        this.receiptService = receiptService;
        this.destinationReceipts = destinationReceipts;
    }

    @Override
    public Void call() throws Exception {
        deserializeFromFile(argumentFinder, receiptIdFilter, destinationReceipts);
        return null;
    }

    private void deserializeFromFile(
            ArgumentFinder finder,
            ReceiptIdFilter receiptIdFilter,
            Collection<Receipt> destinationReceipts) throws IOException {

        String format = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_FORMAT);
        String path = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_PATH);

        var receipts = receiptService.deserialize(new File(path), StructureFormat.parse(format));
        destinationReceipts.addAll(receiptIdFilter.applyFilterIfExist(receipts));
    }
}

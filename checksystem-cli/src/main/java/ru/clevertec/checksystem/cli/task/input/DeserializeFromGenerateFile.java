package ru.clevertec.checksystem.cli.task.input;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentFinder;
import ru.clevertec.checksystem.cli.argument.ReceiptIdFilter;
import ru.clevertec.checksystem.core.constant.Formats;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.io.format.GenerateFormat;
import ru.clevertec.checksystem.core.service.IIoReceiptService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

import static ru.clevertec.checksystem.cli.Constants.Keys;

public class DeserializeFromGenerateFile implements Callable<Void> {

    private final ArgumentFinder argumentFinder;
    private final ReceiptIdFilter receiptIdFilter;
    private final IIoReceiptService ioReceiptService;
    private final Collection<Receipt> destinationReceipts;

    public DeserializeFromGenerateFile(
            ArgumentFinder argumentFinder,
            ReceiptIdFilter receiptIdFilter,
            IIoReceiptService ioReceiptService,
            Collection<Receipt> destinationReceipts) {

        this.argumentFinder = argumentFinder;
        this.receiptIdFilter = receiptIdFilter;
        this.ioReceiptService = ioReceiptService;
        this.destinationReceipts = destinationReceipts;
    }

    @Override
    public Void call() throws Exception {
        deserializeFromGenerateFile(argumentFinder, receiptIdFilter, destinationReceipts);
        return null;
    }

    private void deserializeFromGenerateFile(
            ArgumentFinder finder,
            ReceiptIdFilter receiptIdFilter,
            Collection<Receipt> receipts) throws IOException {

        String format = finder.firstStringOrDefault(Keys.DESERIALIZE_GENERATE_FORMAT, Formats.JSON);
        String path = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_GENERATE_PATH);

        var receiptList = ioReceiptService.fromGenerate(new File(path), GenerateFormat.parse(format));
        receipts.addAll(receiptIdFilter.applyFilterIfExist(receiptList));
    }
}

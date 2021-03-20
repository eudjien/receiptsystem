package ru.clevertec.checksystem.cli.task.output;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentFinder;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.io.format.StructureFormat;
import ru.clevertec.checksystem.core.service.IIoReceiptService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class SerializeToFile implements Callable<Void> {

    private final ArgumentFinder argumentFinder;
    private final IIoReceiptService receiptService;
    private final Collection<Receipt> receipts;

    public SerializeToFile(
            ArgumentFinder argumentFinder, IIoReceiptService receiptService, Collection<Receipt> receipts) {
        this.argumentFinder = argumentFinder;
        this.receiptService = receiptService;
        this.receipts = receipts;
    }

    @Override
    public Void call() throws Exception {
        serializeToFile(argumentFinder, receipts);
        return null;
    }

    private void serializeToFile(ArgumentFinder finder, Collection<Receipt> receipts) throws IOException {

        var format = finder.firstStringOrThrow(Constants.Keys.SERIALIZE_FORMAT);
        var path = finder.firstStringOrThrow(Constants.Keys.SERIALIZE_PATH);

        receiptService.serialize(receipts, new File(path), StructureFormat.parse(format));
    }
}

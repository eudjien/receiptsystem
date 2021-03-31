package ru.clevertec.checksystem.cli.task.output;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentFinder;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.io.format.GenerateFormat;
import ru.clevertec.checksystem.core.service.common.IIoReceiptService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class SerializeToGenerateFile implements Callable<Void> {

    private final ArgumentFinder argumentFinder;
    private final IIoReceiptService ioReceiptService;
    private final Collection<Receipt> receipts;

    public SerializeToGenerateFile(
            ArgumentFinder argumentFinder,
            IIoReceiptService ioReceiptService,
            Collection<Receipt> receipts) {
        this.argumentFinder = argumentFinder;
        this.ioReceiptService = ioReceiptService;
        this.receipts = receipts;
    }

    @Override
    public Void call() throws Exception {
        serializeToFile(argumentFinder, receipts);
        return null;
    }

    private void serializeToFile(ArgumentFinder finder, Collection<Receipt> receipts) throws IOException {

        var format = finder.firstStringOrThrow(Constants.Keys.GENERATE_SERIALIZE_FORMAT);
        var path = finder.firstStringOrThrow(Constants.Keys.GENERATE_SERIALIZE_PATH);

        ioReceiptService.toGenerate(receipts, new File(path), GenerateFormat.parse(format));
    }
}

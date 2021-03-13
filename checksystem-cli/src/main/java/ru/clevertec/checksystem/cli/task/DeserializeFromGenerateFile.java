package ru.clevertec.checksystem.cli.task;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.ReceiptIdFilter;
import ru.clevertec.checksystem.cli.exception.ArgumentNotExistException;
import ru.clevertec.checksystem.core.common.service.IGenerateReceiptService;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.GenerateReceiptService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

import static ru.clevertec.checksystem.cli.Constants.Keys;
import static ru.clevertec.checksystem.core.Constants.Formats;

public class DeserializeFromGenerateFile implements Callable<Void> {

    private final ArgumentsFinder argumentsFinder;
    private final ReceiptIdFilter receiptIdFilter;
    private final ServiceFactory serviceFactory;
    private final Collection<Receipt> destinationReceipts;

    public DeserializeFromGenerateFile(
            ArgumentsFinder argumentsFinder,
            ReceiptIdFilter receiptIdFilter,
            ServiceFactory serviceFactory,
            Collection<Receipt> destinationReceipts) {

        this.argumentsFinder = argumentsFinder;
        this.receiptIdFilter = receiptIdFilter;
        this.serviceFactory = serviceFactory;
        this.destinationReceipts = destinationReceipts;
    }

    @Override
    public Void call() throws Exception {
        deserializeFromGenerateFile(argumentsFinder, receiptIdFilter, serviceFactory, destinationReceipts);
        return null;
    }

    private static void deserializeFromGenerateFile(
            ArgumentsFinder finder,
            ReceiptIdFilter receiptIdFilter,
            ServiceFactory serviceFactory,
            Collection<Receipt> receipts) throws IOException, ArgumentNotExistException {

        var format = finder.firstStringOrDefault(Keys.DESERIALIZE_GENERATE_FORMAT, Formats.JSON);
        var path = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_GENERATE_PATH);

        IGenerateReceiptService generateReceiptService = serviceFactory.instance(GenerateReceiptService.class);

        var checkList = generateReceiptService.fromGenerate(new File(path), format);
        receipts.addAll(receiptIdFilter.applyFilterIfExist(checkList));
    }
}

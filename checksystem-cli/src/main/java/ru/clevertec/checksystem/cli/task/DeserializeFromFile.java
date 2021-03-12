package ru.clevertec.checksystem.cli.task;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.ReceiptIdFilter;
import ru.clevertec.checksystem.cli.exception.ArgumentNotExistException;
import ru.clevertec.checksystem.core.common.service.IIoReceiptService;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.IoReceiptService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class DeserializeFromFile implements Callable<Void> {

    private final ArgumentsFinder argumentsFinder;
    private final ReceiptIdFilter receiptIdFilter;
    private final ServiceFactory serviceFactory;
    private final Collection<Receipt> destinationReceipts;

    public DeserializeFromFile(
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
        deserializeFromFile(argumentsFinder, receiptIdFilter, serviceFactory, destinationReceipts);
        return null;
    }

    private static void deserializeFromFile(
            ArgumentsFinder finder,
            ReceiptIdFilter receiptIdFilter,
            ServiceFactory serviceFactory,
            Collection<Receipt> destinationReceipts) throws IOException, ArgumentNotExistException {

        var format = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_FORMAT);
        var path = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_PATH);

        IIoReceiptService ioService = serviceFactory.instance(IoReceiptService.class);

        var receipts = ioService.deserialize(new File(path), format);
        destinationReceipts.addAll(receiptIdFilter.applyFilterIfExist(receipts));
    }
}

package ru.clevertec.checksystem.cli.task;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.ReceiptIdFilter;
import ru.clevertec.checksystem.core.common.service.IReceiptService;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.ReceiptService;

import java.util.Collection;
import java.util.concurrent.Callable;

public class LoadFromDatabase implements Callable<Void> {

    private final ArgumentsFinder argumentsFinder;
    private final ReceiptIdFilter receiptIdFilter;
    private final ServiceFactory serviceFactory;
    private final Collection<Receipt> destinationReceipts;

    public LoadFromDatabase(
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
        loadFromDatabase(argumentsFinder, receiptIdFilter, serviceFactory, destinationReceipts);
        return null;
    }

    private static void loadFromDatabase(
            ArgumentsFinder finder,
            ReceiptIdFilter receiptIdFilter,
            ServiceFactory serviceFactory,
            Collection<Receipt> destinationReceipts) {

        IReceiptService checkService = serviceFactory.instance(ReceiptService.class);

        if (finder.hasArgumentKey(Constants.Keys.INPUT_FILTER_ID))
            checkService.getReceiptRepository().findAllById(
                    receiptIdFilter.getIdentifiers()).forEach(destinationReceipts::add);
        else
            checkService.getReceiptRepository().findAll().forEach(destinationReceipts::add);
    }
}

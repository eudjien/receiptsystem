package ru.clevertec.checksystem.cli.task.input;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentFinder;
import ru.clevertec.checksystem.cli.argument.ReceiptIdFilter;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;

import java.util.Collection;
import java.util.concurrent.Callable;

public class LoadFromDatabase implements Callable<Void> {

    private final ArgumentFinder argumentFinder;
    private final ReceiptIdFilter receiptIdFilter;
    private final Collection<Receipt> destinationReceipts;
    private final ReceiptRepository receiptRepository;

    public LoadFromDatabase(
            ArgumentFinder argumentFinder,
            ReceiptIdFilter receiptIdFilter,
            ReceiptRepository receiptRepository,
            Collection<Receipt> destinationReceipts) {
        this.argumentFinder = argumentFinder;
        this.receiptIdFilter = receiptIdFilter;
        this.receiptRepository = receiptRepository;
        this.destinationReceipts = destinationReceipts;
    }

    @Override
    public Void call() throws Exception {
        loadFromDatabase(argumentFinder, receiptIdFilter, destinationReceipts);
        return null;
    }

    private void loadFromDatabase(
            ArgumentFinder finder,
            ReceiptIdFilter receiptIdFilter,
            Collection<Receipt> destinationReceipts) {

        if (finder.hasArgumentKey(Constants.Keys.INPUT_FILTER_ID))
            receiptRepository.findAllById(receiptIdFilter.getIdentifiers()).forEach(destinationReceipts::add);
        else
            receiptRepository.findAll().forEach(destinationReceipts::add);
    }
}

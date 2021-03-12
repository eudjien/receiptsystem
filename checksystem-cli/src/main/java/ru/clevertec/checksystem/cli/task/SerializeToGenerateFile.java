package ru.clevertec.checksystem.cli.task;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.exception.ArgumentNotExistException;
import ru.clevertec.checksystem.core.common.service.IGenerateReceiptService;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.GenerateReceiptService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class SerializeToGenerateFile implements Callable<Void> {

    private final ArgumentsFinder argumentsFinder;
    private final ServiceFactory serviceFactory;
    private final Collection<Receipt> receipts;

    public SerializeToGenerateFile(
            ArgumentsFinder argumentsFinder, ServiceFactory serviceFactory, Collection<Receipt> receipts) {

        this.argumentsFinder = argumentsFinder;
        this.serviceFactory = serviceFactory;
        this.receipts = receipts;
    }

    @Override
    public Void call() throws Exception {
        serializeToFile(argumentsFinder, serviceFactory, receipts);
        return null;
    }

    private static void serializeToFile(
            ArgumentsFinder finder, ServiceFactory serviceFactory, Collection<Receipt> receipts) throws IOException, ArgumentNotExistException {

        var format = finder.firstStringOrThrow(Constants.Keys.GENERATE_SERIALIZE_FORMAT);
        var path = finder.firstStringOrThrow(Constants.Keys.GENERATE_SERIALIZE_PATH);

        IGenerateReceiptService generateReceiptService = serviceFactory.instance(GenerateReceiptService.class);
        generateReceiptService.toGenerate(receipts, new File(path), format);
    }
}

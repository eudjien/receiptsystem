package ru.clevertec.checksystem.cli.task;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.CheckIdFilter;
import ru.clevertec.checksystem.cli.exception.ArgumentNotExistException;
import ru.clevertec.checksystem.core.common.service.IIoCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.IoCheckService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class DeserializeFromFile implements Callable<Void> {

    private final ArgumentsFinder argumentsFinder;
    private final CheckIdFilter checkIdFilter;
    private final ServiceFactory serviceFactory;
    private final Collection<Check> destinationChecks;

    public DeserializeFromFile(
            ArgumentsFinder argumentsFinder,
            CheckIdFilter checkIdFilter,
            ServiceFactory serviceFactory,
            Collection<Check> destinationChecks) {
        this.argumentsFinder = argumentsFinder;
        this.checkIdFilter = checkIdFilter;
        this.serviceFactory = serviceFactory;
        this.destinationChecks = destinationChecks;
    }

    @Override
    public Void call() throws Exception {
        deserializeFromFile(argumentsFinder, checkIdFilter, serviceFactory, destinationChecks);
        return null;
    }

    private static void deserializeFromFile(
            ArgumentsFinder finder,
            CheckIdFilter checkIdFilter,
            ServiceFactory serviceFactory,
            Collection<Check> destinationChecks) throws IOException, ArgumentNotExistException {

        var format = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_FORMAT);
        var path = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_PATH);

        IIoCheckService ioService = serviceFactory.instance(IoCheckService.class);

        var checks = ioService.deserialize(new File(path), format);
        destinationChecks.addAll(checkIdFilter.applyFilterIfExist(checks));
    }
}

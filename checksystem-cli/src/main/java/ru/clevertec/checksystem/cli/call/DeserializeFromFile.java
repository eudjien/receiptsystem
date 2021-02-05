package ru.clevertec.checksystem.cli.call;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.CheckIdArgumentFilter;
import ru.clevertec.checksystem.core.common.service.IIoCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.IoCheckService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class DeserializeFromFile implements Callable<CallResult> {

    private final ArgumentsFinder argumentsFinder;
    private final CheckIdArgumentFilter checkIdArgumentFilter;
    private final ServiceFactory serviceFactory;
    private final Collection<Check> destinationChecks;

    public DeserializeFromFile(
            ArgumentsFinder argumentsFinder,
            CheckIdArgumentFilter checkIdArgumentFilter,
            ServiceFactory serviceFactory,
            Collection<Check> destinationChecks) {
        this.argumentsFinder = argumentsFinder;
        this.checkIdArgumentFilter = checkIdArgumentFilter;
        this.serviceFactory = serviceFactory;
        this.destinationChecks = destinationChecks;
    }

    @Override
    public CallResult call() {
        try {
            deserializeFromFile(argumentsFinder, checkIdArgumentFilter, serviceFactory, destinationChecks);
        } catch (Exception e) {
            return CallResult.fail(e, "Something went wrong with deserializing from a file.");
        }
        return CallResult.success("Deserializing from a file is complete.");
    }

    private static void deserializeFromFile(
            ArgumentsFinder finder,
            CheckIdArgumentFilter checkIdArgumentFilter,
            ServiceFactory serviceFactory,
            Collection<Check> destinationChecks) throws IOException {

        var format = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_FORMAT);
        var path = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_PATH);

        IIoCheckService ioService = serviceFactory.instance(IoCheckService.class);
        var checks = ioService.deserialize(new File(path), format);
        destinationChecks.addAll(checkIdArgumentFilter.applyFilterIfExist(checks));
    }
}

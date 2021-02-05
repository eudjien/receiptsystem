package ru.clevertec.checksystem.cli.call;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.core.common.service.IIoCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.IoCheckService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class SerializeToFile implements Callable<CallResult> {

    private final ArgumentsFinder argumentsFinder;
    private final ServiceFactory serviceFactory;
    private final Collection<Check> checks;

    public SerializeToFile(
            ArgumentsFinder argumentsFinder, ServiceFactory serviceFactory, Collection<Check> checks) {

        this.argumentsFinder = argumentsFinder;
        this.serviceFactory = serviceFactory;
        this.checks = checks;
    }

    @Override
    public CallResult call() {
        try {
            serializeToFile(argumentsFinder, serviceFactory, checks);
        } catch (Exception e) {
            return CallResult.fail(e, "Something went wrong while serializing to a file.");
        }
        return CallResult.success("Serializing to a file is complete.");
    }

    private static void serializeToFile(
            ArgumentsFinder finder, ServiceFactory serviceFactory, Collection<Check> checks) throws IOException {

        var format = finder.firstStringOrThrow(Constants.Keys.SERIALIZE_FORMAT);
        var path = finder.firstStringOrThrow(Constants.Keys.SERIALIZE_PATH);
        IIoCheckService ioService = serviceFactory.instance(IoCheckService.class);
        ioService.serialize(checks, new File(path), format);
    }
}

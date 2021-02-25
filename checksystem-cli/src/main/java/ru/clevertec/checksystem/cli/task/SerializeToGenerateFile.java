package ru.clevertec.checksystem.cli.task;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.exception.ArgumentNotExistException;
import ru.clevertec.checksystem.core.common.service.IGenerateCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.GenerateCheckService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class SerializeToGenerateFile implements Callable<Void> {

    private final ArgumentsFinder argumentsFinder;
    private final ServiceFactory serviceFactory;
    private final Collection<Check> checks;

    public SerializeToGenerateFile(
            ArgumentsFinder argumentsFinder, ServiceFactory serviceFactory, Collection<Check> checks) {

        this.argumentsFinder = argumentsFinder;
        this.serviceFactory = serviceFactory;
        this.checks = checks;
    }

    @Override
    public Void call() throws Exception {
        serializeToFile(argumentsFinder, serviceFactory, checks);
        return null;
    }

    private static void serializeToFile(
            ArgumentsFinder finder, ServiceFactory serviceFactory, Collection<Check> checks) throws IOException, ArgumentNotExistException {

        var format = finder.firstStringOrThrow(Constants.Keys.GENERATE_SERIALIZE_FORMAT);
        var path = finder.firstStringOrThrow(Constants.Keys.GENERATE_SERIALIZE_PATH);

        IGenerateCheckService generateCheckService = serviceFactory.instance(GenerateCheckService.class);
        generateCheckService.toGenerate(checks, new File(path), format);
    }
}

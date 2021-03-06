package ru.clevertec.checksystem.cli.task;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.CheckIdFilter;
import ru.clevertec.checksystem.cli.exception.ArgumentNotExistException;
import ru.clevertec.checksystem.core.common.service.IGenerateCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.GenerateCheckService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

import static ru.clevertec.checksystem.cli.Constants.Keys;
import static ru.clevertec.checksystem.core.Constants.Format;

public class DeserializeFromGenerateFile implements Callable<Void> {

    private final ArgumentsFinder argumentsFinder;
    private final CheckIdFilter checkIdFilter;
    private final ServiceFactory serviceFactory;
    private final Collection<Check> destinationChecks;

    public DeserializeFromGenerateFile(
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
        deserializeFromGenerateFile(argumentsFinder, checkIdFilter, serviceFactory, destinationChecks);
        return null;
    }

    private static void deserializeFromGenerateFile(
            ArgumentsFinder finder,
            CheckIdFilter checkIdFilter,
            ServiceFactory serviceFactory,
            Collection<Check> checks) throws IOException, ArgumentNotExistException {

        var format = finder.firstStringOrDefault(Keys.DESERIALIZE_GENERATE_FORMAT, Format.JSON);
        var path = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_GENERATE_PATH);

        IGenerateCheckService generateCheckService = serviceFactory.instance(GenerateCheckService.class);

        var checkList = generateCheckService.fromGenerate(new File(path), format);
        checks.addAll(checkIdFilter.applyFilterIfExist(checkList));
    }
}

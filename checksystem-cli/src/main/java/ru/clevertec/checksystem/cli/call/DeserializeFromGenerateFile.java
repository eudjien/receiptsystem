package ru.clevertec.checksystem.cli.call;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.CheckIdArgumentFilter;
import ru.clevertec.checksystem.core.common.service.IGenerateCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.GenerateCheckService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class DeserializeFromGenerateFile implements Callable<CallResult> {

    private final ArgumentsFinder argumentsFinder;
    private final CheckIdArgumentFilter checkIdArgumentFilter;
    private final ServiceFactory serviceFactory;
    private final Collection<Check> destinationChecks;

    public DeserializeFromGenerateFile(
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
            deserializeFromGenerateFile(argumentsFinder, checkIdArgumentFilter, serviceFactory, destinationChecks);
            System.out.println("Serializing to a file completed.");
        } catch (Exception e) {
            return CallResult.fail(e, "Something went wrong with deserializing from a generated file.");
        }
        return CallResult.success("Deserializing from a generate file is complete.");
    }

    private static void deserializeFromGenerateFile(
            ArgumentsFinder finder,
            CheckIdArgumentFilter checkIdArgumentFilter,
            ServiceFactory serviceFactory,
            Collection<Check> checks) throws IOException {

        var format = finder.firstStringOrDefault(Constants.Keys.DESERIALIZE_GENERATE_FORMAT, ru.clevertec.checksystem.core.Constants.Format.IO.JSON);
        var path = finder.firstStringOrThrow(Constants.Keys.DESERIALIZE_GENERATE_PATH);

        IGenerateCheckService generateCheckService = serviceFactory.instance(GenerateCheckService.class);
        var checkList = generateCheckService.fromGenerate(new File(path), format);
        checks.addAll(checkIdArgumentFilter.applyFilterIfExist(checkList));
    }
}

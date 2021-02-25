package ru.clevertec.checksystem.cli.call;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.core.common.service.IGenerateCheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.GenerateCheckService;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

public class SerializeToGenerateFile implements Callable<CallResult> {

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
    public CallResult call() {
        try {
            serializeToFile(argumentsFinder, serviceFactory, checks);
        } catch (Exception e) {
            return CallResult.fail(e, "Something went wrong while serializing to a generate file.");
        }
        return CallResult.success("Serializing to a generate file is complete.");
    }

    private static void serializeToFile(
            ArgumentsFinder finder, ServiceFactory serviceFactory, Collection<Check> checks) throws IOException {

        var format = finder.firstStringOrThrow(Constants.Keys.GENERATE_SERIALIZE_FORMAT);
        var path = finder.firstStringOrThrow(Constants.Keys.GENERATE_SERIALIZE_PATH);
        IGenerateCheckService generateCheckService = serviceFactory.instance(GenerateCheckService.class);
        generateCheckService.toGenerate(checks, new File(path), format);
    }
}

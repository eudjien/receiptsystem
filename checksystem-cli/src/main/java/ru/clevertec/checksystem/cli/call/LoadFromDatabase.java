package ru.clevertec.checksystem.cli.call;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.CheckIdArgumentFilter;
import ru.clevertec.checksystem.core.common.service.ICheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.CheckService;

import java.util.Collection;
import java.util.concurrent.Callable;

public class LoadFromDatabase implements Callable<CallResult> {

    private final ArgumentsFinder argumentsFinder;
    private final CheckIdArgumentFilter checkIdArgumentFilter;
    private final ServiceFactory serviceFactory;
    private final Collection<Check> destinationChecks;

    public LoadFromDatabase(
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
            loadFromDatabase(argumentsFinder, checkIdArgumentFilter, serviceFactory, destinationChecks);
        } catch (Exception e) {
            return CallResult.fail(e, "Something went wrong with load from a database.");
        }
        return CallResult.success("Load from a database is complete.");
    }

    private static void loadFromDatabase(
            ArgumentsFinder finder,
            CheckIdArgumentFilter checkIdArgumentFilter,
            ServiceFactory serviceFactory,
            Collection<Check> destinationChecks) {

        ICheckService checkService = serviceFactory.instance(CheckService.class);

        if (finder.hasArgumentKey(Constants.Keys.INPUT_FILTER_ID))
            checkService.getCheckRepository().findAllById(checkIdArgumentFilter.getIdentifiers()).forEach(destinationChecks::add);
        else
            checkService.getCheckRepository().findAll().forEach(destinationChecks::add);
    }
}

package ru.clevertec.checksystem.cli.task;

import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.cli.argument.ArgumentsFinder;
import ru.clevertec.checksystem.cli.argument.CheckIdFilter;
import ru.clevertec.checksystem.core.common.service.ICheckService;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.factory.service.ServiceFactory;
import ru.clevertec.checksystem.core.service.CheckService;

import java.util.Collection;
import java.util.concurrent.Callable;

public class LoadFromDatabase implements Callable<Void> {

    private final ArgumentsFinder argumentsFinder;
    private final CheckIdFilter checkIdFilter;
    private final ServiceFactory serviceFactory;
    private final Collection<Check> destinationChecks;

    public LoadFromDatabase(
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
        loadFromDatabase(argumentsFinder, checkIdFilter, serviceFactory, destinationChecks);
        return null;
    }

    private static void loadFromDatabase(
            ArgumentsFinder finder,
            CheckIdFilter checkIdFilter,
            ServiceFactory serviceFactory,
            Collection<Check> destinationChecks) {

        ICheckService checkService = serviceFactory.instance(CheckService.class);

        if (finder.hasArgumentKey(Constants.Keys.INPUT_FILTER_ID))
            checkService.getCheckRepository().findAllById(
                    checkIdFilter.getIdentifiers()).forEach(destinationChecks::add);
        else
            checkService.getCheckRepository().findAll().forEach(destinationChecks::add);
    }
}

package ru.clevertec.checksystem.cli.argument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.cli.Constants;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckIdArgumentFilter {

    private final ArgumentsFinder finder;

    @Autowired
    public CheckIdArgumentFilter(ArgumentsFinder finder) {
        this.finder = finder;
    }

    public Collection<Check> applyFilterIfExist(Collection<Check> checks) {

        var value = finder.firstStringOrDefault(Constants.Keys.INPUT_FILTER_ID);

        if (value != null) {

            var ids = getIdentifiers();

            return checks.stream()
                    .filter(check -> ids.contains(check.getId()))
                    .collect(Collectors.toCollection(SinglyLinkedList<Check>::new));
        }

        return checks;
    }

    public List<Long> getIdentifiers() {
        var argumentValue = finder.firstStringOrDefault(Constants.Keys.INPUT_FILTER_ID);
        var checkIds = new SinglyLinkedList<Long>();
        if (argumentValue != null) {
            var values = argumentValue.split(",");
            for (var s : values) {
                try {
                    checkIds.add(Long.parseLong(s));
                } catch (Exception ignored) {
                }
            }
        }
        return checkIds;
    }
}

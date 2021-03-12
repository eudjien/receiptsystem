package ru.clevertec.checksystem.webuiservlet;

import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.repository.CheckRepository;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.custom.list.SinglyLinkedList;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ChecksDataSource {

    private final HttpSession httpSession;
    private final CheckRepository checkRepository;
    private final String sessionName;

    public ChecksDataSource(CheckRepository checkRepository, HttpSession httpSession, String sessionName) {
        this.checkRepository = checkRepository;
        this.httpSession = httpSession;
        this.sessionName = sessionName;
    }

    @SuppressWarnings("unchecked")
    public Collection<Check> findAll(String source) {

        var list = new SinglyLinkedList<Check>();

        switch (source) {
            case Constants.Sources.DATABASE -> list.addAll(CollectionUtils.asList(checkRepository.findAll()));
            case Constants.Sources.FILE -> {
                var checks = httpSession.getAttribute(sessionName);
                if (checks != null)
                    list.addAll((Collection<Check>) checks);
            }
            default -> throw new ArgumentNotSupportedException("source");
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public Collection<Check> findAllById(String source, Collection<Long> ids) {
        switch (source) {
            case Constants.Sources.DATABASE:
                return CollectionUtils.asList(checkRepository.findAllById(ids));
            case Constants.Sources.FILE:
                var checksObj = httpSession.getAttribute(sessionName);
                if (checksObj != null) {
                    var checks = ((Collection<Check>) checksObj);
                    return ids.stream()
                            .map(id -> checks.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow())
                            .sorted(Comparator.comparing(BaseEntity::getId))
                            .collect(Collectors.toList());
                }
                return new SinglyLinkedList<>();
            default:
                throw new ArgumentNotSupportedException("source");
        }
    }
}

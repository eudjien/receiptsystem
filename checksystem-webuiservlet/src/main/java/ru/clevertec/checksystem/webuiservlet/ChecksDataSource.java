package ru.clevertec.checksystem.webuiservlet;

import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.repository.CheckRepository;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.custom.list.SinglyLinkedList;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.stream.Collectors;

public class ChecksDataSource {

    private final HttpSession httpSession;
    private final CheckRepository checkRepository;
    private final String sessionAttributeName;

    public ChecksDataSource(CheckRepository checkRepository, HttpSession httpSession, String sessionAttributeName) {
        this.checkRepository = checkRepository;
        this.httpSession = httpSession;
        this.sessionAttributeName = sessionAttributeName;
    }

    @SuppressWarnings("unchecked")
    public Collection<Check> findAll(String source) {
        switch (source) {
            case Constants.Sources.DATABASE:
                return CollectionUtils.asList(checkRepository.findAll());
            case Constants.Sources.FILE:
                var checks = httpSession.getAttribute(sessionAttributeName);
                if (checks != null)
                    return (Collection<Check>) checks;
                return new SinglyLinkedList<>();
            default:
                throw new ArgumentNotSupportedException("source");
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<Check> findAllById(String source, Collection<Long> ids) {
        switch (source) {
            case Constants.Sources.DATABASE:
                return CollectionUtils.asList(checkRepository.findAllById(ids));
            case Constants.Sources.FILE:
                var checksObj = httpSession.getAttribute(sessionAttributeName);
                if (checksObj != null) {
                    var checks = ((Collection<Check>) checksObj);
                    return ids.stream()
                            .map(id -> checks.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow())
                            .collect(Collectors.toList());
                }
                return new SinglyLinkedList<>();
            default:
                throw new ArgumentNotSupportedException("source");
        }
    }
}

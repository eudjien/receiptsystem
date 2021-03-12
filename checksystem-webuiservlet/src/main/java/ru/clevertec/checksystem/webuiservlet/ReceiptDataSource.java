package ru.clevertec.checksystem.webuiservlet;

import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.core.util.CollectionUtils;
import ru.clevertec.custom.list.SinglyLinkedList;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ReceiptDataSource {

    private final HttpSession httpSession;
    private final ReceiptRepository receiptRepository;
    private final String sessionName;

    public ReceiptDataSource(ReceiptRepository receiptRepository, HttpSession httpSession, String sessionName) {
        this.receiptRepository = receiptRepository;
        this.httpSession = httpSession;
        this.sessionName = sessionName;
    }

    @SuppressWarnings("unchecked")
    public Collection<Receipt> findAll(String source) {

        var list = new SinglyLinkedList<Receipt>();

        switch (source) {
            case Constants.Sources.DATABASE -> list.addAll(CollectionUtils.asList(receiptRepository.findAll()));
            case Constants.Sources.FILE -> {
                var receipts = httpSession.getAttribute(sessionName);
                if (receipts != null)
                    list.addAll((Collection<Receipt>) receipts);
            }
            default -> throw new ArgumentNotSupportedException("source");
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public Collection<Receipt> findAllById(String source, Collection<Long> ids) {
        switch (source) {
            case Constants.Sources.DATABASE:
                return CollectionUtils.asList(receiptRepository.findAllById(ids));
            case Constants.Sources.FILE:
                var checksObj = httpSession.getAttribute(sessionName);
                if (checksObj != null) {
                    var receipts = ((Collection<Receipt>) checksObj);
                    return ids.stream()
                            .map(id -> receipts.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow())
                            .sorted(Comparator.comparing(BaseEntity::getId))
                            .collect(Collectors.toList());
                }
                return new SinglyLinkedList<>();
            default:
                throw new ArgumentNotSupportedException("source");
        }
    }
}

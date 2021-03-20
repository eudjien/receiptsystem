package ru.clevertec.checksystem.webuiservlet;

import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.webuiservlet.constant.Sessions;
import ru.clevertec.checksystem.webuiservlet.constant.Sources;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ReceiptDataSource {

    private final HttpSession httpSession;
    private final ReceiptRepository receiptRepository;

    public ReceiptDataSource(ReceiptRepository receiptRepository, HttpSession httpSession) {
        this.receiptRepository = receiptRepository;
        this.httpSession = httpSession;
    }

    @SuppressWarnings("unchecked")
    public Collection<Receipt> findAll(String source) {

        var list = new ArrayList<Receipt>();

        switch (source) {
            case Sources.DATABASE -> list.addAll(
                    StreamSupport.stream(receiptRepository.findAll().spliterator(), true)
                            .collect(Collectors.toList())
            );
            case Sources.FILE -> {
                var receipts = httpSession.getAttribute(Sessions.RECEIPTS_SESSION);
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
            case Sources.DATABASE:
                return StreamSupport.stream(receiptRepository.findAllById(ids).spliterator(), true)
                        .collect(Collectors.toList());
            case Sources.FILE:
                var checksObj = httpSession.getAttribute(Sessions.RECEIPTS_SESSION);
                if (checksObj != null) {
                    var receipts = ((Collection<Receipt>) checksObj);
                    return ids.stream()
                            .map(id -> receipts.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow())
                            .sorted(Comparator.comparing(BaseEntity::getId))
                            .collect(Collectors.toList());
                }
                return new ArrayList<>();
            default:
                throw new ArgumentNotSupportedException("source");
        }
    }
}

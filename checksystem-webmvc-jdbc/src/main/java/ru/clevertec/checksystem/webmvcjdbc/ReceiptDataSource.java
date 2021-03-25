package ru.clevertec.checksystem.webmvcjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.exception.ArgumentNotSupportedException;
import ru.clevertec.checksystem.core.service.IReceiptService;
import ru.clevertec.checksystem.webmvcjdbc.constant.Sessions;
import ru.clevertec.checksystem.webmvcjdbc.constant.Sources;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class ReceiptDataSource {

    private final HttpSession httpSession;
    private final IReceiptService receiptService;

    @Autowired
    public ReceiptDataSource(IReceiptService receiptService, HttpSession httpSession) {
        this.receiptService = receiptService;
        this.httpSession = httpSession;
    }

    @SuppressWarnings("unchecked")
    public Collection<Receipt> findAll(String source) {
        switch (source) {
            case Sources.DATABASE:
                return receiptService.findAll();
            case Sources.FILE:
                var receipts = httpSession.getAttribute(Sessions.RECEIPTS_SESSION);
                return receipts != null ? (Collection<Receipt>) receipts : new ArrayList<>();
            default:
                throw new ArgumentNotSupportedException("source");
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<Receipt> findAllById(String source, Collection<Long> ids) {
        switch (source) {
            case Sources.DATABASE:
                return receiptService.findAllById(ids);
            case Sources.FILE:
                var receiptsObj = httpSession.getAttribute(Sessions.RECEIPTS_SESSION);
                if (receiptsObj != null) {
                    var receipts = ((Collection<Receipt>) receiptsObj);
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

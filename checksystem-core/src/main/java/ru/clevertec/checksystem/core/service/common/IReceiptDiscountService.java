package ru.clevertec.checksystem.core.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;

import java.util.Collection;
import java.util.List;

public interface IReceiptDiscountService {

    ReceiptDiscountDto getReceiptDiscountById(Long id);

    List<ReceiptDiscountDto> getReceiptDiscounts();

    List<ReceiptDiscountDto> getReceiptDiscounts(Sort sort);

    Page<ReceiptDiscountDto> getReceiptDiscountsPage(Pageable pageable);

    List<ReceiptDiscountDto> getReceiptDiscountsById(Collection<Long> ids);

    List<ReceiptDiscountDto> getReceiptDiscountsById(Sort sort, Collection<Long> ids);

    Page<ReceiptDiscountDto> getReceiptDiscountsPageById(Pageable page, Collection<Long> ids);

    void deleteReceiptDiscountById(Long id);

    ReceiptDiscountDto createReceiptDiscount(ReceiptDiscountDto receiptDiscountDto);

    ReceiptDiscountDto updateReceiptDiscount(ReceiptDiscountDto receiptDiscountDto);

    Long getReceiptDiscountCount();
}

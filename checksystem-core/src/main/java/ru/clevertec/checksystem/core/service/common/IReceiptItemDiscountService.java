package ru.clevertec.checksystem.core.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;

import java.util.Collection;
import java.util.List;

public interface IReceiptItemDiscountService {

    ReceiptItemDiscountDto getReceiptItemDiscountById(Long id);

    List<ReceiptItemDiscountDto> getReceiptItemDiscounts();

    List<ReceiptItemDiscountDto> getReceiptItemDiscounts(Sort sort);

    Page<ReceiptItemDiscountDto> getReceiptItemDiscountsPage(Pageable pageable);

    List<ReceiptItemDiscountDto> getReceiptItemDiscountsById(Collection<Long> ids);

    List<ReceiptItemDiscountDto> getReceiptItemDiscountsById(Sort sort, Collection<Long> ids);

    Page<ReceiptItemDiscountDto> getReceiptItemDiscountsPageById(Pageable page, Collection<Long> ids);

    void deleteReceiptItemDiscountById(Long id);

    ReceiptItemDiscountDto createReceiptItemDiscount(ReceiptItemDiscountDto receiptItemDiscountDto);

    ReceiptItemDiscountDto updateReceiptItemDiscount(ReceiptItemDiscountDto receiptItemDiscountDto);

    Long getReceiptItemDiscountCount();
}

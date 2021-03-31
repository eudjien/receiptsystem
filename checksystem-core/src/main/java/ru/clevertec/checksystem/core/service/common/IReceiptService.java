package ru.clevertec.checksystem.core.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.clevertec.checksystem.core.dto.ProductDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.receipt.ReceiptDto;
import ru.clevertec.checksystem.core.dto.receipt.ReceiptItemDto;

import java.util.Collection;
import java.util.List;

public interface IReceiptService {

    ReceiptDto getReceiptById(Long id);

    ReceiptItemDto getReceiptItemById(Long id);

    ReceiptItemDto getReceiptItemByIdAndReceiptId(Long id, Long receiptId);

    ProductDto getProductByReceiptItemId(Long receiptItemId);

    ProductDto getProductByReceiptIdAndItemId(Long receiptId, Long receiptItemId);

    ReceiptDiscountDto getReceiptDiscountByIdAndReceiptId(Long receiptId, Long receiptDiscountId);

    ReceiptItemDiscountDto getReceiptItemDiscountByIdAndReceiptItemId(Long receiptItemId, Long receiptItemDiscountId);

    List<ReceiptDto> getReceipts();

    List<ReceiptDto> getReceipts(Sort sort);

    Page<ReceiptDto> getReceiptsPage(Pageable pageable);

    List<ReceiptDto> getReceiptsById(Collection<Long> ids);

    List<ReceiptDto> findReceiptsById(Sort sort, Collection<Long> ids);

    Page<ReceiptDto> getReceiptsPageById(Pageable pageable, Collection<Long> ids);

    Page<ReceiptItemDto> getReceiptItemsPage(Pageable pageable, Long receiptId);

    List<ReceiptItemDto> getReceiptItems(Sort sort, Long receiptId);

    List<ReceiptItemDto> getReceiptItems(Long receiptId);

    void deleteReceiptById(Long id);

    void deleteReceiptItemById(Long id);

    ReceiptDto createReceipt(ReceiptDto dto);

    ReceiptDto updateReceipt(ReceiptDto dto);

    ReceiptItemDto createReceiptItem(ReceiptItemDto dto);

    ReceiptItemDto updateReceiptItem(ReceiptItemDto dto);

    void addDiscountToReceipt(Long receiptId, Long receiptDiscountId);

    void removeDiscountFromReceipt(Long receiptId, Long receiptDiscountId);

    List<ReceiptDiscountDto> getDiscounts(Sort sort, Long receiptId);

    List<ReceiptDiscountDto> getDiscounts(Long receiptId);

    Page<ReceiptDiscountDto> getReceiptDiscountsPage(Pageable pageable, Long receiptId);

    Page<ReceiptItemDiscountDto> getReceiptItemDiscountsPage(Pageable pageable, Long receiptItemId);

    List<ReceiptItemDto> getReceiptItems();

    List<ReceiptItemDto> getReceiptItems(Sort sort);

    Page<ReceiptItemDto> getReceiptItemsPage(Pageable pageable);

    List<ReceiptItemDto> getReceiptItemsById(Collection<Long> receiptIds);

    List<ReceiptItemDto> getReceiptItemsById(Sort sort, Collection<Long> ids);

    Page<ReceiptItemDto> getReceiptItemsPageById(Pageable page, Collection<Long> ids);

    List<ReceiptItemDto> getReceiptItemsByReceiptId(Long receiptId);

    List<ReceiptItemDto> getReceiptItemsByReceiptId(Sort sort, Long receiptId);

    Page<ReceiptItemDto> getReceiptItemsPageByReceiptId(Pageable pageable, Long receiptId);

    ReceiptItemDto removeItemFromReceipt(Long receiptItemId, Long receiptId);

    ReceiptItemDto addItemFromReceipt(ReceiptItemDto receiptItemDto);

    Long getReceiptCount();

    Long getReceiptItemCount();
}

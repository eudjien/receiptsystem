package ru.clevertec.checksystem.webapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checksystem.core.dto.ProductDto;
import ru.clevertec.checksystem.core.dto.SummaryDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.receipt.ReceiptDto;
import ru.clevertec.checksystem.core.dto.receipt.ReceiptItemDto;
import ru.clevertec.checksystem.core.service.common.IReceiptService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReceiptController {

    private final IReceiptService receiptService;

    @GetMapping
    ResponseEntity<Page<ReceiptDto>> get(Pageable pageable) {
        return new ResponseEntity<>(receiptService.getReceiptsPage(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<ReceiptDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(receiptService.getReceiptById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/summary")
    ResponseEntity<SummaryDto> getSummary(@PathVariable Long id) {
        return new ResponseEntity<>(receiptService.getReceiptSummaryById(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    ResponseEntity<Long> getCount() {
        return new ResponseEntity<>(receiptService.getReceiptCount(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        receiptService.deleteReceiptById(id);
    }

    @PutMapping
    ResponseEntity<ReceiptDto> update(@RequestBody @Valid ReceiptDto receiptDto) {
        return new ResponseEntity<>(receiptService.updateReceipt(receiptDto), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<ReceiptDto> create(@RequestBody @Valid ReceiptDto receiptDto) {
        return new ResponseEntity<>(receiptService.createReceipt(receiptDto), HttpStatus.CREATED);
    }

    @GetMapping("/{receiptId}/items")
    ResponseEntity<Page<ReceiptItemDto>> getItems(Pageable pageable, @PathVariable Long receiptId) {
        return new ResponseEntity<>(receiptService.getReceiptItemsPage(pageable, receiptId), HttpStatus.OK);
    }

    @GetMapping("/{receiptId}/items/{receiptItemId}")
    ResponseEntity<ReceiptItemDto> getItem(@PathVariable Long receiptId, @PathVariable Long receiptItemId) {
        return new ResponseEntity<>(receiptService.getReceiptItemByIdAndReceiptId(receiptItemId, receiptId), HttpStatus.OK);
    }

    @GetMapping("/{receiptId}/items/{receiptItemId}/product")
    ResponseEntity<ProductDto> getItemProduct(@PathVariable Long receiptId, @PathVariable Long receiptItemId) {
        return new ResponseEntity<>(receiptService.getProductByReceiptIdAndItemId(receiptId, receiptItemId), HttpStatus.OK);
    }

    @GetMapping("/{receiptId}/discounts")
    ResponseEntity<Page<ReceiptDiscountDto>> getDiscounts(Pageable pageable, @PathVariable Long receiptId) {
        return new ResponseEntity<>(receiptService.getReceiptDiscountsPage(pageable, receiptId), HttpStatus.OK);
    }

    @GetMapping("/{receiptId}/discounts/{discountId}")
    ResponseEntity<ReceiptDiscountDto> getDiscount(@PathVariable Long receiptId, @PathVariable Long discountId) {
        return new ResponseEntity<>(receiptService.getReceiptDiscountByIdAndReceiptId(discountId, receiptId), HttpStatus.OK);
    }
}

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
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.receipt.ReceiptItemDto;
import ru.clevertec.checksystem.core.service.common.IReceiptService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/receiptItems", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReceiptItemController {

    private final IReceiptService receiptService;

    @GetMapping
    ResponseEntity<Page<ReceiptItemDto>> get(Pageable pageable) {
        return new ResponseEntity<>(receiptService.getReceiptItemsPage(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<ReceiptItemDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(receiptService.getReceiptItemById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/summary")
    ResponseEntity<SummaryDto> getSummary(@PathVariable Long id) {
        return new ResponseEntity<>(receiptService.getReceiptItemSummaryById(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    ResponseEntity<Long> getCount() {
        return new ResponseEntity<>(receiptService.getReceiptItemCount(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        receiptService.deleteReceiptItemById(id);
    }

    @PutMapping
    ResponseEntity<ReceiptItemDto> update(@RequestBody @Valid ReceiptItemDto receiptItemDto) {
        return new ResponseEntity<>(receiptService.updateReceiptItem(receiptItemDto), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<ReceiptItemDto> create(@RequestBody @Valid ReceiptItemDto receiptItemDto) {
        return new ResponseEntity<>(receiptService.createReceiptItem(receiptItemDto), HttpStatus.CREATED);
    }

    @GetMapping("/{receiptItemId}/product")
    ResponseEntity<ProductDto> getProduct(@PathVariable Long receiptItemId) {
        return new ResponseEntity<>(receiptService.getProductByReceiptItemId(receiptItemId), HttpStatus.OK);
    }

    @GetMapping("/{receiptItemId}/discounts")
    Page<ReceiptItemDiscountDto> getReceiptItemDiscounts(Pageable pageable, @PathVariable Long receiptItemId) {
        return receiptService.getReceiptItemDiscountsPage(pageable, receiptItemId);
    }

    @GetMapping("/{receiptItemId}/discounts/{receiptItemDiscountId}")
    ResponseEntity<ReceiptItemDiscountDto> getReceiptItemDiscounts(@PathVariable Long receiptItemId, @PathVariable Long receiptItemDiscountId) {
        return new ResponseEntity<>(receiptService.getReceiptItemDiscountByIdAndReceiptItemId(receiptItemId, receiptItemDiscountId), HttpStatus.OK);
    }
}

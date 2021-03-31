package ru.clevertec.checksystem.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.service.common.IReceiptDiscountService;
import ru.clevertec.checksystem.core.util.factory.ReceiptDiscountDtoFactory;

import java.util.Map;

@RestController
@RequestMapping(value = "receiptDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReceiptDiscountController {

    private final IReceiptDiscountService receiptDiscountService;

    @Autowired
    public ReceiptDiscountController(IReceiptDiscountService receiptDiscountService) {
        this.receiptDiscountService = receiptDiscountService;
    }

    @GetMapping
    ResponseEntity<Page<ReceiptDiscountDto>> get(Pageable pageable) {
        return new ResponseEntity<>(receiptDiscountService.getReceiptDiscountsPage(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<ReceiptDiscountDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(receiptDiscountService.getReceiptDiscountById(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    ResponseEntity<Long> getCount() {
        return new ResponseEntity<>(receiptDiscountService.getReceiptDiscountCount(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        receiptDiscountService.deleteReceiptDiscountById(id);
    }

    @PutMapping
    ResponseEntity<ReceiptDiscountDto> update(@RequestBody Map<String, String> map, ReceiptDiscountDtoFactory receiptDiscountDtoFactory) {
        var dto = receiptDiscountDtoFactory.create(map);
        return new ResponseEntity<>(receiptDiscountService.updateReceiptDiscount(dto), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<ReceiptDiscountDto> create(@RequestBody Map<String, String> map, ReceiptDiscountDtoFactory receiptDiscountDtoFactory) {
        var dto = receiptDiscountDtoFactory.create(map);
        return new ResponseEntity<>(receiptDiscountService.createReceiptDiscount(dto), HttpStatus.CREATED);
    }
}

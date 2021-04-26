package ru.clevertec.checksystem.webapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.service.common.IReceiptDiscountService;
import ru.clevertec.checksystem.core.util.factory.ReceiptDiscountDtoFactory;

import javax.validation.Validator;
import java.util.Map;

@RestController
@RequestMapping(value = "receiptDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReceiptDiscountController {

    private final IReceiptDiscountService receiptDiscountService;
    private final Validator validator;

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
        var receiptDiscountDto = receiptDiscountDtoFactory.create(map, validator);
        return new ResponseEntity<>(receiptDiscountService.updateReceiptDiscount(receiptDiscountDto), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<ReceiptDiscountDto> create(@RequestBody Map<String, String> map, ReceiptDiscountDtoFactory receiptDiscountDtoFactory) {
        var receiptDiscountDto = receiptDiscountDtoFactory.create(map, validator);
        return new ResponseEntity<>(receiptDiscountService.createReceiptDiscount(receiptDiscountDto), HttpStatus.CREATED);
    }
}

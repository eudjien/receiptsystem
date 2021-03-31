package ru.clevertec.checksystem.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.service.ReceiptItemDiscountService;
import ru.clevertec.checksystem.core.util.factory.ReceiptItemDiscountDtoFactory;

import java.util.Map;

@RestController
@RequestMapping(value = "/receiptItemDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReceiptItemDiscountController {

    private final ReceiptItemDiscountService receiptItemDiscountService;

    @Autowired
    public ReceiptItemDiscountController(ReceiptItemDiscountService receiptItemDiscountService) {
        this.receiptItemDiscountService = receiptItemDiscountService;
    }

    @GetMapping
    ResponseEntity<Page<ReceiptItemDiscountDto>> get(Pageable pageable) {
        return new ResponseEntity<>(receiptItemDiscountService.getReceiptItemDiscountsPage(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<ReceiptItemDiscountDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(receiptItemDiscountService.getReceiptItemDiscountById(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    ResponseEntity<Long> getCount() {
        return new ResponseEntity<>(receiptItemDiscountService.getReceiptItemDiscountCount(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable Long id) {
        receiptItemDiscountService.deleteReceiptItemDiscountById(id);
    }

    @PutMapping
    ResponseEntity<ReceiptItemDiscountDto> update(@RequestBody Map<String, String> map, ReceiptItemDiscountDtoFactory receiptItemDiscountDtoFactory) {
        var dto = receiptItemDiscountDtoFactory.create(map);
        return new ResponseEntity<>(receiptItemDiscountService.updateReceiptItemDiscount(dto), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<ReceiptItemDiscountDto> create(@RequestBody Map<String, String> map, ReceiptItemDiscountDtoFactory receiptItemDiscountDtoFactory) {
        var dto = receiptItemDiscountDtoFactory.create(map);
        return new ResponseEntity<>(receiptItemDiscountService.createReceiptItemDiscount(dto), HttpStatus.OK);
    }
}

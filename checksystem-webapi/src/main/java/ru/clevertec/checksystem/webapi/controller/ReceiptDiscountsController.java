package ru.clevertec.checksystem.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checksystem.core.configuration.ApplicationModelMapper;
import ru.clevertec.checksystem.core.dto.ProductDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.repository.ReceiptDiscountRepository;

import java.util.List;

@RestController
public class ReceiptDiscountsController {

    private final ReceiptDiscountRepository receiptDiscountRepository;
    private final ApplicationModelMapper modelMapper;

    @Autowired
    public ReceiptDiscountsController(ReceiptDiscountRepository receiptDiscountRepository, ApplicationModelMapper modelMapper) {
        this.receiptDiscountRepository = receiptDiscountRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/receiptDiscounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReceiptDiscountDto> findById(@PathVariable Long id) {
        var receiptDiscountOptional = receiptDiscountRepository.findById(id);
        return receiptDiscountOptional.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(modelMapper.map(receiptDiscountOptional.get(), ReceiptDiscountDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/receiptDiscounts/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ReceiptDiscountDto[] all() {
        var receiptDiscounts = receiptDiscountRepository.findAll();
        return modelMapper.map(receiptDiscounts, ReceiptDiscountDto[].class);
    }

    @GetMapping(value = "/receiptDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ReceiptDiscountDto> page(Pageable pageable) {
        return receiptDiscountRepository.findAll(pageable).map(receipt -> modelMapper.map(receipt, ReceiptDiscountDto.class));
    }

    @DeleteMapping(value = "/receiptDiscounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReceiptItemDiscountDto> deleteById(@PathVariable Long id) {

        var receiptDiscountOptional = receiptDiscountRepository.findById(id);

        if (receiptDiscountOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        receiptDiscountRepository.delete(receiptDiscountOptional.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/receiptDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDto> deleteAllById(@RequestParam("id") List<Long> ids) {

        var receiptDiscounts = receiptDiscountRepository.findAllById(ids);

        if (!receiptDiscounts.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        receiptDiscountRepository.deleteAll(receiptDiscounts);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

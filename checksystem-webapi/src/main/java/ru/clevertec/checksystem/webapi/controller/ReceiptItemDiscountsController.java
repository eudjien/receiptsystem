package ru.clevertec.checksystem.webapi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checksystem.core.configuration.ApplicationModelMapper;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.repository.ReceiptItemDiscountRepository;

import java.util.List;

@RestController
public class ReceiptItemDiscountsController {

    private final ReceiptItemDiscountRepository receiptItemDiscountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReceiptItemDiscountsController(ReceiptItemDiscountRepository receiptItemDiscountRepository, ApplicationModelMapper modelMapper) {
        this.receiptItemDiscountRepository = receiptItemDiscountRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/receiptItemDiscounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReceiptItemDiscountDto> findById(@PathVariable Long id) {
        var receiptItemDiscountOptional = receiptItemDiscountRepository.findById(id);
        return receiptItemDiscountOptional.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(modelMapper.map(receiptItemDiscountOptional.get(), ReceiptItemDiscountDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/receiptItemDiscounts/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ReceiptItemDiscountDto[] all() {
        var receiptItemDiscounts = receiptItemDiscountRepository.findAll();
        return modelMapper.map(receiptItemDiscounts, ReceiptItemDiscountDto[].class);
    }

    @GetMapping(value = "/receiptItemDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ReceiptItemDiscountDto> page(Pageable pageable) {
        return receiptItemDiscountRepository.findAll(pageable).map(check -> modelMapper.map(check, ReceiptItemDiscountDto.class));
    }

    @DeleteMapping(value = "/receiptItemDiscounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteById(@PathVariable Long id) {

        var receiptItemDiscountOptional = receiptItemDiscountRepository.findById(id);

        if (receiptItemDiscountOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        receiptItemDiscountRepository.delete(receiptItemDiscountOptional.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/receiptItemDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteAllById(@RequestParam("id") List<Long> ids) {

        var receiptDiscounts = receiptItemDiscountRepository.findAllById(ids);

        if (!receiptDiscounts.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        receiptItemDiscountRepository.deleteAll(receiptDiscounts);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

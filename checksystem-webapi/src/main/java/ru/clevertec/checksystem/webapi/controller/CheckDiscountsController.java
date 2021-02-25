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
import ru.clevertec.checksystem.core.dto.discount.check.CheckDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.checkitem.CheckItemDiscountDto;
import ru.clevertec.checksystem.core.repository.CheckDiscountRepository;

import java.util.List;

@RestController
public class CheckDiscountsController {

    private final CheckDiscountRepository checkDiscountRepository;
    private final ApplicationModelMapper modelMapper;

    @Autowired
    public CheckDiscountsController(CheckDiscountRepository checkDiscountRepository, ApplicationModelMapper modelMapper) {
        this.checkDiscountRepository = checkDiscountRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/checkDiscounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CheckDiscountDto> findById(@PathVariable Long id) {
        var checkDiscountOptional = checkDiscountRepository.findById(id);
        return checkDiscountOptional.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(modelMapper.map(checkDiscountOptional.get(), CheckDiscountDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/checkDiscounts/all", produces = MediaType.APPLICATION_JSON_VALUE)
    CheckDiscountDto[] all() {
        var checkDiscounts = checkDiscountRepository.findAll();
        return modelMapper.map(checkDiscounts, CheckDiscountDto[].class);
    }

    @GetMapping(value = "/checkDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<CheckDiscountDto> page(Pageable pageable) {
        return checkDiscountRepository.findAll(pageable).map(check -> modelMapper.map(check, CheckDiscountDto.class));
    }

    @DeleteMapping(value = "/checkDiscounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CheckItemDiscountDto> deleteById(@PathVariable Long id) {

        var checkDiscountOptional = checkDiscountRepository.findById(id);

        if (checkDiscountOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        checkDiscountRepository.delete(checkDiscountOptional.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/checkDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDto> deleteAllById(@RequestParam("id") List<Long> ids) {

        var checkDiscounts = checkDiscountRepository.findAllById(ids);

        if (!checkDiscounts.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        checkDiscountRepository.deleteAll(checkDiscounts);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

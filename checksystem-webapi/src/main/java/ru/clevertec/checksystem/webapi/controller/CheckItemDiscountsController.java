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
import ru.clevertec.checksystem.core.dto.discount.checkitem.CheckItemDiscountDto;
import ru.clevertec.checksystem.core.repository.CheckItemDiscountRepository;

import java.util.List;

@RestController
public class CheckItemDiscountsController {

    private final CheckItemDiscountRepository checkItemDiscountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CheckItemDiscountsController(CheckItemDiscountRepository checkItemDiscountRepository, ApplicationModelMapper modelMapper) {
        this.checkItemDiscountRepository = checkItemDiscountRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/checkItemDiscounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CheckItemDiscountDto> findById(@PathVariable Long id) {
        var checkItemDiscountOptional = checkItemDiscountRepository.findById(id);
        return checkItemDiscountOptional.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(modelMapper.map(checkItemDiscountOptional.get(), CheckItemDiscountDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/checkItemDiscounts/all", produces = MediaType.APPLICATION_JSON_VALUE)
    CheckItemDiscountDto[] all() {
        var checkItemDiscounts = checkItemDiscountRepository.findAll();
        return modelMapper.map(checkItemDiscounts, CheckItemDiscountDto[].class);
    }

    @GetMapping(value = "/checkItemDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<CheckItemDiscountDto> page(Pageable pageable) {
        return checkItemDiscountRepository.findAll(pageable).map(check -> modelMapper.map(check, CheckItemDiscountDto.class));
    }

    @DeleteMapping(value = "/checkItemDiscounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteById(@PathVariable Long id) {

        var checkItemDiscountOptional = checkItemDiscountRepository.findById(id);

        if (checkItemDiscountOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        checkItemDiscountRepository.delete(checkItemDiscountOptional.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/checkItemDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteAllById(@RequestParam("id") List<Long> ids) {

        var checkDiscounts = checkItemDiscountRepository.findAllById(ids);

        if (!checkDiscounts.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        checkItemDiscountRepository.deleteAll(checkDiscounts);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

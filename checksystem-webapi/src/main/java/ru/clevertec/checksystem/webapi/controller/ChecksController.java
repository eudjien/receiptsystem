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
import ru.clevertec.checksystem.core.dto.check.CheckDto;
import ru.clevertec.checksystem.core.repository.CheckItemRepository;
import ru.clevertec.checksystem.core.repository.CheckRepository;
import ru.clevertec.checksystem.core.service.CheckService;

import java.util.List;

@RestController
public class ChecksController {

    private final CheckRepository checkRepository;
    private final CheckItemRepository checkItemRepository;
    private final CheckService checkService;
    private final ModelMapper modelMapper;

    @Autowired
    public ChecksController(
            CheckRepository checkRepository,
            CheckItemRepository checkItemRepository,
            CheckService checkService,
            ApplicationModelMapper modelMapper) {
        this.checkRepository = checkRepository;
        this.checkService = checkService;
        this.checkItemRepository = checkItemRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/checks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CheckDto> findById(@PathVariable Long id) {
        var check = checkRepository.findById(id);
        return check.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(modelMapper.map(check.get(), CheckDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/checks/all", produces = MediaType.APPLICATION_JSON_VALUE)
    CheckDto[] all() {
        return modelMapper.map(checkRepository.findAll(), CheckDto[].class);
    }

    @GetMapping(value = "/checks", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<CheckDto> page(Pageable pageable) {
        return checkRepository.findAll(pageable).map(check -> modelMapper.map(check, CheckDto.class));
    }

    @DeleteMapping(value = "/checks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteById(@PathVariable Long id) {

        var checkOptional = checkRepository.findById(id);

        if (checkOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        checkRepository.delete(checkOptional.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/checks", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteAllById(@RequestParam("id") List<Long> ids) {

        var checks = checkRepository.findAllById(ids);

        if (!checks.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        checkRepository.deleteAll(checks);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

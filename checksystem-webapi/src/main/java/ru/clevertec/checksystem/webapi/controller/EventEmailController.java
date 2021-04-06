package ru.clevertec.checksystem.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checksystem.core.dto.email.EventEmailDto;
import ru.clevertec.checksystem.core.service.common.IEmailService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "eventEmails", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventEmailController {

    private final IEmailService mailService;

    @Autowired
    public EventEmailController(IEmailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping
    ResponseEntity<Page<EventEmailDto>> get(Pageable pageable) {
        return new ResponseEntity<>(mailService.getEventEmailsPage(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<EventEmailDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(mailService.getEventEmailById(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    ResponseEntity<Long> getCount() {
        return new ResponseEntity<>(mailService.getEventEmailCount(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        mailService.deleteEmailById(id);
    }

    @PutMapping
    ResponseEntity<EventEmailDto> update(@RequestBody @Valid EventEmailDto dto) {
        return new ResponseEntity<>(mailService.updateEventEmail(dto), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<EventEmailDto> create(@RequestBody @Valid EventEmailDto dto) {
        return new ResponseEntity<>(mailService.createEventEmail(dto), HttpStatus.CREATED);
    }
}

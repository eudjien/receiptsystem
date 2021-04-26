package ru.clevertec.checksystem.webapi.controller;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class EventEmailController {

    private final IEmailService emailService;

    @GetMapping
    ResponseEntity<Page<EventEmailDto>> get(Pageable pageable) {
        return new ResponseEntity<>(emailService.getEventEmailsPage(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<EventEmailDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(emailService.getEventEmailById(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    ResponseEntity<Long> getCount() {
        return new ResponseEntity<>(emailService.getEventEmailCount(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        emailService.deleteEventEmailById(id);
    }

    @PutMapping
    ResponseEntity<EventEmailDto> update(@RequestBody @Valid EventEmailDto eventEmailDto) {
        return new ResponseEntity<>(emailService.updateEventEmail(eventEmailDto), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<EventEmailDto> create(@RequestBody @Valid EventEmailDto eventEmailDto) {
        return new ResponseEntity<>(emailService.createEventEmail(eventEmailDto), HttpStatus.CREATED);
    }
}

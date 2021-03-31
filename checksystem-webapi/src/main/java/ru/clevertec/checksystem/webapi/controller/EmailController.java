package ru.clevertec.checksystem.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checksystem.core.dto.email.EmailDto;
import ru.clevertec.checksystem.core.dto.email.EventEmailDto;
import ru.clevertec.checksystem.core.service.common.IEmailService;

@RestController
@RequestMapping(value = "/emails", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailController {

    private final IEmailService mailService;

    @Autowired
    public EmailController(IEmailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping
    ResponseEntity<Page<EmailDto>> get(Pageable pageable) {
        return new ResponseEntity<>(mailService.getEmailsPage(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<EmailDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(mailService.getEmailById(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    ResponseEntity<Long> getCount() {
        return new ResponseEntity<>(mailService.getEmailCount(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        mailService.deleteEmailById(id);
    }

    @PutMapping
    ResponseEntity<EmailDto> update(@RequestBody EmailDto dto) {
        return new ResponseEntity<>(mailService.updateEmail(dto), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<EmailDto> create(@RequestBody EmailDto dto) {
        return new ResponseEntity<>(mailService.createEmail(dto), HttpStatus.CREATED);
    }

    @GetMapping("{emailId}/eventEmails")
    Page<EventEmailDto> getEventEmails(Pageable pageable, @PathVariable Long emailId) {
        return mailService.getEventEmailsByEmailId(emailId, pageable);
    }
}

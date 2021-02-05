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
import ru.clevertec.checksystem.core.dto.email.EmailDto;
import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.service.EmailService;

import java.util.List;

@RestController
public class EmailsController {

    private final EmailRepository emailRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmailsController(EmailRepository emailRepository, EmailService emailService, ApplicationModelMapper modelMapper) {
        this.emailRepository = emailRepository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/emails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EmailDto> findById(@PathVariable Long id) {
        var email = emailRepository.findById(id);
        return email.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(modelMapper.map(email.get(), EmailDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/emails/all", produces = MediaType.APPLICATION_JSON_VALUE)
    EmailDto[] all() {
        return modelMapper.map(emailRepository.findAll(), EmailDto[].class);
    }

    @GetMapping(value = "/emails", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<EmailDto> page(Pageable pageable) {
        return emailRepository.findAll(pageable).map(email -> modelMapper.map(email, EmailDto.class));
    }

    @DeleteMapping(value = "/emails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteById(@PathVariable Long id) {

        var emailOptional = emailRepository.findById(id);

        if (emailOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        emailRepository.delete(emailOptional.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/emails", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteAllById(@RequestParam("id") List<Long> ids) {

        var emails = emailRepository.findAllById(ids);

        if (!emails.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        emailRepository.deleteAll(emails);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

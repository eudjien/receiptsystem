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
import ru.clevertec.checksystem.core.dto.email.EventEmailDto;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;
import ru.clevertec.checksystem.core.service.EmailService;

import java.util.List;

@RestController
public class EventEmailsController {

    private final EventEmailRepository eventEmailRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    @Autowired
    public EventEmailsController(EventEmailRepository eventEmailRepository, EmailService emailService, ApplicationModelMapper modelMapper) {
        this.eventEmailRepository = eventEmailRepository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/eventEmails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EventEmailDto> findById(@PathVariable Long id) {
        var eventEmail = eventEmailRepository.findById(id);
        return eventEmail.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(modelMapper.map(eventEmail.get(), EventEmailDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/eventEmails/all", produces = MediaType.APPLICATION_JSON_VALUE)
    EventEmailDto[] all() {
        return modelMapper.map(eventEmailRepository.findAll(), EventEmailDto[].class);
    }

    @GetMapping(value = "/eventEmails", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<EventEmailDto> page(Pageable pageable) {
        return eventEmailRepository.findAll(pageable).map(eventEmail -> modelMapper.map(eventEmail, EventEmailDto.class));
    }

    @DeleteMapping(value = "/eventEmails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteById(@PathVariable Long id) {

        var eventEmailOptional = eventEmailRepository.findById(id);

        if (eventEmailOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        eventEmailRepository.delete(eventEmailOptional.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/eventEmails", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteAllById(@RequestParam("id") List<Long> ids) {

        var eventEmails = eventEmailRepository.findAllById(ids);

        if (!eventEmails.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        eventEmailRepository.deleteAll(eventEmails);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

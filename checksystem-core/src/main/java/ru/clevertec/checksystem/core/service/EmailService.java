package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.service.IEmailService;
import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.exception.EmailNotFoundException;
import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;

@Service
public class EmailService extends EventEmitter<Object> implements IEmailService {

    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    @Autowired
    public EmailService(EmailRepository emailRepository, EventEmailRepository eventEmailRepository) {
        this.emailRepository = emailRepository;
        this.eventEmailRepository = eventEmailRepository;
    }

    public EventEmail assignEmailForEvent(String eventType, String address) {
        var email = emailRepository.findByAddress(address)
                .orElseThrow(() -> new EmailNotFoundException(address));
        return eventEmailRepository.save(new EventEmail(email, eventType));
    }

    public EmailRepository getEmailRepository() {
        return emailRepository;
    }

    public EventEmailRepository getEventEmailRepository() {
        return eventEmailRepository;
    }
}

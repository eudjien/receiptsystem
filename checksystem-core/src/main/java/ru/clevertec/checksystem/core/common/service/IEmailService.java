package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;

public interface IEmailService extends IService {
    EmailRepository getEmailRepository();

    EventEmailRepository getEventEmailRepository();
}

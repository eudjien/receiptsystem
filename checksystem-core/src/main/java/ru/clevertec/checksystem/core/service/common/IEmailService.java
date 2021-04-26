package ru.clevertec.checksystem.core.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.clevertec.checksystem.core.dto.email.EmailDto;
import ru.clevertec.checksystem.core.dto.email.EventEmailDto;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IEmailService {

    void sendEmail(String subject, String body, boolean isHtml, String... addresses) throws MessagingException;

    void sendEmail(String subject, String body, boolean isHtml, Set<File> attachments, String... addresses) throws MessagingException;

    boolean isValidEmailAddress(String emailAddress);

    EmailDto getEmailById(Long id);

    List<EmailDto> getEmails();

    List<EmailDto> getEmails(Sort sort);

    Page<EmailDto> getEmailsPage(Pageable pageable);

    List<EmailDto> getEmailsById(Collection<Long> ids);

    List<EmailDto> getEmailsById(Sort sort, Collection<Long> ids);

    Page<EmailDto> getEmailsPageById(Pageable page, Collection<Long> ids);

    void deleteEmailById(Long id);

    void deleteEventEmailById(Long id);

    Page<EventEmailDto> getEventEmailsByEmailId(Long emailId, Pageable pageable);

    Page<EventEmailDto> getEventEmailsPage(Pageable pageable);

    EventEmailDto getEventEmailById(Long id);

    EmailDto createEmail(EmailDto emailDto);

    EmailDto updateEmail(EmailDto emailDto);

    EventEmailDto updateEventEmail(EventEmailDto eventEmailDto);

    EventEmailDto createEventEmail(EventEmailDto eventEmailDto);

    Long getEmailCount();

    Long getEventEmailCount();
}

package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.dto.email.EmailDto;
import ru.clevertec.checksystem.core.dto.email.EventEmailDto;
import ru.clevertec.checksystem.core.entity.Email;
import ru.clevertec.checksystem.core.entity.EventEmail;
import ru.clevertec.checksystem.core.repository.EmailRepository;
import ru.clevertec.checksystem.core.repository.EventEmailRepository;
import ru.clevertec.checksystem.core.service.common.IEmailService;
import ru.clevertec.checksystem.core.util.mapper.ApplicationModelMapper;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;

    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    private final ApplicationModelMapper modelMapper;

    @Autowired
    public EmailService(
            JavaMailSender mailSender,
            EmailRepository emailRepository,
            EventEmailRepository eventEmailRepository,
            ApplicationModelMapper modelMapper) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
        this.modelMapper = modelMapper;
        this.eventEmailRepository = eventEmailRepository;
    }

    @Override
    public void sendEmail(String subject, String body, boolean isHtml, String... addresses) throws MessagingException {
        sendMail(subject, body, isHtml, null, addresses);
    }

    @Override
    public void sendEmail(String subject, String body, boolean isHtml, Set<File> attachments, String... addresses) throws MessagingException {
        sendMail(subject, body, isHtml, attachments, addresses);
    }

    private void sendMail(String subject, String body, boolean isHtml, Set<File> attachments, String... addresses) throws MessagingException {
        var message = createMessage(subject, body, isHtml, attachments, addresses);
        mailSender.send(message);
    }

    @Override
    public EmailDto getEmailById(Long id) {

        var email = emailRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Email with id '%s' not found", id)));

        return modelMapper.map(email, EmailDto.class);
    }

    @Override
    public EventEmailDto getEventEmailById(Long id) {

        var email = eventEmailRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("EventEmail with id '%s' not found", id)));

        return modelMapper.map(email, EventEmailDto.class);
    }

    @Override
    public List<EmailDto> getEmails() {
        return modelMapper.mapToList(emailRepository.findAll());
    }

    @Override
    public List<EmailDto> getEmails(Sort sort) {
        return modelMapper.mapToList(emailRepository.findAll(sort));
    }

    @Override
    public Page<EmailDto> getEmailsPage(Pageable pageable) {
        return emailRepository.findAll(pageable).map(e -> modelMapper.map(e, EmailDto.class));
    }

    @Override
    public Page<EventEmailDto> getEventEmailsPage(Pageable pageable) {
        return eventEmailRepository.findAll(pageable).map(e -> modelMapper.map(e, EventEmailDto.class));
    }

    @Override
    public List<EmailDto> getEmailsById(Collection<Long> ids) {
        return modelMapper.mapToList(emailRepository.findAllById(ids));
    }

    @Override
    public List<EmailDto> getEmailsById(Sort sort, Collection<Long> ids) {
        return modelMapper.mapToList(emailRepository.findAllByIdIn(sort, ids));
    }

    @Override
    public Page<EmailDto> getEmailsPageById(Pageable pageable, Collection<Long> ids) {
        return emailRepository.findAllByIdIn(pageable, ids).map(e -> modelMapper.map(e, EmailDto.class));
    }

    @Override
    public void deleteEmailById(Long id) {

        var email = emailRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Email with id '%s' not found", id)));

        emailRepository.delete(email);
    }

    @Override
    public EmailDto createEmail(EmailDto dto) {

        if (Objects.isNull(dto))
            throw new NullPointerException("dto");

        return modelMapper.map(emailRepository.save(modelMapper.map(dto, Email.class)), EmailDto.class);
    }

    @Override
    public EmailDto updateEmail(EmailDto dto) {

        if (Objects.isNull(dto))
            throw new NullPointerException("dto");

        emailRepository.findById(dto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Email with id '%s' not found", dto.getId())));

        return modelMapper.map(emailRepository.save(modelMapper.map(dto, Email.class)), EmailDto.class);
    }

    @Override
    public EventEmailDto updateEventEmail(EventEmailDto dto) {

        if (Objects.isNull(dto))
            throw new NullPointerException("dto");

        var eventEmail = eventEmailRepository.findById(dto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("EventEmail with id '%s' not found", dto.getId())));

        if (!emailRepository.existsById(dto.getEmailId()))
            throw new EntityExistsException(String.format("Email with id '%s' not found", dto.getEmailId()));

        if (eventEmailRepository.findByEmailIdAndEventType(dto.getEmailId(), dto.getEventType()).isPresent())
            throw new EntityExistsException(
                    String.format("EventEmail with id '%s' and eventType '%s' already exists",
                            dto.getEmailId(), dto.getEventType()));

        modelMapper.map(dto, eventEmail);

        return modelMapper.map(eventEmailRepository.save(eventEmail), EventEmailDto.class);
    }

    @Override
    public EventEmailDto createEventEmail(EventEmailDto dto) {

        if (Objects.isNull(dto))
            throw new NullPointerException("dto");

        if (!emailRepository.existsById(dto.getEmailId()))
            throw new EntityNotFoundException("Email with id '%s' not found");

        if (eventEmailRepository.findByEmailIdAndEventType(dto.getEmailId(), dto.getEventType()).isPresent())
            throw new EntityExistsException(
                    String.format("EventEmail with id '%s' and eventType '%s' already exists",
                            dto.getEmailId(), dto.getEventType()));

        return modelMapper.map(eventEmailRepository.save(modelMapper.map(dto, EventEmail.class)), EventEmailDto.class);
    }

    @Override
    public Long getEmailCount() {
        return emailRepository.count();
    }

    @Override
    public Long getEventEmailCount() {
        return eventEmailRepository.count();
    }

    @Override
    public Page<EventEmailDto> getEventEmailsByEmailId(Long emailId, Pageable pageable) {
        return eventEmailRepository.findAllByEmailId(emailId, pageable).map(e -> modelMapper.map(e, EventEmailDto.class));
    }

    public boolean isValidEmailAddress(String emailAddress) {
        try {
            new InternetAddress(emailAddress);
            return true;
        } catch (AddressException ignored) {
            return false;
        }
    }

    public MimeMessage createMessage(String subject, String body, boolean isHtml, Set<File> attachments, String... addresses) throws MessagingException {

        var mimeMessageHelper = new MimeMessageHelper(mailSender.createMimeMessage(),
                attachments != null && attachments.size() > 0);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(addresses);
        mimeMessageHelper.setText(body, isHtml);

        if (attachments != null)
            for (var attachment : attachments)
                mimeMessageHelper.addAttachment(attachment.getName(), attachment);

        return mimeMessageHelper.getMimeMessage();
    }
}

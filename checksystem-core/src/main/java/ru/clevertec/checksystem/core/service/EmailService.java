package ru.clevertec.checksystem.core.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;

    private final EmailRepository emailRepository;
    private final EventEmailRepository eventEmailRepository;

    private final ApplicationModelMapper modelMapper;

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
    public void deleteEventEmailById(Long id) {

        var eventEmail = eventEmailRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("EventEmail with id '%s' not found", id)));

        eventEmailRepository.delete(eventEmail);
    }

    @Override
    public EmailDto createEmail(EmailDto emailDto) {

        if (Objects.isNull(emailDto))
            throw new NullPointerException("emailDto");

        return modelMapper.map(emailRepository.save(modelMapper.map(emailDto, Email.class)), EmailDto.class);
    }

    @Override
    public EmailDto updateEmail(EmailDto emailDto) {

        if (Objects.isNull(emailDto))
            throw new NullPointerException("emailDto");

        emailRepository.findById(emailDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Email with id '%s' not found", emailDto.getId())));

        return modelMapper.map(emailRepository.save(modelMapper.map(emailDto, Email.class)), EmailDto.class);
    }

    @Override
    public EventEmailDto updateEventEmail(EventEmailDto eventEmailDto) {

        if (Objects.isNull(eventEmailDto))
            throw new NullPointerException("eventEmailDto");

        var eventEmail = eventEmailRepository.findById(eventEmailDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("EventEmail with id '%s' not found", eventEmailDto.getId())));

        if (!emailRepository.existsById(eventEmailDto.getEmailId()))
            throw new EntityExistsException(String.format("Email with id '%s' not found", eventEmailDto.getEmailId()));

        if (eventEmailRepository.findByEmailIdAndEventType(eventEmailDto.getEmailId(), eventEmailDto.getEventType()).isPresent())
            throw new EntityExistsException(
                    String.format("EventEmail with id '%s' and eventType '%s' already exists",
                            eventEmailDto.getEmailId(), eventEmailDto.getEventType()));

        modelMapper.map(eventEmailDto, eventEmail);

        return modelMapper.map(eventEmailRepository.save(eventEmail), EventEmailDto.class);
    }

    @Override
    public EventEmailDto createEventEmail(EventEmailDto eventEmailDto) {

        if (Objects.isNull(eventEmailDto))
            throw new NullPointerException("eventEmailDto");

        if (!emailRepository.existsById(eventEmailDto.getEmailId()))
            throw new EntityNotFoundException("Email with id '%s' not found");

        if (eventEmailRepository.findByEmailIdAndEventType(eventEmailDto.getEmailId(), eventEmailDto.getEventType()).isPresent())
            throw new EntityExistsException(
                    String.format("EventEmail with id '%s' and eventType '%s' already exists",
                            eventEmailDto.getEmailId(), eventEmailDto.getEventType()));

        return modelMapper.map(eventEmailRepository.save(modelMapper.map(eventEmailDto, EventEmail.class)), EventEmailDto.class);
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

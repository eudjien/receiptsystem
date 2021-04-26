package ru.clevertec.checksystem.webapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.checksystem.core.dto.email.EventEmailDto;
import ru.clevertec.checksystem.core.service.common.IEmailService;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/test_schema.sql", "/test_data.sql"})
public class EmailEventIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IEmailService emailService;

    @Test
    public void getEmailEventById_GoesWell() {
        var emailEventId = 76L;
        var emailEventDto = emailService.getEventEmailById(emailEventId);
        var emailEventDtoResponseEntity = restTemplate.getForEntity(getUrlRoot() + "/eventEmails/" + emailEventId, EventEmailDto.class);
        assertThat(emailEventDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(emailEventDtoResponseEntity.getBody()).isEqualTo(emailEventDto);
    }

    @Test
    public void createEventEmail_GoesWell() {

        var eventEmailDto = EventEmailDto.builder()
                .eventType("CREATED_EVENT_1")
                .emailId(75L)
                .build();

        var emailDtoResponseEntity = restTemplate.postForEntity(
                getUrlRoot() + "/eventEmails", eventEmailDto, EventEmailDto.class);

        assertThat(emailDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(emailDtoResponseEntity.getBody()).isNotNull();
        assertThat(emailDtoResponseEntity.getBody()).matches(a -> a.getEventType().equals(eventEmailDto.getEventType()));
        assertThat(emailDtoResponseEntity.getBody()).matches(a -> a.getEmailId().equals(eventEmailDto.getEmailId()));
    }

    @Test
    public void updateEventEmail_GoesWell() {

        var emailEventId = 914L;

        var eventEmailDto = emailService.getEventEmailById(emailEventId);
        eventEmailDto.setEventType("UPDATED_EVENT_1");

        var emailEventDtoResponseEntity = restTemplate.exchange(
                getUrlRoot() + "/eventEmails", HttpMethod.PUT, new HttpEntity<>(eventEmailDto), EventEmailDto.class);

        assertThat(emailEventDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(emailEventDtoResponseEntity.getBody()).isNotNull();
        assertThat(emailEventDtoResponseEntity.getBody()).matches(a -> a.getId().equals(emailEventId));
        assertThat(emailEventDtoResponseEntity.getBody()).matches(a -> a.getEventType().equals(eventEmailDto.getEventType()));
    }

    @Test
    public void deleteEventEmail_GoesWell() {
        var emailEventId = 914L;
        restTemplate.delete(getUrlRoot() + "/eventEmails/" + emailEventId);
        assertThatThrownBy(() -> emailService.getEventEmailById(emailEventId)).isInstanceOf(EntityNotFoundException.class);
    }

    private String getUrlRoot() {
        return "http://localhost:" + port;
    }
}

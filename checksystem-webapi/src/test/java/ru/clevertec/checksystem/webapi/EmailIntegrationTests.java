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
import ru.clevertec.checksystem.core.dto.email.EmailDto;
import ru.clevertec.checksystem.core.service.common.IEmailService;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/test_schema.sql", "/test_data.sql"})
public class EmailIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IEmailService emailService;

    @Test
    public void getEmailById_GoesWell() {
        var emailId = 75L;
        var emailDto = emailService.getEmailById(emailId);
        var emailDtoResponseEntity = restTemplate.getForEntity(getUrlRoot() + "/emails/" + emailId, EmailDto.class);
        assertThat(emailDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(emailDtoResponseEntity.getBody()).isEqualTo(emailDto);
    }

    @Test
    public void createEmail_GoesWell() {

        var emailDto = EmailDto.builder()
                .address("created@gmail.com")
                .build();

        var emailDtoResponseEntity = restTemplate.postForEntity(
                getUrlRoot() + "/emails", emailDto, EmailDto.class);

        assertThat(emailDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(emailDtoResponseEntity.getBody()).isNotNull();
        assertThat(emailDtoResponseEntity.getBody()).matches(a -> a.getAddress().equals(emailDto.getAddress()));
    }

    @Test
    public void updateEmail_GoesWell() {

        var emailId = 982L;

        var emailDto = emailService.getEmailById(emailId);
        emailDto.setAddress("updated1@gmail.com");

        var emailDtoResponseEntity = restTemplate.exchange(
                getUrlRoot() + "/emails", HttpMethod.PUT, new HttpEntity<>(emailDto), EmailDto.class);

        assertThat(emailDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(emailDtoResponseEntity.getBody()).isNotNull();
        assertThat(emailDtoResponseEntity.getBody()).matches(a -> a.getId().equals(emailId));
        assertThat(emailDtoResponseEntity.getBody()).matches(a -> a.getAddress().equals(emailDto.getAddress()));
    }

    @Test
    public void deleteEmail_GoesWell() {
        var emailId = 982L;
        restTemplate.delete(getUrlRoot() + "/emails/" + emailId);
        assertThatThrownBy(() -> emailService.getEmailById(emailId)).isInstanceOf(EntityNotFoundException.class);
    }

    private String getUrlRoot() {
        return "http://localhost:" + port;
    }
}

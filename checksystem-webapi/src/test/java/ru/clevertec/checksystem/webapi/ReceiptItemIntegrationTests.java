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
import ru.clevertec.checksystem.core.dto.receipt.ReceiptItemDto;
import ru.clevertec.checksystem.core.service.common.IReceiptService;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/test_schema.sql", "/test_data.sql"})
public class ReceiptItemIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IReceiptService receiptService;

    @Test
    public void getReceiptItemById_GoesWell() {
        var receiptItemId = 3L;
        var receiptItemDto = receiptService.getReceiptItemById(receiptItemId);
        var receiptItemDtoResponseEntity = restTemplate.getForEntity(
                getUrlRoot() + "/receiptItems/" + receiptItemId, ReceiptItemDto.class);
        assertThat(receiptItemDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(receiptItemDtoResponseEntity.getBody()).isEqualTo(receiptItemDto);
    }

    @Test
    public void createReceiptItem_GoesWell() {

        var receiptItemDto = ReceiptItemDto.builder()
                .receiptId(1L)
                .productId(29L)
                .quantity(3L)
                .build();

        var receiptItemDtoResponseEntity = restTemplate.postForEntity(getUrlRoot() + "/receiptItems", receiptItemDto, ReceiptItemDto.class);

        assertThat(receiptItemDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(receiptItemDtoResponseEntity.getBody()).matches(a -> a.getReceiptId().equals(receiptItemDto.getReceiptId()));
        assertThat(receiptItemDtoResponseEntity.getBody()).matches(a -> a.getProductId().equals(receiptItemDto.getProductId()));
        assertThat(receiptItemDtoResponseEntity.getBody()).matches(a -> a.getQuantity().equals(receiptItemDto.getQuantity()));
    }

    @Test
    public void updateReceiptItem_GoesWell() {

        var receiptItemId = 3L;

        var receiptItemDto = receiptService.getReceiptItemById(receiptItemId);
        receiptItemDto.setProductId(19L);
        receiptItemDto.setQuantity(557L);

        var receiptItemDtoResponseEntity = restTemplate.exchange(
                getUrlRoot() + "/receiptItems", HttpMethod.PUT, new HttpEntity<>(receiptItemDto), ReceiptItemDto.class);

        assertThat(receiptItemDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(receiptItemDtoResponseEntity.getBody()).isNotNull();
        assertThat(receiptItemDtoResponseEntity.getBody()).matches(a -> a.getId().equals(receiptItemId));
        assertThat(receiptItemDtoResponseEntity.getBody()).matches(a -> a.getQuantity().equals(receiptItemDto.getQuantity()));
        assertThat(receiptItemDtoResponseEntity.getBody()).matches(a -> a.getProductId().equals(receiptItemDto.getProductId()));
    }

    @Test
    public void deleteReceiptItem_GoesWell() {
        var receiptItemId = 3L;
        restTemplate.delete(getUrlRoot() + "/receiptItems/" + receiptItemId);
        assertThatThrownBy(() -> receiptService.getReceiptItemById(receiptItemId)).isInstanceOf(EntityNotFoundException.class);
    }

    private String getUrlRoot() {
        return "http://localhost:" + port;
    }
}

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
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.dto.discount.receipt.PercentageReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.service.common.IReceiptDiscountService;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/test_schema.sql", "/test_data.sql"})
public class ReceiptDiscountIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IReceiptDiscountService receiptDiscountService;

    @Test
    public void getReceiptDiscountById_GoesWell() {
        var receiptDiscountId = 68L;
        var receiptDiscountDto = receiptDiscountService.getReceiptDiscountById(receiptDiscountId);
        var receiptDiscountDtoResponseEntity = restTemplate.getForEntity(
                getUrlRoot() + "/receiptDiscounts/" + receiptDiscountId, ReceiptDiscountDto.class);
        assertThat(receiptDiscountDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(receiptDiscountDtoResponseEntity.getBody()).isEqualTo(receiptDiscountDto);
    }

    @Test
    public void createReceiptDiscount_GoesWell() {

        var map = new HashMap<String, String>();
        map.put("percent", "3");
        map.put("description", "-3%");
        map.put("type", Entities.DiscriminatorValues.PERCENTAGE_RECEIPT_DISCOUNT);

        var receiptDiscountDtoResponseEntity = restTemplate.postForEntity(getUrlRoot() + "/receiptDiscounts", map, ReceiptDiscountDto.class);

        assertThat(receiptDiscountDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void updateReceiptDiscount_GoesWell() {

        var receiptDiscountId = 67L;

        var receiptDiscountDto = (PercentageReceiptDiscountDto) receiptDiscountService.getReceiptDiscountById(receiptDiscountId);

        var map = new HashMap<String, String>();
        map.put("id", String.valueOf(receiptDiscountId));
        map.put("type", Entities.DiscriminatorValues.PERCENTAGE_RECEIPT_DISCOUNT);
        map.put("percent", "3");
        map.put("description", "-3%");
        if (receiptDiscountDto.getDependentDiscountId() != null) {
            map.put("setDependentDiscountId", String.valueOf(receiptDiscountDto.getDependentDiscountId()));
        }

        var discountDtoResponseEntity = restTemplate.exchange(
                getUrlRoot() + "/receiptDiscounts", HttpMethod.PUT, new HttpEntity<>(map), ReceiptDiscountDto.class);

        assertThat(discountDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(discountDtoResponseEntity.getBody()).isNotNull();
        assertThat((PercentageReceiptDiscountDto) discountDtoResponseEntity.getBody()).matches(a -> a.getPercent() == 3D);
        assertThat((PercentageReceiptDiscountDto) discountDtoResponseEntity.getBody()).matches(a -> a.getDescription().equals("-3%"));
    }

    @Test
    public void deleteReceiptDiscount_GoesWell() {
        var receiptDiscountId = 555L;
        restTemplate.delete(getUrlRoot() + "/receiptDiscounts/" + receiptDiscountId);
        assertThatThrownBy(() -> receiptDiscountService.getReceiptDiscountById(receiptDiscountId)).isInstanceOf(EntityNotFoundException.class);
    }

    private String getUrlRoot() {
        return "http://localhost:" + port;
    }
}

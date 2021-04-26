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
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ConstantReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.service.common.IReceiptItemDiscountService;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/test_schema.sql", "/test_data.sql"})
public class ReceiptItemDiscountIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IReceiptItemDiscountService receiptItemDiscountService;

    @Test
    public void getReceiptItemDiscountById_GoesWell() {
        var receiptDiscountId = 11L;
        var receiptItemDiscountDto = receiptItemDiscountService.getReceiptItemDiscountById(receiptDiscountId);
        var receiptItemDiscountDtoResponseEntity = restTemplate.getForEntity(
                getUrlRoot() + "/receiptItemDiscounts/" + receiptDiscountId, ReceiptItemDiscountDto.class);
        assertThat(receiptItemDiscountDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(receiptItemDiscountDtoResponseEntity.getBody()).isEqualTo(receiptItemDiscountDto);
    }

    @Test
    public void createReceiptItemDiscount_GoesWell() {

        var map = new HashMap<String, String>();
        map.put("percent", "3");
        map.put("description", "-3%");
        map.put("type", Entities.DiscriminatorValues.PERCENTAGE_RECEIPT_ITEM_DISCOUNT);

        var receiptItemDtoResponseEntity = restTemplate.postForEntity(getUrlRoot() + "/receiptItemDiscounts", map, ReceiptItemDiscountDto.class);

        assertThat(receiptItemDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void updateReceiptItemDiscount_GoesWell() {

        var receiptItemDiscountId = 50L;

        var receiptItemDiscountDto = (ConstantReceiptItemDiscountDto) receiptItemDiscountService.getReceiptItemDiscountById(receiptItemDiscountId);

        var map = new HashMap<String, String>();
        map.put("id", String.valueOf(receiptItemDiscountId));
        map.put("type", Entities.DiscriminatorValues.CONSTANT_RECEIPT_ITEM_DISCOUNT);
        map.put("constant", "20");
        map.put("description", "-20$");
        if (receiptItemDiscountDto.getDependentDiscountId() != null) {
            map.put("setDependentDiscountId", String.valueOf(receiptItemDiscountDto.getDependentDiscountId()));
        }

        var discountDtoResponseEntity = restTemplate.exchange(
                getUrlRoot() + "/receiptItemDiscounts", HttpMethod.PUT, new HttpEntity<>(map), ReceiptItemDiscountDto.class);

        assertThat(discountDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(discountDtoResponseEntity.getBody()).isNotNull();
        assertThat((ConstantReceiptItemDiscountDto) discountDtoResponseEntity.getBody()).matches(a -> a.getConstant().compareTo(BigDecimal.valueOf(20)) == 0);
        assertThat((ConstantReceiptItemDiscountDto) discountDtoResponseEntity.getBody()).matches(a -> a.getDescription().equals("-20$"));
    }

    @Test
    public void deleteReceiptItemDiscount_GoesWell() {
        var receiptItemDiscountId = 73L;
        restTemplate.delete(getUrlRoot() + "/receiptItemDiscounts/" + receiptItemDiscountId);
        assertThatThrownBy(() -> receiptItemDiscountService.getReceiptItemDiscountById(receiptItemDiscountId)).isInstanceOf(EntityNotFoundException.class);
    }

    private String getUrlRoot() {
        return "http://localhost:" + port;
    }
}

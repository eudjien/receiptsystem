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
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.receipt.ReceiptDto;
import ru.clevertec.checksystem.core.dto.receipt.ReceiptItemDto;
import ru.clevertec.checksystem.core.service.common.IReceiptService;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/test_schema.sql", "/test_data.sql"})
public class ReceiptIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IReceiptService receiptService;

    @Test
    public void getReceiptById_GoesWell() {
        var receiptId = 1L;
        var receiptDto = receiptService.getReceiptById(receiptId);
        var receiptDtoResponseEntity = restTemplate.getForEntity(getUrlRoot() + "/receipts/" + receiptId, ReceiptDto.class);
        assertThat(receiptDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(receiptDtoResponseEntity.getBody()).isEqualTo(receiptDto);
    }

    @Test
    public void createReceipt_GoesWell() throws ParseException {

        var dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

        var receiptDto = ReceiptDto.builder()
                .name("Test Receipt 1")
                .address("Test Address 1")
                .phoneNumber("+375290000001")
                .cashier("Test Cashier 1")
                .description("Test Description 1")
                .date(dateFormatter.parse("01.01.2018"))
                .receiptItem(ReceiptItemDto.builder().productId(4L).quantity(3L).build())
                .receiptItem(ReceiptItemDto.builder().productId(6L).quantity(1L).build())
                .receiptItem(ReceiptItemDto.builder().productId(8L).quantity(2L).discount(ReceiptItemDiscountDto.builder().id(23L).build()).build())
                .receiptItem(ReceiptItemDto.builder().productId(10L).quantity(1L).build())
                .receiptItem(ReceiptItemDto.builder().productId(13L).quantity(1L).build())
                .receiptItem(ReceiptItemDto.builder().productId(15L).quantity(4L).build())
                .discount(ReceiptDiscountDto.builder().id(2L).build())
                .discount(ReceiptDiscountDto.builder().id(17L).build())
                .build();

        var receiptDtoResponseEntity = restTemplate.postForEntity(getUrlRoot() + "/receipts", receiptDto, ReceiptDto.class);

        assertThat(receiptDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void updateReceipt_GoesWell() {

        var receiptId = 1L;

        var receiptDto = receiptService.getReceiptById(receiptId);
        receiptDto.setName("Updated Receipt Name 1");
        receiptDto.setAddress("Updated Receipt Address 1");

        var removedReceiptItem = receiptDto.getReceiptItems().stream().filter(a -> a.getId().equals(3L)).findFirst().orElseThrow();
        receiptDto.getReceiptItems().remove(removedReceiptItem);

        var updatedReceiptItem = receiptDto.getReceiptItems().stream().filter(a -> a.getId().equals(9L)).findFirst().orElseThrow();
        var updateReceiptItemDiscount1 = ReceiptItemDiscountDto.builder().id(25L).build();
        var updateReceiptItemDiscount2 = ReceiptItemDiscountDto.builder().id(32L).build();
        updatedReceiptItem.setQuantity(987L);
        updatedReceiptItem.getDiscounts().clear();
        updatedReceiptItem.getDiscounts().add(updateReceiptItemDiscount1);
        updatedReceiptItem.getDiscounts().add(updateReceiptItemDiscount2);

        var newReceiptDiscount = ReceiptDiscountDto.builder().id(68L).build();
        receiptDto.getDiscounts().add(newReceiptDiscount);

        var receiptDtoResponseEntity = restTemplate.exchange(
                getUrlRoot() + "/receipts", HttpMethod.PUT, new HttpEntity<>(receiptDto), ReceiptDto.class);

        assertThat(receiptDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(receiptDtoResponseEntity.getBody()).isNotNull();
        assertThat(receiptDtoResponseEntity.getBody().getName()).isEqualTo(receiptDto.getName());
        assertThat(receiptDtoResponseEntity.getBody().getAddress()).isEqualTo(receiptDto.getAddress());
        assertThat(receiptDtoResponseEntity.getBody().getReceiptItems().stream()
                .filter(a -> a.getId().equals(removedReceiptItem.getId())).findFirst().orElse(null))
                .isNull();
        assertThat(receiptDtoResponseEntity.getBody().getReceiptItems().stream()
                .filter(a -> a.getId().equals(updatedReceiptItem.getId())).findFirst().orElse(null))
                .isNotNull()
                .matches(a -> a.getQuantity().equals(updatedReceiptItem.getQuantity()));
        assertThat(receiptDtoResponseEntity.getBody().getReceiptItems().stream()
                .filter(a -> a.getId().equals(updatedReceiptItem.getId())).findFirst().orElse(null))
                .isNotNull()
                .matches(a -> a.getDiscounts().size() == updatedReceiptItem.getDiscounts().size());
        assertThat(receiptDtoResponseEntity.getBody().getReceiptItems().stream()
                .filter(a -> a.getId().equals(updatedReceiptItem.getId())).findFirst().orElseThrow().getDiscounts().stream()
                .filter(d -> d.getId().equals(updateReceiptItemDiscount1.getId())).findFirst().orElse(null))
                .isNotNull();
        assertThat(receiptDtoResponseEntity.getBody().getReceiptItems().stream()
                .filter(a -> a.getId().equals(updatedReceiptItem.getId())).findFirst().orElseThrow().getDiscounts().stream()
                .filter(d -> d.getId().equals(updateReceiptItemDiscount2.getId())).findFirst().orElse(null))
                .isNotNull();
        assertThat(receiptDtoResponseEntity.getBody().getDiscounts().stream()
                .filter(a -> a.getId().equals(newReceiptDiscount.getId())).findFirst().orElse(null))
                .isNotNull();
    }

    @Test
    public void deleteReceipt_GoesWell() {
        var receiptId = 1L;
        restTemplate.delete(getUrlRoot() + "/receipts/" + receiptId);
        assertThatThrownBy(() -> receiptService.getReceiptById(receiptId)).isInstanceOf(EntityNotFoundException.class);
    }

    private String getUrlRoot() {
        return "http://localhost:" + port;
    }
}

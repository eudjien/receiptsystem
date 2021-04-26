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
import ru.clevertec.checksystem.core.dto.ProductDto;
import ru.clevertec.checksystem.core.service.common.IProductService;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/test_schema.sql", "/test_data.sql"})
public class ProductIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IProductService productService;

    @Test
    public void getProductById_GoesWell() {
        var productId = 4L;
        var productDto = productService.getProductById(productId);
        var productDtoResponseEntity = restTemplate.getForEntity(
                getUrlRoot() + "/products/" + productId, ProductDto.class);
        assertThat(productDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productDtoResponseEntity.getBody()).isEqualTo(productDto);
    }

    @Test
    public void createProduct_GoesWell() {

        var productDto = ProductDto.builder()
                .name("Created Product 1")
                .price(BigDecimal.valueOf(43))
                .build();

        var productDtoResponseEntity = restTemplate.postForEntity(
                getUrlRoot() + "/products", productDto, ProductDto.class);

        assertThat(productDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(productDtoResponseEntity.getBody()).isNotNull();
        assertThat(productDtoResponseEntity.getBody()).matches(a -> a.getName().equals(productDto.getName()));
        assertThat(productDtoResponseEntity.getBody()).matches(a -> a.getPrice().equals(productDto.getPrice()));
    }

    @Test
    public void updateProduct_GoesWell() {

        var productId = 4L;

        var productDto = productService.getProductById(productId);
        productDto.setName("Updated Name 1");
        productDto.setPrice(BigDecimal.valueOf(78));

        var productDtoResponseEntity = restTemplate.exchange(
                getUrlRoot() + "/products", HttpMethod.PUT, new HttpEntity<>(productDto), ProductDto.class);

        assertThat(productDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productDtoResponseEntity.getBody()).isNotNull();
        assertThat(productDtoResponseEntity.getBody()).matches(a -> a.getId().equals(productId));
        assertThat(productDtoResponseEntity.getBody()).matches(a -> a.getName().equals(productDto.getName()));
        assertThat(productDtoResponseEntity.getBody()).matches(a -> a.getPrice().equals(productDto.getPrice()));
    }

    @Test
    public void deleteProduct_GoesWell() {
        var productId = 701L;
        restTemplate.delete(getUrlRoot() + "/products/" + productId);
        assertThatThrownBy(() -> productService.getProductById(productId)).isInstanceOf(EntityNotFoundException.class);
    }

    private String getUrlRoot() {
        return "http://localhost:" + port;
    }
}

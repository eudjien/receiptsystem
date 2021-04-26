package ru.clevertec.checksystem.core.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.clevertec.checksystem.core.dto.ProductDto;

import java.util.Collection;
import java.util.List;

public interface IProductService {

    ProductDto getProductById(Long id);

    List<ProductDto> getProducts();

    List<ProductDto> getProducts(Sort sort);

    Page<ProductDto> getProductsPage(Pageable pageable);

    List<ProductDto> getProductsById(Collection<Long> ids);

    List<ProductDto> getProductsById(Sort sort, Collection<Long> ids);

    Page<ProductDto> getProductsPageById(Pageable page, Collection<Long> ids);

    void deleteProductById(Long id);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    Long getProductCount();
}

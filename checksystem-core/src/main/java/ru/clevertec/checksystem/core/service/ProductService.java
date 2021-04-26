package ru.clevertec.checksystem.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.dto.ProductDto;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.repository.ProductRepository;
import ru.clevertec.checksystem.core.service.common.IProductService;
import ru.clevertec.checksystem.core.util.mapper.ApplicationModelMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ApplicationModelMapper modelMapper;

    @Override
    public ProductDto getProductById(Long id) {

        var product = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Product with id '%s' not found", id)));

        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> getProducts() {
        return modelMapper.mapToList(productRepository.findAll());
    }

    @Override
    public List<ProductDto> getProducts(Sort sort) {
        return modelMapper.mapToList(productRepository.findAll(sort));
    }

    @Override
    public Page<ProductDto> getProductsPage(Pageable pageable) {
        return productRepository.findAll(pageable).map(e -> modelMapper.map(e, ProductDto.class));
    }

    @Override
    public List<ProductDto> getProductsById(Collection<Long> ids) {
        return modelMapper.mapToList(productRepository.findAllById(ids));
    }

    @Override
    public List<ProductDto> getProductsById(Sort sort, Collection<Long> ids) {
        return modelMapper.mapToList(productRepository.findAllByIdIn(sort, ids));
    }

    @Override
    public Page<ProductDto> getProductsPageById(Pageable pageable, Collection<Long> ids) {
        return productRepository.findAllByIdIn(pageable, ids).map(e -> modelMapper.map(e, ProductDto.class));
    }

    @Override
    public void deleteProductById(Long id) {

        if (Objects.isNull(id))
            throw new NullPointerException("id");

        var product = productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Product with id '%s' not found", id)));

        productRepository.delete(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        if (Objects.isNull(productDto))
            throw new NullPointerException("productDto");

        return modelMapper.map(productRepository.save(modelMapper.map(productDto, Product.class)), ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {

        if (Objects.isNull(productDto))
            throw new NullPointerException("productDto");

        productRepository.findById(productDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Product with id '%s' not found", productDto.getId())));

        return modelMapper.map(productRepository.save(modelMapper.map(productDto, Product.class)), ProductDto.class);
    }

    @Override
    public Long getProductCount() {
        return productRepository.count();
    }
}

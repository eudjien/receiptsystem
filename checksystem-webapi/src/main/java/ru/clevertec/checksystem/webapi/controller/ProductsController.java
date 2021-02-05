package ru.clevertec.checksystem.webapi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checksystem.core.configuration.ApplicationModelMapper;
import ru.clevertec.checksystem.core.dto.ProductDto;
import ru.clevertec.checksystem.core.repository.ProductRepository;
import ru.clevertec.checksystem.core.service.ProductService;

import java.util.List;

@RestController
public class ProductsController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductsController(ProductRepository productRepository,
                              ProductService productService, ApplicationModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        var product = productRepository.findById(id);
        return product.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(modelMapper.map(product.get(), ProductDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/products/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDto[] all() {
        return modelMapper.map(productRepository.findAll(), ProductDto[].class);
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ProductDto> page(Pageable pageable) {
        return productRepository.findAll(pageable).map(product -> modelMapper.map(product, ProductDto.class));
    }

    @DeleteMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteById(@PathVariable Long id) {

        var productOptional = productRepository.findById(id);

        if (productOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        productRepository.delete(productOptional.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteAllById(@RequestParam("id") List<Long> ids) {

        var products = productRepository.findAllById(ids);

        if (!products.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        productRepository.deleteAll(products);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

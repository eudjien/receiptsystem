package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.service.IProductService;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.repository.ProductRepository;

@Service
public class ProductService extends EventEmitter<Object> implements IProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }
}

package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.repository.ProductRepository;

public interface IProductService extends IService {
    ProductRepository getProductRepository();
}

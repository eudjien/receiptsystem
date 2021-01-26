package ru.clevertec.checksystem.core.common.builder;

import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.common.IBuildable;

import java.math.BigDecimal;

public interface IProductBuilder extends IBuildable<Product> {

    IProductBuilder setId(int id);

    IProductBuilder setName(String name);

    IProductBuilder setPrice(BigDecimal price);
}

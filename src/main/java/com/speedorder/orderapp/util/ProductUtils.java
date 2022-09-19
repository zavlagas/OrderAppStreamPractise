package com.speedorder.orderapp.util;


import com.speedorder.orderapp.entity.Product;
import com.speedorder.orderapp.exception.custom.DiscountException;

import java.util.function.Function;

public interface ProductUtils {

    static Function<Product, Product> discount(Integer percentage) {
        if (percentage > 0 && percentage <= 100) {
            return product -> {
                product.setPrice(product.getPrice() * ((100.0 - percentage) / 100));
                return product;
            };
        }
        throw new DiscountException("You cant discount less than 0 and more tha 100");
    }
}

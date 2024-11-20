package com.shatakshee.yummyrest.mapper;

import com.shatakshee.yummyrest.dto.ProductRequest;
import com.shatakshee.yummyrest.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .build();
    }


}
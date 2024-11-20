package com.shatakshee.yummyrest.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.shatakshee.yummyrest.dto.ProductRequest;
import com.shatakshee.yummyrest.entity.Product;
import com.shatakshee.yummyrest.exception.CustomerNotFoundException;
import com.shatakshee.yummyrest.mapper.ProductMapper;
import com.shatakshee.yummyrest.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo prepo;
    private final ProductMapper pmapper;

    public String createProduct(ProductRequest request) {

        Product p = pmapper.toEntity(request);
        prepo.save(p);
        return "Created";
    }

    public List<Product> getTop2ProductsInPriceRange(double minPrice, double maxPrice) {
        return prepo.findTop2ByPriceRange(minPrice, maxPrice);
    }

    public String updateProduct(JsonNode requestBody) {
        String name = requestBody.get("name").asText();
        Product existing = prepo.findByName(name);

        if (existing == null) {
            throw new CustomerNotFoundException("Product not found");
        }



        // Update fields if they exist in the request
        if (requestBody.has("price")) {
            existing.setPrice(requestBody.get("price").asDouble());
        }

        prepo.save(existing);
        return "Product updated successfully";
    }

    public void deleteProduct(String name) {
        Product existing = prepo.findByName(name);
        //logger.debug("Loading user details for deletion: {}", existingCustomer);
        if (existing == null) {
            throw new CustomerNotFoundException("Product not found");
        }


        prepo.delete(existing);
    }
}

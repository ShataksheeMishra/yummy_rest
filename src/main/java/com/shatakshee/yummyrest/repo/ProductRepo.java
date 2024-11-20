package com.shatakshee.yummyrest.repo;


import com.shatakshee.yummyrest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long> {

    @Query(value = "SELECT * FROM Product WHERE price BETWEEN :minPrice AND :maxPrice ORDER BY price DESC LIMIT 2", nativeQuery = true)
    List<Product> findTop2ByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    Product findByName(String name);

}

package com.shatakshee.yummyrest.repo;

import com.shatakshee.yummyrest.entity.Customer;
//import com.shatakshee.yummyrest.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
//    @Query("select u from Product u where u.price between ?1 and ?2 order by u.price limit 2")
//    public List<Product> findProductByPrice(int start, int end);
}


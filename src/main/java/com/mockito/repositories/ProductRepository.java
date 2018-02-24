package com.mockito.repositories;

import com.mockito.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Vetty on 2/19/18.
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findById(Long id);


}

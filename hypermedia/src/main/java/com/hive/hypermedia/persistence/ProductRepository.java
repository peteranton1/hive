package com.hive.hypermedia.persistence;

import com.hive.hypermedia.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findByProductcode(final String productcode);
}

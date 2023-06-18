package com.onebox.pruebatecnica.persistence.repo;

import com.onebox.pruebatecnica.persistence.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}

package com.onebox.pruebatecnica.service;

import com.onebox.pruebatecnica.persistence.entity.Product;
import com.onebox.pruebatecnica.persistence.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {

        final List<Product> products = new ArrayList<>();

        productRepository.findAll().forEach(products::add);

        return products;

    }

    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() ->
                new Exception("Product not found!"));
    }

    public Product saveOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }


}

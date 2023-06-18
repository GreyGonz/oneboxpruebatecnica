package com.onebox.pruebatecnica.controller;

import com.onebox.pruebatecnica.persistence.entity.Product;
import com.onebox.pruebatecnica.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/data", produces = "application/json")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") long id) {

        ResponseEntity<Product> response;

        try {
            Product product = service.getProductById(id);
            response = new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;

    }

    @PostMapping("/product")
    public ResponseEntity<Product> saveOrUpdateProduct(@RequestBody Product product) {
        return new ResponseEntity<>(service.saveOrUpdateProduct(product), HttpStatus.OK);
    }

    @DeleteMapping("/products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        service.deleteAllProducts();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable("id") long id) {
        service.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

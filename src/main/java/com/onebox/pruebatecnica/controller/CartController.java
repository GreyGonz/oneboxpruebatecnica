package com.onebox.pruebatecnica.controller;

import com.onebox.pruebatecnica.persistence.entity.Cart;
import com.onebox.pruebatecnica.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/data", produces = "application/json")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private CartService service;

    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> findAll() {
        return new ResponseEntity<>(service.getAllCarts(), HttpStatus.OK);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<Cart> findById(@PathVariable("id") long id) {

        ResponseEntity<Cart> response;

        try {
            Cart cart = service.getCartById(id);
            response = new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;

    }

    @PostMapping("/cart")
    public ResponseEntity<Cart> createCart() {
        return new ResponseEntity<>(service.createCart(), HttpStatus.OK);
    }

    @DeleteMapping("/carts")
    public ResponseEntity<HttpStatus> deleteAllCarts() {
        service.deleteAllCarts();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<HttpStatus> deleteCartById(@PathVariable("id") long id) {
        service.deleteCartById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

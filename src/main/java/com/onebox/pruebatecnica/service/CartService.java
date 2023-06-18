package com.onebox.pruebatecnica.service;

import com.onebox.pruebatecnica.persistence.entity.Cart;
import com.onebox.pruebatecnica.persistence.repo.CartRepository;
import com.onebox.pruebatecnica.persistence.repo.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Cart> getAllCarts() {

        final List<Cart> carts = new ArrayList<>();

        cartRepository.findAll().forEach(carts::add);

        return carts;

    }

    public Cart getCartById(long id) throws Exception {
        return cartRepository.findById(id).orElseThrow(() -> new Exception("Cart not found!"));
    }


    public Cart createCart() {
        return cartRepository.save(Cart.builder().build());
    }

    public void deleteAllCarts() {
        cartRepository.deleteAll();
    }

    public void deleteCartById(long id) {
        cartRepository.deleteById(id);
    }


}

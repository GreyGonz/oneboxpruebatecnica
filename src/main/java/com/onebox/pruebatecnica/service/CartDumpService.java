package com.onebox.pruebatecnica.service;

import com.onebox.pruebatecnica.persistence.entity.Cart;
import com.onebox.pruebatecnica.persistence.repo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDumpService {

    private static final int FIXED_RATE = 30000;
    private static final int TIMEOUT = 1;
    @Autowired
    private CartRepository cartRepository;

    @Scheduled(fixedRate = FIXED_RATE)
    public void dumpInactiveCarts() {
        final List<Cart> carts = cartRepository.findInactiveCarts(TIMEOUT);
        cartRepository.deleteAll(carts);
    }

}

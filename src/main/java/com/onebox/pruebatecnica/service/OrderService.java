package com.onebox.pruebatecnica.service;

import com.onebox.pruebatecnica.persistence.dto.OrderDTO;
import com.onebox.pruebatecnica.persistence.entity.Cart;
import com.onebox.pruebatecnica.persistence.entity.Order;
import com.onebox.pruebatecnica.persistence.entity.OrderKey;
import com.onebox.pruebatecnica.persistence.entity.Product;
import com.onebox.pruebatecnica.persistence.repo.OrderRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    public List<Order> getAllOrders() {

        final List<Order> orders = new ArrayList<>();

        orderRepository.findAll().forEach(orders::add);

        return orders;

    }

    public Order getOrderById(OrderKey id) throws Exception {
        return orderRepository.findById(id).orElseThrow(() ->
                new Exception("Order not found!"));
    }

    @Transactional
    public Order saveOrUpdateOrder(@NotNull OrderDTO orderDTO) throws Exception {

        final Cart cart = cartService.getCartById(orderDTO.getCartId());
        final Product product = productService.getProductById(orderDTO.getProductId());

        cart.setUpdatedAt(ZonedDateTime.now());

        return orderRepository.save(
                Order.builder()
                        .id(new OrderKey(orderDTO.getCartId(), orderDTO.getProductId()))
                        .cart(cart)
                        .product(product)
                        .amount(orderDTO.getAmount())
                        .build()
        );

    }

    @Transactional
    public void deleteOrderById(OrderKey id) throws Exception {
        final Order order = orderRepository.findById(id).orElseThrow(() ->
                new Exception("Order not found!"));

        order.getCart().setUpdatedAt(ZonedDateTime.now());

        orderRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllOrders() {
        cartService.getAllCarts().forEach(cart -> cart.setUpdatedAt(ZonedDateTime.now()));
        orderRepository.deleteAll();
    }

}

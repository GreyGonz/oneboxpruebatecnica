package com.onebox.pruebatecnica.controller;

import com.onebox.pruebatecnica.persistence.dto.OrderDTO;
import com.onebox.pruebatecnica.persistence.entity.OrderKey;
import com.onebox.pruebatecnica.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/data", produces = "application/json")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> findAllOrders() {

        final List<OrderDTO> orders = new ArrayList<>();

        service.getAllOrders().forEach(order ->  orders.add(new OrderDTO(order)));

        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @GetMapping("/order/{cartId}/{productId}")
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable("cartId") long cartId,
                                                  @PathVariable("productId") long productId) {
        ResponseEntity<OrderDTO> response;

        try {
            final OrderKey id = new OrderKey(cartId, productId);
            final OrderDTO orderDTO = new OrderDTO(service.getOrderById(id));
            response = new ResponseEntity<>(orderDTO, HttpStatus.OK);
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;

    }

    @PostMapping("/order")
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTO orderDTO) {

        ResponseEntity<OrderDTO> response;

        try {
            response = new ResponseEntity<>(new OrderDTO(service.saveOrUpdateOrder(orderDTO)), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


    @DeleteMapping("/orders")
    public ResponseEntity<HttpStatus> deleteAll() {
        service.deleteAllOrders();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/order/{cartId}/{productId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("cartId") long cartId,
                                        @PathVariable("productId") long productId) {

        ResponseEntity<HttpStatus> response;

        try {
            final OrderKey id = new OrderKey(cartId, productId);

            service.deleteOrderById(id);

            response =  new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;

    }

}

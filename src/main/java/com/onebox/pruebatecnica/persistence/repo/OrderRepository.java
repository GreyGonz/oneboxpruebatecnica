package com.onebox.pruebatecnica.persistence.repo;

import com.onebox.pruebatecnica.persistence.entity.Order;
import com.onebox.pruebatecnica.persistence.entity.OrderKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, OrderKey> {

    List<Order> findByIdCartId(long id);

    List<Order> findByIdProductId(long id);

}

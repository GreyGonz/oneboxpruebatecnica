package com.onebox.pruebatecnica.persistence.repo;

import com.onebox.pruebatecnica.persistence.entity.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Long> {

    @Query("select c from Cart c where c.updatedAt <= DATEADD(MINUTE, -:timeOut, NOW())")
    List<Cart> findInactiveCarts(@Param("timeOut") int timeOut);

}

package com.onebox.pruebatecnica.persistence.dto;

import com.onebox.pruebatecnica.persistence.entity.Order;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private long cartId;

    private long productId;

    private int amount;

    public OrderDTO(Order order) {
        this.cartId = order.getCart().getId();
        this.productId = order.getProduct().getId();
        this.amount = order.getAmount();
    }

}

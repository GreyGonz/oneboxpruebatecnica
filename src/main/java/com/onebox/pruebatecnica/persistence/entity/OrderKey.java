package com.onebox.pruebatecnica.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Embeddable
public class OrderKey implements Serializable {

    private Long cartId;

    private Long productId;

}

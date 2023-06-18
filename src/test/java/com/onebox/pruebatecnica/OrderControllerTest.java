package com.onebox.pruebatecnica;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebox.pruebatecnica.persistence.dto.OrderDTO;
import com.onebox.pruebatecnica.persistence.entity.Cart;
import com.onebox.pruebatecnica.persistence.entity.Order;
import com.onebox.pruebatecnica.persistence.entity.OrderKey;
import com.onebox.pruebatecnica.persistence.entity.Product;
import com.onebox.pruebatecnica.persistence.repo.CartRepository;
import com.onebox.pruebatecnica.persistence.repo.OrderRepository;
import com.onebox.pruebatecnica.persistence.repo.ProductRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.*;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        orderRepository.deleteAll();
        cartRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void canListAllOrders() throws Exception {

        Order order1 = createDummyOrder();
        Order order2 = createDummyOrder();

        mockMvc.perform(get("/api/data/orders"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].cartId", is(order1.getId().getCartId()), Long.class))
                .andExpect(jsonPath("$.[0].productId", is(order1.getId().getProductId()), Long.class))
                .andExpect(jsonPath("$.[1].cartId", is(order2.getId().getCartId()), Long.class))
                .andExpect(jsonPath("$.[1].productId", is(order2.getId().getProductId()), Long.class));

    }

    @Test
    public void canGetOrderById() throws Exception {

        Order order = createDummyOrder();

        mockMvc.perform(get("/api/data/order/"
                        + order.getId().getCartId() + "/"
                        + order.getId().getProductId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.cartId", is(order.getCart().getId()), Long.class))
                .andExpect(jsonPath("$.productId", is(order.getProduct().getId()), Long.class))
                .andExpect(jsonPath("$.amount", is(order.getAmount())));

    }

    @Test
    public void canUpdateAnOrder() throws Exception {


        Order order = createDummyOrder();

        mockMvc.perform(get("/api/data/order/"
                        + order.getId().getCartId() +"/"
                        + order.getId().getProductId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.amount", is(3)));

        OrderDTO orderDTO = OrderDTO.builder()
                .cartId(order.getId().getCartId())
                .productId(order.getId().getProductId())
                .amount(24)
                .build();

        mockMvc.perform(post("/api/data/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.cartId", is(order.getId().getCartId()), Long.class))
                .andExpect(jsonPath("$.productId", is(order.getId().getProductId()), Long.class))
                .andExpect(jsonPath("$.amount", is(24)));

        mockMvc.perform(get("/api/data/cart/" + order.getId().getCartId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.orders.[0].amount", is(24)));

    }

    @Test
    public void canSaveAnOrder() throws Exception {

        OrderDTO orderDTO = createDummyOrderDTO();

        mockMvc.perform(post("/api/data/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.cartId", is(orderDTO.getCartId()), Long.class))
                .andExpect(jsonPath("$.productId", is(orderDTO.getProductId()), Long.class))
                .andExpect(jsonPath("$.amount", is(3)));

        mockMvc.perform(get("/api/data/cart/" + orderDTO.getCartId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.orders.[0].id.cartId", is(orderDTO.getCartId()), Long.class))
                .andExpect(jsonPath("$.orders.[0].id.productId", is(orderDTO.getProductId()), Long.class))
                .andExpect(jsonPath("$.orders.[0].amount", is(3)));

    }

    @Test
    public void canDeleteAllOrders() throws Exception {

        ResultActions result;

        createDummyOrder();
        createDummyOrder();

        mockMvc.perform(get("/api/data/orders"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", not(Matchers.empty())));

        mockMvc.perform(delete("/api/data/orders"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/data/orders"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is(Matchers.empty())));

    }
    
    @Test
    public void canDeleteOrderById() throws Exception {

        Order order = createDummyOrder();

        orderRepository.save(order);

        mockMvc.perform(get("/api/data/cart/" + order.getId().getCartId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.orders", Matchers.not(Matchers.empty())));

        mockMvc.perform(delete("/api/data/order/"
                        + order.getId().getCartId() + "/"
                        + order.getId().getProductId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/data/cart/" + order.getId().getCartId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.orders", is(Matchers.empty())));

    }

    private Order createDummyOrder() {

        final Cart cart = cartRepository.save(Cart.builder().build());
        final Product product = productRepository.save(Product.builder().build());

        final Order order = Order.builder()
                .id(new OrderKey(cart.getId(), product.getId()))
                .cart(cart)
                .product(product)
                .amount(3)
                .build();

        return orderRepository.save(order);

    }

    private OrderDTO createDummyOrderDTO() {

        final Cart cart = cartRepository.save(Cart.builder().build());
        final Product product = productRepository.save(Product.builder().build());

        return OrderDTO.builder()
                .cartId(cart.getId())
                .productId(product.getId())
                .amount(3)
                .build();

    }

}

package com.onebox.pruebatecnica;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebox.pruebatecnica.persistence.entity.Cart;
import com.onebox.pruebatecnica.persistence.repo.CartRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CartRepository cartRepository;


    @BeforeEach
    private void setUp() {
       cartRepository.deleteAll();
    }

    @Test
    public void canListAllCarts() throws Exception {

        Cart cart1 = cartRepository.save(Cart.builder().build());
        Cart cart2 = cartRepository.save(Cart.builder().build());

        mockMvc.perform(get("/api/data/carts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].id", is(cart1.getId()), Long.class))
                .andExpect(jsonPath("$.[1].id", is(cart2.getId()), Long.class));

    }

    @Test
    public void canGetCartById() throws Exception {

        Cart cart = cartRepository.save(Cart.builder().build());

        mockMvc.perform(get("/api/data/cart/" + cart.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(instanceOf(Integer.class))))
                .andExpect(jsonPath("$.orders", is(Matchers.empty())));

    }

    @Test
    public void canCreateACart() throws Exception {

        Cart cart = Cart.builder().build();

        mockMvc.perform(post("/api/data/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(instanceOf(Integer.class))))
                .andExpect(jsonPath("$.orders", is(nullValue())));

    }

    @Test
    public void canDeleteAllCarts() throws Exception {

        Cart cart1 = cartRepository.save(new Cart());
        Cart cart2 = cartRepository.save(new Cart());

        mockMvc.perform(get("/api/data/carts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].id", is(cart1.getId()), Long.class))
                .andExpect(jsonPath("$.[1].id", is(cart2.getId()), Long.class));

        mockMvc.perform(delete("/api/data/carts"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/data/carts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is(Matchers.empty())));

    }

    @Test
    public void canDeleteCartById() throws Exception {

        Cart cart = cartRepository.save(Cart.builder().build());

        mockMvc.perform(get("/api/data/cart/" + cart.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/data/cart/" + cart.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/data/cart/" + cart.getId()))
                .andExpect(status().isNotFound());

    }

}

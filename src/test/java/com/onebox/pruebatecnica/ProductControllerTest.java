package com.onebox.pruebatecnica;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebox.pruebatecnica.persistence.entity.Product;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
    }

    @Test
    public void canListAllProducts() throws Exception {

        Product product1 = productRepository.save(Product.builder().build());
        Product product2 = productRepository.save(Product.builder().build());

        mockMvc.perform(get("/api/data/products"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].id", is(product1.getId()), Long.class))
                .andExpect(jsonPath("$.[1].id", is(product2.getId()), Long.class));
    }

    @Test
    public void canGetProductById() throws Exception {

        Product product = productRepository.save(Product.builder().build());

        mockMvc.perform(get("/api/data/product/" + product.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(product.getId()), Long.class));

    }

    @Test
    public void canSaveAProduct() throws Exception {

        Product product = Product.builder().build();

        mockMvc.perform(post("/api/data/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(instanceOf(Integer.class))));

    }

    @Test
    public void canUpdateAProduct() throws Exception {

        // TODO

        Product product = productRepository.save(Product.builder().build());

        mockMvc.perform(post("/api/data/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(product.getId()), Long.class));

    }
    
    @Test
    public void canDeleteAllProducts() throws Exception {

        Product product1 = productRepository.save(Product.builder().build());
        Product product2 = productRepository.save(Product.builder().build());

        mockMvc.perform(get("/api/data/products"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].id", is(product1.getId()), Long.class))
                .andExpect(jsonPath("$.[1].id", is(product2.getId()), Long.class));

        mockMvc.perform(delete("/api/data/products"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/data/products"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is(Matchers.empty())));

    }
    
    @Test
    public void canDeleteProductById() throws Exception {


        Product product = productRepository.save(Product.builder().build());

        mockMvc.perform(get("/api/data/product/" + product.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/data/product/" + product.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/data/product/" + product.getId()))
                .andExpect(status().isNotFound());

    }

}

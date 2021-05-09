package com.adpro211.a8.tugaskelompok.product.controller;

import com.adpro211.a8.tugaskelompok.auths.controller.SignupController;
import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.PasswordStrategy;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.AdminRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.BuyerRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.SellerRepository;
import com.adpro211.a8.tugaskelompok.auths.service.*;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.service.ProductService;
import com.adpro211.a8.tugaskelompok.product.service.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private AuthServiceImpl authService;

    @MockBean
    private JWTServiceImpl jwtService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private BuyerRepository buyerRepository;

    @MockBean
    private SellerRepository sellerRepository;

    private Seller seller;
    private Buyer buyer;
    private Product product;
    private Iterable<Product> productIterable;
    private PasswordStrategy passwordStrategy;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    static class ProductData {
        public String name;
        public int price;
        public int stock;
        public String description;
        public String imageUrl;
        public Account seller;

        public ProductData(String name, int price, int stock, String description, String imageUrl, Account seller) {
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.description = description;
            this.imageUrl = imageUrl;
            this.seller = seller;
        }
    }

    static class ReviewData {
        public int star;
        public String content;

        public ReviewData(int star, String content) {
            this.star = star;
            this.content = content;
        }
    }

    @BeforeEach
    void setUp() {
        seller = new Seller();
        seller.setName("advis");
        seller.setId(1);
        seller.setEmail("advistasyam@gmail.com");
        seller.setAccountType("seller");

        buyer = new Buyer();
        buyer.setName("pembeli");
        buyer.setId(3);
        buyer.setEmail("kangsabun71@gmail.com");
        buyer.setAccountType("buyer");
        buyer.setAlamat("dimana saja");

        product = new Product();
        product.setId(2);
        product.setName("bakso");
        product.setPrice(1000);
        product.setStock(50);
        product.setDescription("makanan enak");
        product.setImageUrl("https://google.com");
        product.setOwnerAccount(seller);
    }

    @Test
    void testControllerCreateProductError() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.seller);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(post("/product/create").header("Authorization", "aaaaa").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                mapToJson(new ProductData("nama", 20, 12, "deskripsi", "google", this.seller)))).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetAllProductSuccess() throws Exception {
        mvc.perform(get("/product").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetProductReviewSuccess() throws Exception {
        mvc.perform(get("/product/2/review").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetProductByIdSuccess() throws Exception {
        when(productService.getProductById(2)).thenReturn(product);

        mvc.perform(get("/product/2").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerUpdateProductByIdSuccess() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.seller);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(put("/product/update/2").header("Authorization", "aaaaa").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                mapToJson(new ProductData("nama", 20, 12, "deskripsi", "google", this.seller)))).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetProductTenantSuccess() throws Exception {
        mvc.perform(get("/product/tenant/1").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerSearchProductSuccess() throws Exception {
        mvc.perform(get("/product/name/bak").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerDeleteProductSuccess() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.seller);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(delete("/product/delete/2").header("Authorization", "aaaaa").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerCreateProductReviewSuccess() throws Exception {
        when(accountService.getAccountById(3)).thenReturn(this.buyer);
        when(accountService.getAccountById(1)).thenReturn(this.buyer);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(post("/product/2/create/review").header("Authorization", "aaaaa").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                mapToJson(new ReviewData(5, "lumayan enak")))).andExpect(status().is2xxSuccessful());
    }
}

package com.adpro211.a8.tugaskelompok.order.controller;

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
import com.adpro211.a8.tugaskelompok.product.service.ProductServiceImpl;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.service.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private OrderServiceImpl orderService;

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
    private Order order;
    private Item item;
    private PasswordStrategy passwordStrategy;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    static class OrderData {
        public int totalPrice;
        public boolean paymentReceived;
        public String status;
        public boolean isFinished;
        public List<Item> items;
        public Account buyer;
        public Account seller;

        public OrderData(int totalPrice, String status, Account buyer, Account seller, List<Item> items) {
            this.totalPrice = totalPrice;
            this.paymentReceived = false;
            this.status = status;
            this.isFinished = false;
            this.items = items;
            this.buyer = buyer;
            this.seller = seller;
        }
    }

    static class ItemData {
        public String name;
        public int quantity;
        public int price;
        public Product product;
        public Order order;

        public ItemData(String name, int quantity, int price, Product product, Order order) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.product = product;
            this.order = order;
        }
    }

    @BeforeEach
    void setUp() {
        seller = new Seller();
        seller.setName("pram");
        seller.setId(1);
        seller.setEmail("naufal.pramudya11@gmail.com");
        seller.setAccountType("seller");

        buyer = new Buyer();
        buyer.setName("pembeli");
        buyer.setId(2);
        buyer.setEmail("ajib@gmail.com");
        buyer.setAccountType("buyer");
        buyer.setAlamat("menuju jalanmu yang lurus ya Allah");

        product = new Product();
        product.setId(3);
        product.setName("mie");
        product.setPrice(1000);
        product.setStock(50);
        product.setDescription("maknyusss");
        product.setImageUrl("https://google.com");
        product.setOwnerAccount(seller);

        item = new Item();
        item.setId(5);
        item.setName(product.getName());
        item.setQuantity(5);
        item.setOrder(order);
        item.setProduct(product);
        item.setPrice(product.getPrice() * item.getQuantity());

        order = new Order();
        order.setId(4);
        order.setPaymentReceived(false);
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setTotalPrice(0);
        order.setFinished(false);

        List<Item> itemList = new ArrayList<Item>();
        itemList.add(item);
        order.setItems(itemList);
        order.setTotalPrice(order.getTotalPrice() + item.getPrice());
    }

    @Test
    void testControllerCreateOrderSuccess() throws Exception {
        List<Item> kosong = new ArrayList<Item>();
        when(accountService.getAccountById(buyer.getId())).thenReturn(this.buyer);
        when(jwtService.verifyToken(anyString())).thenReturn("2");
        when(accountService.getAccountById(seller.getId())).thenReturn(this.seller);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(
                post("/order/checkout").header("Authorization", "test").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(new OrderData(0, "Open", this.buyer, this.seller, kosong))))
                .andExpect(status().is2xxSuccessful());
    }
}

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
    private Iterable<Item> items;
    private PasswordStrategy passwordStrategy;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    static class OrderData {
        public int idSeller;

        public OrderData(int idSeller) {
            this.idSeller = idSeller;
        }
    }

    static class OrderDataError {
        public int notSeller;
    }

    static class ItemData {
        public int quantity;
        public int productId;

        public ItemData(int quantity, int productId) {
            this.quantity = quantity;
            this.productId = productId;
        }
    }

    static class ItemDataError {
        public int notQuantity;
        public int notProductId;
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
        when(accountService.getAccountById(buyer.getId())).thenReturn(this.buyer);
        when(jwtService.verifyToken(anyString())).thenReturn("2");
        when(accountService.getAccountById(seller.getId())).thenReturn(this.seller);

        mvc.perform(post("/order/checkout").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(new OrderData(1))).header("Authorization", "aaaaaaaaa"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerCreateOrderFails() throws Exception {
        when(accountService.getAccountById(buyer.getId())).thenReturn(this.buyer);
        when(jwtService.verifyToken(anyString())).thenReturn("2");

        mvc.perform(post("/order/checkout").contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization",
                "aaaaaaaaa")).andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerCreateOrderWithNoIdSeller() throws Exception {
        when(accountService.getAccountById(buyer.getId())).thenReturn(this.buyer);
        when(jwtService.verifyToken(anyString())).thenReturn("2");
        when(accountService.getAccountById(seller.getId())).thenReturn(this.seller);

        mvc.perform(post("/order/checkout").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(new OrderDataError())).header("Authorization", "aaaaaaaaa"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerCreateOrderWithInvalidToken() throws Exception {
        mvc.perform(post("/order/checkout").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(new OrderData(1)))).andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerGetOrderById() throws Exception {
        when(orderService.getOrderById(4)).thenReturn(order);
        mvc.perform(get("/order/4").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetOrdersByBuyer() throws Exception {
        when(accountService.getAccountById(buyer.getId())).thenReturn(this.buyer);
        when(jwtService.verifyToken(anyString())).thenReturn("2");

        mvc.perform(get("/order/all").param("strategy", "buyer").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetOrdersByBuyerWithInvalidToken() throws Exception {
        mvc.perform(get("/order/all").param("strategy", "buyer").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerGetOrdersButBuyerIsNull() throws Exception {
        when(accountService.getAccountById(buyer.getId())).thenReturn(null);
        when(jwtService.verifyToken(anyString())).thenReturn("2");

        mvc.perform(get("/order/all").param("strategy", "buyer").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerGetOrdersBySeller() throws Exception {
        when(accountService.getAccountById(seller.getId())).thenReturn(this.seller);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/order/all").param("strategy", "seller").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetOrdersBySellerWithInvalidToken() throws Exception {
        mvc.perform(get("/order/all").param("strategy", "seller").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerGetOrdersButSellerIsNull() throws Exception {
        when(accountService.getAccountById(seller.getId())).thenReturn(null);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/order/all").param("strategy", "seller").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerGetOrdersByAccountNoParam() throws Exception {
        mvc.perform(get("/order/all").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerCreateItemSuccess() throws Exception {
        when(orderService.getOrderById(4)).thenReturn(order);
        when(productService.getProductById(3)).thenReturn(product);
        when(orderService.checkProductStock(2, product)).thenReturn(true);

        mvc.perform(post("/order/4/create-item").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(new ItemData(2, 3)))).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerCreateItemFails() throws Exception {
        mvc.perform(post("/order/4/create-item").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerCreateItemWrongRequestKey() throws Exception {
        when(orderService.getOrderById(4)).thenReturn(order);
        when(productService.getProductById(3)).thenReturn(product);
        when(orderService.checkProductStock(2, product)).thenReturn(true);

        mvc.perform(post("/order/4/create-item").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(new ItemDataError()))).andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerCreateItemStockInvalid() throws Exception {
        when(orderService.getOrderById(4)).thenReturn(order);
        when(productService.getProductById(3)).thenReturn(product);
        when(orderService.checkProductStock(2, product)).thenReturn(false);

        mvc.perform(post("/order/4/create-item").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(new ItemData(2, 3)))).andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerGetItemByIdSuccess() throws Exception {
        when(orderService.getItemById(5)).thenReturn(item);
        mvc.perform(get("/order/item/5").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetItemsByOrderIdSuccess() throws Exception {
        mvc.perform(get("/order/4/item/all").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerConfirmOrderSuccess() throws Exception {
        when(orderService.getOrderById(4)).thenReturn(order);

        mvc.perform(put("/order/4/confirm").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerConfirmOrderFails() throws Exception {
        when(orderService.getOrderById(4)).thenThrow(new NullPointerException());

        mvc.perform(put("/order/4/confirm").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testControllerShipOrderSuccess() throws Exception {
        when(orderService.getOrderById(4)).thenReturn(order);

        mvc.perform(put("/order/4/ship").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerDeliverOrderSuccess() throws Exception {
        when(orderService.getOrderById(4)).thenReturn(order);

        mvc.perform(put("/order/4/deliver").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerCancelOrderSuccess() throws Exception {
        when(orderService.getOrderById(4)).thenReturn(order);

        mvc.perform(put("/order/4/cancel").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }
}

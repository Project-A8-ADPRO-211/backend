package com.adpro211.a8.tugaskelompok.order.model.order;

import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderTest {

    private Order order;
    private Item item;
    private Product product;
    private Buyer buyer;
    private Seller seller;
    private LocalDateTime orderTime, paymentTime, shipTime, completedTime;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

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

        orderTime = LocalDateTime.now();
        order.setOrderTime(orderTime);
        paymentTime = LocalDateTime.now();
        order.setPaymentTime(paymentTime);
        shipTime = LocalDateTime.now();
        order.setShipTime(shipTime);
        completedTime = LocalDateTime.now();
        order.setCompletedTime(completedTime);

        List<Item> itemList = new ArrayList<Item>();
        itemList.add(item);
        order.setItems(itemList);
        order.setTotalPrice(order.getTotalPrice() + item.getPrice());
    }

    @Test
    void testGetId() {
        assertEquals(4, order.getId());
    }

    @Test
    void testGetWrongId() {
        assertNotEquals(1, order.getId());
    }

    @Test
    void testGetPaymentReceived() {
        assertFalse(order.isPaymentReceived());
    }

    @Test
    void testGetPaymentReceivedTrue() {
        assertTrue(!order.isPaymentReceived());
    }

    @Test
    void testGetBuyer() {
        assertEquals(this.buyer, order.getBuyer());
    }

    @Test
    void testGetWrongBuyer() {
        Buyer buyerTest = new Buyer();
        assertNotEquals(buyerTest, order.getBuyer());
    }

    @Test
    void testGetSeller() {
        assertEquals(this.seller, order.getSeller());
    }

    @Test
    void testGetWrongSeller() {
        Seller sellerTest = new Seller();
        assertNotEquals(sellerTest, order.getSeller());
    }

    @Test
    void testGetItemFromItemList() {
        assertEquals(item, order.getItems().get(0));
    }

    @Test
    void testGetWrongItemFromItemList() {
        Item itemTest = new Item();
        assertNotEquals(itemTest, order.getItems().get(0));
    }

    @Test
    void testGetStatus() {
        assertEquals("Open", order.getStatus());
    }

    @Test
    void testGetWrongStatus() {
        assertNotEquals("Confirmed", order.getStatus());
        assertNotEquals("Ship", order.getStatus());
        assertNotEquals("Delivered", order.getStatus());
        assertNotEquals("Cancelled", order.getStatus());
    }

    @Test
    void testGetTotalPrice() {
        assertEquals(5000, order.getTotalPrice());
    }

    @Test
    void testGetWrongTotalPrice() {
        assertNotEquals(1000, order.getTotalPrice());
    }

    @Test
    void testOrderTimeNotNull() {
        assertNotNull(order.getOrderTime());
    }

    @Test
    void testGetOrderTime() {
        assertEquals(orderTime, order.getOrderTime());
    }

    @Test
    void testGetPaymentTime() {
        assertEquals(paymentTime, order.getPaymentTime());
    }

    @Test
    void testGetShipTime() {
        assertEquals(shipTime, order.getShipTime());
    }

    @Test
    void testGetCompletedTime() {
        assertEquals(completedTime, order.getCompletedTime());
    }

    @Test
    void testEquals() {
        Order cek = new Order();
        assertTrue(cek.equals(cek));
    }

    @Test
    void testNotNull() {
        Order cek = new Order();
        assertFalse(cek.equals(null));
    }

    @Test
    void testNotSame() {
        Order cek = new Order();
        assertFalse(cek.equals(order));
    }
}

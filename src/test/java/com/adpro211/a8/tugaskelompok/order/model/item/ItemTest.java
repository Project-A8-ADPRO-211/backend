package com.adpro211.a8.tugaskelompok.order.model.item;

import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ItemTest {

    private Item item;
    private Order order;
    private Product product;
    private Seller seller;
    private Buyer buyer;

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

        order = new Order();
        order.setId(4);
        order.setPaymentReceived(false);
        order.setBuyer(buyer);
        order.setSeller(seller);

        item = new Item();
        item.setId(5);
        item.setName(product.getName());
        item.setQuantity(5);
        item.setOrder(order);
        item.setProduct(product);
        item.setPrice(product.getPrice() * item.getQuantity());

        List<Item> items = new ArrayList<Item>();
        items.add(item);
        order.setItems(items);
    }

    @Test
    void testGetId() {
        assertEquals(5, item.getId());
    }

    @Test
    void testGetWrongId() {
        assertNotEquals(1, item.getId());
    }

    @Test
    void testGetName() {
        assertEquals("mie", item.getName());
    }

    @Test
    void testWrongName() {
        assertNotEquals("bibimbap", item.getName());
    }

    @Test
    void testGetQuantity() {
        assertEquals(5, item.getQuantity());
    }

    @Test
    void testGetWrongQuantity() {
        assertNotEquals(10, item.getQuantity());
    }

    @Test
    void testGetOrder() {
        assertEquals(this.order, item.getOrder());
    }

    @Test
    void testGetWrongOrder() {
        Order orderTest = new Order();
        assertNotEquals(orderTest, item.getOrder());
    }

    @Test
    void testGetProduct() {
        assertEquals(this.product, item.getProduct());
    }

    @Test
    void testGetWrongProduct() {
        Product productTest = new Product();
        assertNotEquals(productTest, item.getProduct());
    }

    @Test
    void testGetPrice() {
        assertEquals(5000, item.getPrice());
    }

    @Test
    void testGetWrongPrice() {
        assertNotEquals(2000, item.getPrice());
    }

    @Test
    void testEquals() {
        Item baruw = new Item();
        assertTrue(baruw.equals(baruw));
    }

    @Test
    void testNotNull() {
        Item baruw = new Item();
        assertFalse(baruw.equals(null));
    }

    @Test
    void testNotSame() {
        Item baruw = new Item();
        assertFalse(baruw.equals(item));
    }
}

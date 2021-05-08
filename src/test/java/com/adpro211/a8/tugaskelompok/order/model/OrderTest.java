package com.adpro211.a8.tugaskelompok.order.model;

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

public class OrderTest {

    private Order order;
    private Item item;
    private Product product;
    private Buyer buyer;
    private Seller seller;

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
        item.setProductOwner(product.getOwnerAccount());
        item.setPrice(product.getPrice() * item.getQuantity());

        order = new Order();
        order.setId(4);
        order.setPaymentReceived(false);
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setTotalPrice(0);

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
    void testGetPaymentReceived() {
        assertEquals(false, order.isPaymentReceived());
    }

    @Test
    void testGetBuyer() {
        assertEquals(this.buyer, order.getBuyer());
    }

    @Test
    void testGetItems() {
        assertEquals(this.seller, order.getSeller());
    }

    @Test
    void testGetItemFromItemList() {
        assertEquals(item, order.getItems().get(0));
    }

    @Test
    void testGetCurrentState() {
        assertEquals("Open", order.getStateDescription());
    }

    @Test
    void testGetStatusInt() {
        assertEquals(0, order.getStatusInt());
    }

    @Test
    void testGetTotalPrice() {
        assertEquals(5000, order.getTotalPrice());
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

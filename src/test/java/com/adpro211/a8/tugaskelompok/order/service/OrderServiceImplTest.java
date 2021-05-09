package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.order.service.OrderServiceImpl;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.repository.ItemRepository;
import com.adpro211.a8.tugaskelompok.order.repository.OrderRepository;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ItemRepository itemRepository;

    private Order order;
    private Item item1;
    private Item item2;
    private Buyer buyer;
    private Seller seller;
    private Product product1;
    private Product product2;
    private Wallet walletBuyer;
    private Wallet walletSeller;

    @BeforeEach
    void setUp() {

        seller = new Seller();
        seller.setName("umji");
        seller.setId(2);
        seller.setEmail("yewonkim@gmail.com");
        seller.setAccountType("seller");

        buyer = new Buyer();
        buyer.setName("pembeli");
        buyer.setId(2);
        buyer.setEmail("ajib@gmail.com");
        buyer.setAccountType("buyer");
        buyer.setAlamat("menuju jalan lurusmu ya Allah");

        walletBuyer = new Wallet();
        walletBuyer.setId(5);
        walletBuyer.setAccount(buyer);
        walletBuyer.setBalance(200000.0);
        buyer.setWallet(walletBuyer);

        walletSeller = new Wallet();
        walletSeller.setId(6);
        walletSeller.setAccount(seller);
        walletSeller.setBalance(100000.0);
        seller.setWallet(walletSeller);

        product1 = new Product();
        product1.setId(3);
        product1.setName("bakso");
        product1.setPrice(2000);
        product1.setStock(50);
        product1.setDescription("maknyusss");
        product1.setImageUrl("https://google.com");
        product1.setOwnerAccount(seller);

        product2 = new Product();
        product2.setId(4);
        product2.setName("mie");
        product2.setPrice(1000);
        product2.setStock(50);
        product2.setDescription("asik asik jos");
        product2.setImageUrl("https://google.com");
        product2.setOwnerAccount(seller);

        item1 = new Item();
        item1.setId(3);
        item1.setName(product1.getName());
        item1.setOrder(order);
        item1.setQuantity(2);
        item1.setPrice(product1.getPrice() * item1.getQuantity());
        item1.setProduct(product1);
        item1.setProductOwner(product1.getOwnerAccount());

        item2 = new Item();
        item2.setId(4);
        item2.setName(product2.getName());
        item2.setOrder(order);
        item2.setQuantity(2);
        item2.setPrice(product2.getPrice() * item2.getQuantity());
        item2.setProduct(product2);
        item2.setProductOwner(product2.getOwnerAccount());

        order = new Order();
        order.setId(1);
        order.setPaymentReceived(false);
        order.setBuyer(buyer);
        order.setSeller(seller);

        List<Item> itemList = new ArrayList<Item>();
        itemList.add(item1);
        itemList.add(item2);
        order.setItems(itemList);
        order.setTotalPrice(order.getTotalPrice() + item1.getPrice() + item2.getPrice());

    }

}

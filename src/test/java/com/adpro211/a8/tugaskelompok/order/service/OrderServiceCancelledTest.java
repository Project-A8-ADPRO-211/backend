package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.states.*;
import com.adpro211.a8.tugaskelompok.order.repository.ItemRepository;
import com.adpro211.a8.tugaskelompok.order.repository.OrderRepository;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;
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

@ExtendWith(MockitoExtension.class)
public class OrderServiceCancelledTest {
    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    WalletRepository walletRepository;

    private Order order;
    private Item item;
    private OrderState state;
    private Buyer buyer;
    private Seller seller;
    private Product product;
    private Wallet walletBuyer;
    private Wallet walletSeller;

    @BeforeEach
    void setUp() {

        seller = new Seller();
        seller.setName("umji");
        seller.setId(1);
        seller.setEmail("yewonkim@gmail.com");
        seller.setAccountType("seller");

        buyer = new Buyer();
        buyer.setName("pembeli");
        buyer.setId(2);
        buyer.setEmail("ajib@gmail.com");
        buyer.setAccountType("buyer");
        buyer.setAlamat("menuju jalan lurusmu ya Allah");

        accountRepository.save(buyer);
        accountRepository.save(seller);

        walletBuyer = new Wallet();
        walletBuyer.setId(5);
        walletBuyer.setAccount(buyer);
        walletBuyer.setBalance(200000.0);
        walletRepository.save(walletBuyer);
        buyer.setWallet(walletBuyer);

        walletSeller = new Wallet();
        walletSeller.setId(6);
        walletSeller.setAccount(seller);
        walletSeller.setBalance(100000.0);
        walletRepository.save(walletSeller);
        seller.setWallet(walletSeller);

        product = new Product();
        product.setId(3);
        product.setName("mie");
        product.setPrice(1000);
        product.setStock(50);
        product.setDescription("maknyusss");
        product.setImageUrl("https://google.com");
        product.setOwnerAccount(seller);

        item = new Item();
        item.setId(3);
        item.setName(product.getName());
        item.setOrder(order);
        item.setQuantity(2);
        item.setPrice(product.getPrice() * item.getQuantity());
        item.setProduct(product);
        itemRepository.save(item);

        state = new CancelledState();

        order = new Order();
        order.setId(1);
        order.setPaymentReceived(true);
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setStatus(state.getStateDescription());

        List<Item> itemList = new ArrayList<Item>();
        itemList.add(item);
        order.setItems(itemList);
        order.setTotalPrice(order.getTotalPrice() + item.getPrice());

    }

    // Tests when Order State is Delivered
    @Test
    void testOrderTimeNotNull() {
        assertNotNull(order.getOrderTime());
    }

    @Test
    void testConfirmOrderFailsWhenStateIsCancelled() {
        Order toConfirm = orderService.confirmOrder(order);
        assertNotEquals("Confirmed", toConfirm.getStatus());
        assertNull(toConfirm.getPaymentTime());
        assertFalse(toConfirm.isFinished());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void testCancelOrderDoNothing() {
        Order toCancel = orderService.cancelOrder(order);
        toCancel.setFinished(true);
        assertEquals("Cancelled", toCancel.getStatus());
        assertTrue(toCancel.isFinished());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void testShipOrderFailsWhenStateIsCancelled() {
        Order toShip = orderService.shipOrder(order);
        assertNotEquals("Ship", toShip.getStatus());
        assertNull(toShip.getShipTime());
        assertFalse(toShip.isFinished());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void testDeliverOrderFailsWhenStateIsCancelled() {
        Order toDeliver = orderService.deliverOrder(order);
        toDeliver.setFinished(true);
        assertNotEquals("Delivered", toDeliver.getStatus());
        assertNull(toDeliver.getCompletedTime());
        assertTrue(toDeliver.isFinished());
        verify(orderRepository, times(1)).save(any());
    }
}

package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.order.service.OrderServiceImpl;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.states.ConfirmedState;
import com.adpro211.a8.tugaskelompok.order.model.states.ShipState;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    AccountRepository accountRepository;

    private Order order;
    private Item item;
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
        item.setProductOwner(product.getOwnerAccount());

        order = new Order();
        order.setId(1);
        order.setPaymentReceived(false);
        order.setBuyer(buyer);
        order.setSeller(seller);

        List<Item> itemList = new ArrayList<Item>();
        itemList.add(item);
        order.setItems(itemList);
        order.setTotalPrice(order.getTotalPrice() + item.getPrice());
    }

    @Test
    void testCreateOrderSuccess() {
        accountRepository.save(buyer);
        accountRepository.save(seller);
        orderService.createOrder(false, 1, 2);
        verify(orderRepository, times(1)).save(any());
    }

    // @Test
    // void testCreateOrderThrowsException() {
    // lenient().when(orderRepository.save(order))
    // .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "This order
    // already exist"));

    // Throwable exception = assertThrows(ResponseStatusException.class, () ->
    // orderService.createOrder(false, 1, 2));
    // assertEquals("This order already exist", exception.getMessage());
    // }

    @Test
    void testGetOrderById() {
        lenient().when(orderRepository.findOrderById(1)).thenReturn(order);
        assertEquals(order, orderService.getOrderById(1));
    }

    @Test
    void testGetOrdersBySellerSuccess() {
        List<Order> listOrder = new ArrayList<Order>();
        listOrder.add(order);
        lenient().when(orderRepository.findAllBySeller(seller)).thenReturn(listOrder);
        assertEquals(listOrder, orderService.getOrdersBySeller(seller));
    }

    @Test
    void testGetOrdersByBuyerSuccess() {
        List<Order> listOrder = new ArrayList<Order>();
        listOrder.add(order);
        lenient().when(orderRepository.findAllByBuyer(buyer)).thenReturn(listOrder);
        assertEquals(listOrder, orderService.getOrdersByBuyer(buyer));
    }

    @Test
    void testCreateItemSuccess() {
        orderRepository.save(order);
        orderService.createItem("mie", 2, 1, product);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    void testCheckProductStockValid() {
        assertTrue(orderService.checkProductStock(2, product));
    }

    @Test
    void testCheckProductStockNotValid() {
        assertFalse(orderService.checkProductStock(51, product));
    }

    @Test
    void testGetItemById() {
        lenient().when(itemRepository.findItemById(3)).thenReturn(item);
        assertEquals(item, orderService.getItemById(3));
    }

    @Test
    void testConfirmOrderSuccess() {
        Order toConfirm = orderService.confirmOrder(order);
        assertEquals("Confirmed", toConfirm.getStateDescription());
        assertFalse(toConfirm.isFinished());
    }

    @Test
    void testCancelOrderSuccess() {
        Order toCancel = orderService.cancelOrder(order);
        assertEquals("Cancelled", toCancel.getStateDescription());
        assertTrue(toCancel.isFinished());
    }

    @Test
    void testShipOrderSuccess() {
        order.setCurrentState(new ConfirmedState(order));
        order.setPaymentReceived(true);
        Order toShip = orderService.shipOrder(order);
        assertEquals("Ship", toShip.getStateDescription());
        assertFalse(toShip.isFinished());
    }

    @Test
    void testDeliverOrderSuccess() {
        order.setCurrentState(new ShipState(order));
        Order toDeliver = orderService.shipOrder(order);
        assertEquals("Delivered", toDeliver.getStateDescription());
        assertTrue(toDeliver.isFinished());
    }

    @Test
    void testPayOrderWorks() {
        Order toPay = orderService.payOrder(order);
        assertTrue(toPay.isPaymentReceived());
    }
}

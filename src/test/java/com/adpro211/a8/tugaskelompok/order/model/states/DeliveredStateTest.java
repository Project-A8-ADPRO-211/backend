package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveredStateTest {

    private Order order;
    private OrderState state;

    @BeforeEach
    void setUp() {

        order = new Order();
        order.setId(4);
        order.setPaymentReceived(false);
        order.setBuyer(new Buyer());
        order.setSeller(new Seller());

        state = new DeliveredState();
        order.setStatus(state.getStateDescription());
    }

    @Test
    void testStateisDeliveredState() {
        assertEquals(DeliveredState.class, state.getClass());
    }

    @Test
    void testGetStateDescription() {
        assertEquals("Delivered", state.getStateDescription());
    }

    @Test
    void testShipStateStatusIsCorrect() {
        assertEquals("Delivered", order.getStatus());
    }

    @Test
    void testConfirmOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.confirmOrder(order));
        assertEquals("Can't confirm an order when the order is Delivered", exception.getMessage());
    }

    @Test
    void testCancelOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.cancelOrder(order));
        assertEquals("Can't cancel an order when the order is Delivered", exception.getMessage());
    }

    @Test
    void testShipOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.shipOrder(order));
        assertEquals("Can't ship an order when the order is Delivered", exception.getMessage());
    }

    @Test
    void testDeliverOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.orderDelivered(order));
        assertEquals("Can't deliver an order when the order is Delivered", exception.getMessage());
    }

    @Test
    void testOrderIsFinished() {
        assertTrue(state.isFinished());
    }
}

package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfirmedStateTest {

    private Order order;
    private OrderState state;

    @BeforeEach
    void setUp() {

        order = new Order();
        order.setId(4);
        order.setPaymentReceived(false);
        order.setBuyer(new Buyer());
        order.setSeller(new Seller());

        state = new ConfirmedState();
        order.setStatus(state.getStateDescription());
    }

    @Test
    void testStateisConfirmedState() {
        assertEquals(ConfirmedState.class, state.getClass());
    }

    @Test
    void testGetStateDescription() {
        assertEquals("Confirmed", state.getStateDescription());
    }

    @Test
    void testConfirmedStateStatusIsCorrect() {
        assertEquals("Confirmed", order.getStatus());
    }

    @Test
    void testConfirmOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.confirmOrder(order));
        assertEquals("Can't confirm an order when the order is Confirmed", exception.getMessage());
    }

    @Test
    void testCancelOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.cancelOrder(order));
        assertEquals("Can't cancel an order when the order is Confirmed", exception.getMessage());
    }

    @Test
    void testCanNotShipOrderIfPaymentNotReceived() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.shipOrder(order));
        assertEquals("An order could not be shipped when payment is not received", exception.getMessage());
    }

    @Test
    void testShipOrderWorks() {
        order.setPaymentReceived(true);
        Order orderShipped = state.shipOrder(order);
        assertEquals("Ship", orderShipped.getStatus());
    }

    @Test
    void testDeliverOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.orderDelivered(order));
        assertEquals("Can't deliver an order when the order is Confirmed", exception.getMessage());
    }

    @Test
    void testOrderIsNotFinished() {
        assertFalse(state.isFinished());
    }
}

package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OpenStateTest {

    private Order order;
    private OrderState state;

    @BeforeEach
    void setUp() {

        order = new Order();
        order.setId(4);
        order.setPaymentReceived(false);
        order.setBuyer(new Buyer());
        order.setSeller(new Seller());

        state = new OpenState();
        order.setStatus(state.getStateDescription());
    }

    @Test
    void testStateisOpenState() {
        assertEquals(OpenState.class, state.getClass());
    }

    @Test
    void testGetStateDescription() {
        assertEquals("Open", state.getStateDescription());
    }

    @Test
    void testOpenStateStatusIsCorrect() {
        assertEquals("Open", order.getStatus());
    }

    @Test
    void testConfirmOrderWorks() {
        Order orderConfirmed = state.confirmOrder(order);
        assertEquals("Confirmed", orderConfirmed.getStatus());
    }

    @Test
    void testCancelOrderWorks() {
        Order orderCancelled = state.cancelOrder(order);
        assertEquals("Cancelled", orderCancelled.getStatus());
    }

    @Test
    void testShipOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.shipOrder(order));
        assertEquals("Can't ship an order when the order is Open", exception.getMessage());

    }

    @Test
    void testDeliverOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.orderDelivered(order));
        assertEquals("Can't deliver an order when the order is Open", exception.getMessage());
    }

    @Test
    void testOrderIsNotFinished() {
        assertFalse(state.isFinished());
    }
}

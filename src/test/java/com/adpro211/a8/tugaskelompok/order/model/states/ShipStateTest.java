package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShipStateTest {
    private Order order;
    private OrderState state;

    @BeforeEach
    void setUp() {

        order = new Order();
        order.setId(4);
        order.setPaymentReceived(false);
        order.setBuyer(new Buyer());
        order.setSeller(new Seller());

        state = new ShipState();
        order.setStatus(state.getStateDescription());
    }

    @Test
    void testStateisShipState() {
        assertEquals(ShipState.class, state.getClass());
    }

    @Test
    void testGetStateDescription() {
        assertEquals("Ship", state.getStateDescription());
    }

    @Test
    void testShipStateStatusIsCorrect() {
        assertEquals("Ship", order.getStatus());
    }

    @Test
    void testConfirmOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.confirmOrder(order));
        assertEquals("Can't confirm an order when the order is in Ship state", exception.getMessage());
    }

    @Test
    void testCancelOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.cancelOrder(order));
        assertEquals("Can't cancel an order when the order is in Ship state", exception.getMessage());
    }

    @Test
    void testShipOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.shipOrder(order));
        assertEquals("Can't ship an order when the order is in Ship state", exception.getMessage());
    }

    @Test
    void testDeliverOrderWorks() {
        Order orderDelivered = state.orderDelivered(order);
        orderDelivered.setFinished(true);
        assertEquals("Delivered", orderDelivered.getStatus());
        assertTrue(orderDelivered.isFinished());
    }

    @Test
    void testOrderIsNotFinished() {
        assertFalse(state.isFinished());
    }
}

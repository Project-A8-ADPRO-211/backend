package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CancelledStateTest {
    private Order order;
    private OrderState state;

    @BeforeEach
    void setUp() {

        order = new Order();
        order.setId(4);
        order.setPaymentReceived(false);
        order.setBuyer(new Buyer());
        order.setSeller(new Seller());

        state = new CancelledState(order);
        order.setCurrentState(state);
    }

    @Test
    void testStateisCancelledState() {
        assertEquals(CancelledState.class, state.getClass());
    }

    @Test
    void testGetStateDescription() {
        assertEquals("Cancelled", state.getStateDescription());
    }

    @Test
    void testShipStateStatusIntIsCorrect() {
        assertEquals(4, order.getStatusInt());
    }

    @Test
    void testConfirmOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.confirmOrder());
        assertEquals("Can't confirm an order when the order is in Cancelled state", exception.getMessage());
    }

    @Test
    void testCancelOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.cancelOrder());
        assertEquals("Can't cancel an order when the order is in Cancelled state", exception.getMessage());
    }

    @Test
    void testShipOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.shipOrder());
        assertEquals("Can't ship an order when the order is in Cancelled state", exception.getMessage());
    }

    @Test
    void testDeliverOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.orderDelivered());
        assertEquals("Can't deliver an order when the order is in Cancelled state", exception.getMessage());
    }

    @Test
    void testOrderIsFinished() {
        assertTrue(state.isFinished());
    }
}

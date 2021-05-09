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

        state = new OpenState(order);
        order.setCurrentState(state);
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
    void testOpenStateStatusIntIsCorrect() {
        assertEquals(0, order.getStatusInt());
    }

    @Test
    void testConfirmOrderWorks() {
        state.confirmOrder();
        assertEquals("Confirmed", order.getStateDescription());
        assertEquals(1, order.getStatusInt());
    }

    @Test
    void testCancelOrderWorks() {
        state.cancelOrder();
        assertEquals("Cancelled", order.getStateDescription());
        assertEquals(4, order.getStatusInt());
    }

    @Test
    void testShipOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.shipOrder());
        assertEquals("Can't ship an order when the order is in Open state", exception.getMessage());

    }

    @Test
    void testDeliverOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.orderDelivered());
        assertEquals("Can't deliver an order when the order is in Open state", exception.getMessage());
    }

    @Test
    void testOrderIsNotFinished() {
        assertFalse(state.isFinished());
    }
}

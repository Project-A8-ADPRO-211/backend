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

        state = new ConfirmedState(order);
        order.setCurrentState(state);
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
    void testConfirmedStateStatusIntIsCorrect() {
        assertEquals(1, order.getStatusInt());
    }

    @Test
    void testConfirmOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.confirmOrder());
        assertEquals("Can't confirm an order when the order is in Confirmed state", exception.getMessage());
    }

    @Test
    void testCancelOrderWorks() {
        state.cancelOrder();
        assertEquals("Cancelled", order.getStateDescription());
        assertEquals(4, order.getStatusInt());
    }

    @Test
    void testCanNotShipOrderIfPaymentNotReceived() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.shipOrder());
        assertEquals("An order could not be shipped when payment is not received", exception.getMessage());
    }

    @Test
    void testShipOrderWorks() {
        order.setPaymentReceived(true);
        state.shipOrder();
        assertEquals("Ship", order.getStateDescription());
        assertEquals(2, order.getStatusInt());
    }

    @Test
    void testDeliverOrderThrowsException() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> state.orderDelivered());
        assertEquals("Can't deliver an order when the order is in Confirmed state", exception.getMessage());
    }

    @Test
    void testOrderIsNotFinished() {
        assertFalse(state.isFinished());
    }
}

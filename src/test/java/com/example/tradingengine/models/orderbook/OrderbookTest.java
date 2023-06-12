package com.example.tradingengine.models.orderbook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.tradingengine.models.orders.CancelOrder;
import com.example.tradingengine.models.orders.ModifyOrder;
import com.example.tradingengine.models.orders.Order;
import com.example.tradingengine.models.orders.OrderCore;
import com.example.tradingengine.models.orders.OrderbookEntry;

public class OrderbookTest {

    @Test
    public void testAddSingleOrderSuccess() {
        Orderbook ob = new Orderbook(null);
        ob.addOrder(new Order(new OrderCore(0, "TestUser", 1), 1, 10, true));

        int actual = ob.getCount();
        final int expected = 1;

        assertEquals(expected, actual);
    }

    @Test
    public void testAddMultipleOrderSuccess() {
        Orderbook ob = new Orderbook(null);

        final int expected = 10;

        for (int i = 0; i < expected; i++) {
            ob.addOrder(new Order(new OrderCore(i, "TestUser", 1), (long) (Math.random() * 100), 10,
                    true));
        }

        int actual = ob.getCount();

        assertEquals(expected, actual);
    }

    @Test
    public void testAddOrderThenRemoveOrderSuccess() {
        final long orderId = 0;
        Orderbook ob = new Orderbook(null);
        ob.addOrder(new Order(new OrderCore(orderId, "TestUser", 1), 1, 10, true));
        ob.removeOrder(new CancelOrder(new OrderCore(orderId, "TestUser", 1)));

        int actual = ob.getCount();
        final int expected = 0;

        assertEquals(expected, actual);
    }

    @Test
    public void testAddOrderThenModifyOrderSuccess() {
        final long orderId = 0;
        final int modifyOrderQuantity = 5;
        Orderbook ob = new Orderbook(null);
        ob.addOrder(new Order(new OrderCore(orderId, "TestUser", 1), 1, 10, true));
        assertEquals(1, ob.getBuyOrders().size());
        ob.changeOrder(new ModifyOrder(new OrderCore(orderId, "TestUser", 1), 1, modifyOrderQuantity, true));

        int actual = ob.getCount();
        final int expected = 1;

        assertEquals(expected, actual);

        List<OrderbookEntry> buyOrders = ob.getBuyOrders();
        assertEquals(1, buyOrders.size());
        assertEquals(modifyOrderQuantity, buyOrders.get(0).currentOrder.currentQuantity);
    }
}

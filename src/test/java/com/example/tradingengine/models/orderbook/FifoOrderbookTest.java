package com.example.tradingengine.models.orderbook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.tradingengine.models.instrument.Security;
import com.example.tradingengine.models.orders.Order;
import com.example.tradingengine.models.orders.OrderCore;

public class FifoOrderbookTest {

    @Test
    public void testMatchTwoOrdersCompleteFill() {
        MatchingOrderbook orderbook = new FifoOrderbook(new Security());

        long askOrderId = 0;
        long bidOrderId = 1;

        Order askOrder = new Order(new OrderCore(askOrderId, "TestUser", 0), 10, 10, false);
        Order bidOrder = new Order(new OrderCore(bidOrderId, "TestUser", 0), 10, 10, true);

        orderbook.addOrder(askOrder);
        orderbook.addOrder(bidOrder);

        MatchResult matchResult = orderbook.match();

        assertEquals(1, matchResult.getTrades().size());
        assertEquals(2, matchResult.getFills().size());
        assertEquals(false, orderbook.containsOrder(askOrderId));
        assertEquals(false, orderbook.containsOrder(bidOrderId));
    }

    @Test
    public void testMatchTwoOrdersPartialFill() {
        MatchingOrderbook orderbook = new FifoOrderbook(new Security());

        long askOrderId = 0;
        long bidOrderId = 1;

        Order askOrder = new Order(new OrderCore(askOrderId, "TestUser", 0), 10, 10, false);
        Order bidOrder = new Order(new OrderCore(bidOrderId, "TestUser", 0), 10, 20, true);

        orderbook.addOrder(askOrder);
        orderbook.addOrder(bidOrder);

        MatchResult matchResult = orderbook.match();

        assertEquals(1, matchResult.getTrades().size());
        assertEquals(2, matchResult.getFills().size());
        assertEquals(false, orderbook.containsOrder(askOrderId));
        assertEquals(true, orderbook.containsOrder(bidOrderId));
    }

    @Test
    public void testMatchThreeOrdersCompleteFill() {
        MatchingOrderbook orderbook = new FifoOrderbook(new Security());

        long askOrderId1 = 0;
        long askOrderId2 = 1;
        long bidOrderId = 2;

        Order askOrder1 = new Order(new OrderCore(askOrderId1, "TestUser", 0), 10, 10, false);
        Order askOrder2 = new Order(new OrderCore(askOrderId2, "TestUser", 0), 10, 10, false);
        Order bidOrder = new Order(new OrderCore(bidOrderId, "TestUser", 0), 10, 20, true);

        orderbook.addOrder(askOrder1);
        orderbook.addOrder(askOrder2);
        orderbook.addOrder(bidOrder);

        MatchResult matchResult = orderbook.match();

        assertEquals(2, matchResult.getTrades().size());
        assertEquals(4, matchResult.getFills().size());
        assertEquals(false, orderbook.containsOrder(askOrderId1));
        assertEquals(false, orderbook.containsOrder(askOrderId2));
        assertEquals(false, orderbook.containsOrder(bidOrderId));
    }

    @Test
    public void testMatchThreeOrdersPartialFillAskRemaining() {
        MatchingOrderbook orderbook = new FifoOrderbook(new Security());

        long askOrderId1 = 0;
        long askOrderId2 = 1;
        long bidOrderId = 2;

        Order askOrder1 = new Order(new OrderCore(askOrderId1, "TestUser", 0), 10, 10, false);
        Order askOrder2 = new Order(new OrderCore(askOrderId2, "TestUser", 0), 10, 20, false);
        Order bidOrder = new Order(new OrderCore(bidOrderId, "TestUser", 0), 10, 20, true);

        orderbook.addOrder(askOrder1);
        orderbook.addOrder(askOrder2);
        orderbook.addOrder(bidOrder);

        MatchResult matchResult = orderbook.match();

        assertEquals(2, matchResult.getTrades().size());
        assertEquals(4, matchResult.getFills().size());
        assertEquals(false, orderbook.containsOrder(askOrderId1));
        assertEquals(true, orderbook.containsOrder(askOrderId2));
        assertEquals(false, orderbook.containsOrder(bidOrderId));
    }

    @Test
    public void testMatchThreeOrdersPartialFillBidRemaining() {
        MatchingOrderbook orderbook = new FifoOrderbook(new Security());

        long askOrderId1 = 0;
        long askOrderId2 = 1;
        long bidOrderId = 2;

        Order askOrder1 = new Order(new OrderCore(askOrderId1, "TestUser", 0), 10, 10, false);
        Order askOrder2 = new Order(new OrderCore(askOrderId2, "TestUser", 0), 10, 10, false);
        Order bidOrder = new Order(new OrderCore(bidOrderId, "TestUser", 0), 10, 30, true);

        orderbook.addOrder(askOrder1);
        orderbook.addOrder(askOrder2);
        orderbook.addOrder(bidOrder);

        MatchResult matchResult = orderbook.match();

        assertEquals(2, matchResult.getTrades().size());
        assertEquals(4, matchResult.getFills().size());
        assertEquals(false, orderbook.containsOrder(askOrderId1));
        assertEquals(false, orderbook.containsOrder(askOrderId2));
        assertEquals(true, orderbook.containsOrder(bidOrderId));
    }

    @Test
    public void testMatchFourOrdersCompleteFill() {
        MatchingOrderbook orderbook = new FifoOrderbook(new Security());

        long askOrderId1 = 0;
        long askOrderId2 = 1;
        long bidOrderId1 = 2;
        long bidOrderId2 = 3;

        Order askOrder1 = new Order(new OrderCore(askOrderId1, "TestUser", 0), 10, 20, false);
        Order askOrder2 = new Order(new OrderCore(askOrderId2, "TestUser", 0), 10, 10, false);
        Order bidOrder1 = new Order(new OrderCore(bidOrderId1, "TestUser", 0), 10, 15, true);
        Order bidOrder2 = new Order(new OrderCore(bidOrderId1, "TestUser", 0), 10, 15, true);

        orderbook.addOrder(askOrder1);
        orderbook.addOrder(askOrder2);
        orderbook.addOrder(bidOrder1);
        orderbook.addOrder(bidOrder2);

        MatchResult matchResult = orderbook.match();

        assertEquals(3, matchResult.getTrades().size());
        assertEquals(6, matchResult.getFills().size());
        assertEquals(false, orderbook.containsOrder(askOrderId1));
        assertEquals(false, orderbook.containsOrder(askOrderId2));
        assertEquals(false, orderbook.containsOrder(bidOrderId1));
        assertEquals(false, orderbook.containsOrder(bidOrderId2));
    }

    @Test
    public void testMatchFourOrdersCompleteFillDifferentPrice() {
        MatchingOrderbook orderbook = new FifoOrderbook(new Security());

        long askOrderId1 = 0;
        long askOrderId2 = 1;
        long bidOrderId1 = 2;
        long bidOrderId2 = 3;

        Order askOrder1 = new Order(new OrderCore(askOrderId1, "TestUser", 0), 10, 20, false);
        Order askOrder2 = new Order(new OrderCore(askOrderId2, "TestUser", 0), 10, 10, false);
        Order bidOrder1 = new Order(new OrderCore(bidOrderId1, "TestUser", 0), 10, 15, true);
        Order bidOrder2 = new Order(new OrderCore(bidOrderId1, "TestUser", 0), 11, 15, true);

        orderbook.addOrder(askOrder1);
        orderbook.addOrder(askOrder2);
        orderbook.addOrder(bidOrder1);
        orderbook.addOrder(bidOrder2);

        MatchResult matchResult = orderbook.match();

        assertEquals(3, matchResult.getTrades().size());
        assertEquals(6, matchResult.getFills().size());
        assertEquals(false, orderbook.containsOrder(askOrderId1));
        assertEquals(false, orderbook.containsOrder(askOrderId2));
        assertEquals(false, orderbook.containsOrder(bidOrderId1));
        assertEquals(false, orderbook.containsOrder(bidOrderId2));
    }

}

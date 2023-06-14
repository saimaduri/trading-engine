package com.example.tradingengine.models.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.tradingengine.models.fills.FillAllocationAlgorithm;
import com.example.tradingengine.models.instrument.Security;
import com.example.tradingengine.models.orderbook.MatchResult;
import com.example.tradingengine.models.orders.Order;
import com.example.tradingengine.models.orders.OrderCore;

public class EngineTest {

    final List<Security> supportedSecurities = new ArrayList<>(Arrays.asList(
            new Security(0, "AAPL"),
            new Security(1, "GOOG"),
            new Security(2, "MSFT"),
            new Security(3, "META"),
            new Security(4, "NFLX")));

    @Test
    public void testAddInvalidSecurity() {

        Engine engine = new Engine(supportedSecurities, FillAllocationAlgorithm.FIFO);

        Security security = new Security(5, "PLTR");

        Order order = new Order(new OrderCore(0, "TestUser", security.securityId), 10, 1, false);

        MatchResult matchResult = engine.process(order);

        assertNull(matchResult);

    }

    @Test
    public void testAddSameSecurityOrderToEngineAndMatch() {

        Engine engine = new Engine(supportedSecurities, FillAllocationAlgorithm.FIFO);

        Security security = new Security(0, "AAPL");

        long askOrderId1 = 0;
        long askOrderId2 = 1;
        long bidOrderId1 = 2;
        long bidOrderId2 = 3;

        Order askOrder1 = new Order(new OrderCore(askOrderId1, "TestUser", security.securityId), 10, 20, false);
        Order askOrder2 = new Order(new OrderCore(askOrderId2, "TestUser", security.securityId), 10, 10, false);
        Order bidOrder1 = new Order(new OrderCore(bidOrderId1, "TestUser", security.securityId), 10, 15, true);
        Order bidOrder2 = new Order(new OrderCore(bidOrderId2, "TestUser", security.securityId), 11, 15, true);

        MatchResult matchResult1 = engine.process(askOrder1);
        assertEquals(0, matchResult1.getTrades().size());

        MatchResult matchResult2 = engine.process(askOrder2);
        assertEquals(0, matchResult2.getTrades().size());

        MatchResult matchResult3 = engine.process(bidOrder1);
        assertEquals(1, matchResult3.getTrades().size());

        MatchResult matchResult4 = engine.process(bidOrder2);
        assertEquals(2, matchResult4.getTrades().size());

    }

    @Test
    public void testAddDifferentSecuritiesOrdersToEngineAndMatch() {

        Engine engine = new Engine(supportedSecurities, FillAllocationAlgorithm.FIFO);

        Security apple = new Security(0, "AAPL");
        Security google = new Security(1, "GOOG");
        Security microsoft = new Security(2, "MSFT");

        long askOrderId1 = 0;
        long askOrderId2 = 1;
        long bidOrderId1 = 2;
        long bidOrderId2 = 3;

        Order askOrder1 = new Order(new OrderCore(askOrderId1, "TestUser", apple.securityId), 10, 20, false);
        Order askOrder2 = new Order(new OrderCore(askOrderId2, "TestUser", google.securityId), 10, 10, false);
        Order bidOrder1 = new Order(new OrderCore(bidOrderId1, "TestUser", microsoft.securityId), 10, 15, true);
        Order bidOrder2 = new Order(new OrderCore(bidOrderId2, "TestUser", apple.securityId), 11, 15, true);

        MatchResult matchResult1 = engine.process(askOrder1);
        assertEquals(0, matchResult1.getTrades().size());

        MatchResult matchResult2 = engine.process(askOrder2);
        assertEquals(0, matchResult2.getTrades().size());

        MatchResult matchResult3 = engine.process(bidOrder1);
        assertEquals(0, matchResult3.getTrades().size());

        MatchResult matchResult4 = engine.process(bidOrder2);
        assertEquals(1, matchResult4.getTrades().size());

    }

}

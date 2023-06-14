package com.example.tradingengine.models.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.tradingengine.models.fills.FillAllocationAlgorithm;
import com.example.tradingengine.models.instrument.Security;
import com.example.tradingengine.models.orderbook.MatchResult;
import com.example.tradingengine.models.orderbook.MatchingOrderbook;
import com.example.tradingengine.models.orderbook.OrderbookFactory;
import com.example.tradingengine.models.orders.CancelOrder;
import com.example.tradingengine.models.orders.ModifyOrder;
import com.example.tradingengine.models.orders.Order;

public class Engine {

    private Map<Integer, MatchingOrderbook> orderbooks;

    public Engine(List<Security> securities, FillAllocationAlgorithm fillAllocationAlgorithm) {
        orderbooks = new HashMap<>();

        for (Security security : securities) {
            orderbooks.put(security.securityId, OrderbookFactory.createOrderbook(security, fillAllocationAlgorithm));
        }
    }

    public MatchingOrderbook getOrderbook(int securityId) {
        return orderbooks.get(securityId);
    }

    public MatchResult process(Order order) {

        MatchingOrderbook orderbook = getOrderbook(order.securityId);

        if (orderbook == null) {
            return null; // Need to return a reject order status
        }

        orderbook.addOrder(order);

        MatchResult matchResult = orderbook.match();

        // Need to change this to return a result to the exchange

        return matchResult;

    }

    public MatchResult process(ModifyOrder modifyOrder) {

        MatchingOrderbook orderbook = getOrderbook(modifyOrder.securityId);

        if (orderbook == null) {
            return null; // Need to return a reject order status
        }

        orderbook.changeOrder(modifyOrder);

        MatchResult matchResult = orderbook.match();

        // Need to change this to return a result to the exchange

        return matchResult;

    }

    public MatchResult process(CancelOrder cancelOrder) {

        MatchingOrderbook orderbook = getOrderbook(cancelOrder.securityId);

        if (orderbook == null) {
            return null; // Need to return a order reject status
        }

        orderbook.removeOrder(cancelOrder);

        MatchResult matchResult = orderbook.match();

        // Need to change this to return a result to the exchange

        return matchResult;

    }

}

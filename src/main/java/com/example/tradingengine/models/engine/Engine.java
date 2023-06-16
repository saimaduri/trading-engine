package com.example.tradingengine.models.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    private Map<Integer, ExecutorService> workers;

    public Engine(List<Security> securities, FillAllocationAlgorithm fillAllocationAlgorithm) {
        orderbooks = new HashMap<>();
        workers = new HashMap<>();

        for (Security security : securities) {
            orderbooks.put(security.securityId, OrderbookFactory.createOrderbook(security, fillAllocationAlgorithm));
            workers.put(security.securityId, Executors.newSingleThreadExecutor());
        }
    }

    public MatchingOrderbook getOrderbook(int securityId) {
        return orderbooks.get(securityId);
    }

    public void stop() {
        for (ExecutorService executorService : workers.values()) {
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                // Do something to log this failure
            }
        }
    }

    public void processAsync(Order order) {
        ExecutorService executorService = workers.get(order.securityId);

        Runnable runnable = () -> {
            process(order);
        };

        executorService.submit(runnable);
    }

    public void processAsync(ModifyOrder modifyOrder) {
        ExecutorService executorService = workers.get(modifyOrder.securityId);

        Runnable runnable = () -> {
            process(modifyOrder);
        };

        executorService.submit(runnable);
    }

    public void processAsync(CancelOrder cancelOrder) {

        ExecutorService executorService = workers.get(cancelOrder.securityId);

        Runnable runnable = () -> {
            process(cancelOrder);
        };

        executorService.submit(runnable);

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
            return null; // Need to return a reject order status
        }

        orderbook.removeOrder(cancelOrder);

        MatchResult matchResult = orderbook.match();

        // Need to change this to return a result to the exchange

        return matchResult;

    }

}

package com.example.tradingengine.models.orderbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.tradingengine.models.instrument.Security;
import com.example.tradingengine.models.orders.AskLimitComparer;
import com.example.tradingengine.models.orders.BidLimitComparer;
import com.example.tradingengine.models.orders.CancelOrder;
import com.example.tradingengine.models.orders.Limit;
import com.example.tradingengine.models.orders.ModifyOrder;
import com.example.tradingengine.models.orders.Order;
import com.example.tradingengine.models.orders.OrderbookEntry;

public class Orderbook implements RetrievalOrderbook {

    private final Security security;
    private final Map<Long, OrderbookEntry> orders = new HashMap<>();
    private final TreeMap<Long, Limit> askLimits = new TreeMap<>(new AskLimitComparer());
    public final TreeMap<Long, Limit> bidLimits = new TreeMap<>(new BidLimitComparer());

    public Orderbook(Security security) {
        this.security = security;
    }

    @Override
    public void addOrder(Order order) {

        addOrder(order, order.price, order.isBuySide ? bidLimits : askLimits, orders);

    }

    @Override
    public void changeOrder(ModifyOrder modifyOrder) {
        if (orders.containsKey(modifyOrder.orderId)) {
            OrderbookEntry obe = orders.get(modifyOrder.orderId);
            removeOrder(modifyOrder.toCancelOrder());
            addOrder(modifyOrder.toNewOrder(), obe.parentLimit.price, modifyOrder.isBuySide ? bidLimits : askLimits,
                    orders);
        }
    }

    private static void addOrder(Order order, Long price, Map<Long, Limit> limitLevels,
            Map<Long, OrderbookEntry> internalBook) {
        OrderbookEntry orderbookEntry;
        if (!limitLevels.containsKey(price)) {
            Limit limit = new Limit(price);
            limitLevels.put(price, limit);
            orderbookEntry = new OrderbookEntry(order, limit);
            limit.head = orderbookEntry;
            limit.tail = orderbookEntry;
        } else {
            Limit limit = limitLevels.get(price);
            orderbookEntry = new OrderbookEntry(order, limit);
            if (limit.head == null) {
                limit.head = orderbookEntry;
                limit.tail = orderbookEntry;
            } else {
                OrderbookEntry tailPtr = limit.tail;
                tailPtr.next = orderbookEntry;
                orderbookEntry.previous = tailPtr;
                limit.tail = orderbookEntry;
            }
        }
        internalBook.put(order.orderId, orderbookEntry);
    }

    @Override
    public void removeOrder(CancelOrder cancelOrder) {
        if (orders.containsKey(cancelOrder.orderId)) {
            OrderbookEntry obe = orders.get(cancelOrder.orderId);
            removeOrder(cancelOrder.orderId, obe, orders);
        }
    }

    private void removeOrder(long orderId, OrderbookEntry obe, Map<Long, OrderbookEntry> internalBook) {
        if (obe.previous != null && obe.next != null) {
            obe.next.previous = obe.previous;
            obe.previous.next = obe.next;
        } else if (obe.previous != null) {
            obe.previous.next = null;
        } else if (obe.next != null) {
            obe.next.previous = null;
        }

        if (obe.parentLimit.head == obe && obe.parentLimit.tail == obe) {
            obe.parentLimit.head = null;
            obe.parentLimit.tail = null;
        } else if (obe.parentLimit.head == obe) {
            obe.parentLimit.head = obe.next;
        } else if (obe.parentLimit.tail == obe) {
            obe.parentLimit.tail = obe.previous;
        }

        internalBook.remove(orderId);
    }

    @Override
    public boolean containsOrder(long orderId) {
        return orders.containsKey(orderId);
    }

    @Override
    public OrderbookSpread getSpread() {
        Long bestAsk = null;
        Long bestBid = null;

        if (askLimits.size() > 0 && !askLimits.firstEntry().getValue().isEmpty()) {
            bestAsk = askLimits.firstEntry().getValue().price;
        }

        if (bidLimits.size() > 0 && !bidLimits.lastEntry().getValue().isEmpty()) {
            bestBid = bidLimits.lastEntry().getValue().price;
        }

        return new OrderbookSpread(bestBid, bestAsk);
    }

    public int getCount() {
        return orders.size();
    }

    @Override
    public List<OrderbookEntry> getAskOrders() {
        List<OrderbookEntry> askOrders = new ArrayList<>();
        for (Limit askLimit : askLimits.values()) {
            if (askLimit.isEmpty()) {
                continue;
            } else {
                OrderbookEntry obePtr = askLimit.head;
                while (obePtr != null) {
                    askOrders.add(obePtr);
                    obePtr = obePtr.next;
                }
            }
        }
        return askOrders;
    }

    @Override
    public List<OrderbookEntry> getBuyOrders() {
        List<OrderbookEntry> buyOrders = new ArrayList<>();
        for (Limit bidLimit : bidLimits.values()) {
            if (bidLimit.isEmpty()) {
                continue;
            } else {
                OrderbookEntry obePtr = bidLimit.head;
                while (obePtr != null) {
                    buyOrders.add(obePtr);
                    obePtr = obePtr.next;
                }
            }
        }
        return buyOrders;
    }

}

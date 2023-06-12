package com.example.tradingengine.models.orders;

import java.util.concurrent.atomic.AtomicLong;

public class OrderIdGenerator {
    private static AtomicLong orderId = new AtomicLong(0);

    public static long generateTradeId() {
        return orderId.getAndIncrement();
    }

}

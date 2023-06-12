package com.example.tradingengine.models.trades;

import java.util.concurrent.atomic.AtomicLong;

public class TradeIdGenerator {
    private static AtomicLong tradeNumber = new AtomicLong(0);

    public static long generateTradeId() {
        return tradeNumber.getAndIncrement();
    }

}

package com.example.tradingengine.models.orderbook;

import com.example.tradingengine.models.fills.FillAllocationAlgorithm;
import com.example.tradingengine.models.instrument.Security;

public class OrderbookFactory {

    public static MatchingOrderbook createOrderbook(Security security,
            FillAllocationAlgorithm fillAllocationAlgorithm) {
        switch (fillAllocationAlgorithm) {
            case FIFO:
                return new FifoOrderbook(null);
            default:
                throw new UnsupportedOperationException("Unknown FillAllocationAlgorithm: failed to create Orderbook");
        }
    }

}

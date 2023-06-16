package com.example.tradingengine.models.orderbook;

import java.util.List;

import com.example.tradingengine.models.orders.OrderbookEntry;

public interface RetrievalOrderbook extends OrderEntryOrderbook {

    List<OrderbookEntry> getAskOrders();

    List<OrderbookEntry> getBidOrders();

}

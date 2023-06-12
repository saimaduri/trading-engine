package com.example.tradingengine.models.orderbook;

public interface ReadOnlyOrderbook {

    public boolean containsOrder(long orderId);

    public OrderbookSpread getSpread();

}

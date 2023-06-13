package com.example.tradingengine.models.orderbook;

import com.example.tradingengine.models.orders.CancelOrder;
import com.example.tradingengine.models.orders.ModifyOrder;
import com.example.tradingengine.models.orders.Order;

public interface OrderEntryOrderbook extends ReadOnlyOrderbook {

    public void addOrder(Order order);

    public void changeOrder(ModifyOrder modifyOrder);

    public void removeOrder(CancelOrder cancelOrder);

    public int getCount();

}

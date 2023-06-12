package com.example.tradingengine.models.orders;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderbookEntry {

    public Order currentOrder;
    public LocalDateTime creationTime;
    public Limit parentLimit;

    @Setter
    public OrderbookEntry previous;
    @Setter
    public OrderbookEntry next;

    public OrderbookEntry(Order currentOrder, Limit parentLimit) {
        this.creationTime = LocalDateTime.now();
        this.parentLimit = parentLimit;
        this.currentOrder = currentOrder;
    }

}
